package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.util.Collection;

public interface ShoppingDirectory {
    Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException;
    Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException;
    Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException;
    void submitOffer(Offer offer) throws OfferAlreadySubmittedException;
}
