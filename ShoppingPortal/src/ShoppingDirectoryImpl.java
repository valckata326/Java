package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.*;

public class ShoppingDirectoryImpl implements ShoppingDirectory {
    private Map<String, TreeSet<Offer>> storedByProductName;
    private Map<String, HashMap<LocalDate, HashSet<Offer>>> storedForStatistics;
    private static final int DAYS_BEFORE = 30;

    public ShoppingDirectoryImpl() {
        storedByProductName = new HashMap<>();
        storedForStatistics = new HashMap<>();
    }
    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException();
        } else if (getStoredByProductName().get(productName.toLowerCase()) == null) {
            throw new ProductNotFoundException("No products with this productName were found");
        } else {
            Set<Offer> treeSet = new TreeSet<Offer>(new TotalPriceComp());
            treeSet.addAll(getStoredByProductName().get(productName.toLowerCase()));
            return treeSet;
        }
    }

    @Override
    public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {
        if (productName == null) {
            throw new IllegalArgumentException();
        } else if (getStoredByProductName().get(productName.toLowerCase()) == null) {
            throw new ProductNotFoundException("No products with this productName were found!");
        } else {
            Offer offerToReturn = null;
            double minOffer = -1;
            for (Offer offer: this.getStoredByProductName().get(productName.toLowerCase())) {
                if (minOffer == -1 && validateDate(offer)) {
                    offerToReturn = offer;
                    minOffer = offer.getTotalPrice();
                }
                else if (offer.getTotalPrice() < minOffer
                        && validateDate(offer)) {
                    offerToReturn = offer;
                    minOffer = offer.getTotalPrice();
                }
            }
            if (offerToReturn == null) {
                throw new NoOfferFoundException("No such offer found!");
            }
            return offerToReturn;
        }
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException();
        }
        else if (getStoredByProductName().get(productName.toLowerCase()) == null) {
            throw new ProductNotFoundException("No products found!");
        }
        else {
            TreeSet<PriceStatistic> toReturn = new TreeSet<>(new DateComparator());
            for (LocalDate date: getStoredForStatistics().get(productName.toLowerCase()).keySet()) {
                PriceStatistic toAdd = new PriceStatistic(date);
                double lowestPrice = -1;
                double averagePrice = 0;
                int counter = 0;
                for (Offer offer: getStoredForStatistics()
                    .get(productName.toLowerCase()).get(date)) {
                    if (lowestPrice == -1) {
                        lowestPrice = offer.getTotalPrice();
                    }
                    else if (offer.getTotalPrice() < lowestPrice && lowestPrice != -1) {
                        lowestPrice = offer.getTotalPrice();
                    }
                    counter++;
                    averagePrice += offer.getTotalPrice();
                }
                averagePrice = Math.round(averagePrice / counter);
                toAdd.setLowestPrice(lowestPrice);
                toAdd.setAveragePrice(averagePrice);
                toReturn.add(toAdd);
            }
            return toReturn;
        }
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
        if (offer == null) {
            throw new IllegalArgumentException();
        }
        if (checkIfOfferExists(getStoredByProductName().get(offer.getProductName().toLowerCase()), offer)) {
            throw new OfferAlreadySubmittedException("This offer already exists!");
        }
        else {
            if (getStoredForStatistics().get(offer.getProductName().toLowerCase()) == null) {
                getStoredForStatistics()
                        .put(offer.getProductName().toLowerCase(), new HashMap<LocalDate, HashSet<Offer>>());
                getStoredForStatistics().get(offer.getProductName().toLowerCase())
                        .put(offer.getDate(), new HashSet<Offer>());
            }
            else if (getStoredForStatistics().get(offer.getProductName().toLowerCase()).get(offer.getDate()) == null) {
                getStoredForStatistics().get(offer.getProductName().toLowerCase())
                        .put(offer.getDate(), new HashSet<Offer>());
            }
            getStoredByProductName()
                    .computeIfAbsent(offer.getProductName().toLowerCase(), k -> new TreeSet<>(new TotalPriceComp()));
            getStoredByProductName().get(offer.getProductName().toLowerCase()).add(offer);
            getStoredForStatistics().get(offer.getProductName().toLowerCase()).get(offer.getDate()).add(offer);
        }
    }
    private boolean checkIfOfferExists(Collection<Offer> collection, Offer offer) {
        if (collection == null) {
            return false;
        }
        for (Offer current : collection) {
            if (current.checkIfTheSame(offer)) {
                return true;
            }
            if (current.getTotalPrice() < offer.getTotalPrice()) {
                return false;
            }
        }
        return false;
    }
    public Map<String, TreeSet<Offer>> getStoredByProductName() {
        return storedByProductName;
    }

    public Map<String, HashMap<LocalDate, HashSet<Offer>>> getStoredForStatistics() {
        return storedForStatistics;
    }
    public boolean validateDate(Offer offer) {
        return !offer.getDate().plusDays(DAYS_BEFORE).isBefore(LocalDate.now());
    }
}
