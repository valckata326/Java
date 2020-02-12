package bg.sofia.uni.fmi.mjt.authorship.detection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthorshipDetectorImpl implements AuthorshipDetector {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final double DIFFERENCE = 1000000.0;

    private List<String> allWords = new ArrayList<>();
    private List<String> allSentences = new ArrayList<>();
    private List<String> phrases = new ArrayList<>();
    private List<String> differentWords = new ArrayList<>();
    private List<String> uniqueWords = new ArrayList<>();
    private List<String> authorsInformation = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private double[] weights;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(signaturesDataset))) {
            authorsInformation = reader.lines().collect(Collectors.toList());
            for (String current: authorsInformation) {
                String[] currentInformation = current.split(", ");
                Author currentAuthor = new Author(currentInformation[ZERO],
                        Double.parseDouble(currentInformation[ONE]),
                        Double.parseDouble(currentInformation[TWO]),
                        Double.parseDouble(currentInformation[THREE]),
                        Double.parseDouble(currentInformation[FOUR]),
                        Double.parseDouble(currentInformation[FIVE]));

                authors.add(currentAuthor);
            }

            this.weights = weights;
        }
        catch (IOException e) {
            System.out.println("Wrong data input stream!");
        }
    }
    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        if(mysteryText == null) {
            throw new IllegalArgumentException("Mystery text is null!");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(mysteryText))) {
            List<String> streamToList = reader.lines().collect(Collectors.toList());

            allWords = streamToList.stream()
                    .map(Functions::cleanUp)
                    .flatMap(line -> Stream.of(line.split("\\s+")))
                    .filter(x -> !x.isBlank())
                    .filter(Functions::containsAlphabetOrNumber)
                    .map(String::trim)
                    .map(Functions::cleanUp)
                    .collect(Collectors.toList());
            allSentences = Arrays.stream(
                    streamToList.stream()
                            .collect(Collectors.joining("\\n"))
                            .split("[\\.\\?\\!]"))
                    .filter(x -> !x.isBlank())
                    .map(Functions::cleanUp)
                    .map(String::trim)
                    .collect(Collectors.toList());
            phrases = allSentences.stream()
                    .filter(Functions::hasPhrase)
                    .map(str -> str.split(",|\\:|\\;"))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
            differentWords = allWords.stream()
                    .distinct()
                    .collect(Collectors.toList());
            Map<String, Long> occurringOnceMap = allWords.stream()
                    .collect(Collectors.groupingBy(Function.<String>identity(), Collectors.counting()));

            occurringOnceMap.entrySet()
                    .removeIf(entry -> entry.getValue() > ONE);
            uniqueWords = new ArrayList<>(occurringOnceMap.keySet());
            long allWordsLength = allWords.stream().mapToInt(String::length).sum();

            double averageWordLength = (double) allWordsLength / allWords.size();
            double typeTokenRation = (double) differentWords.size() / allWords.size();
            double hapaxLegomenaToken = (double) uniqueWords.size() / allWords.size();
            double averageSentenceLength = (double) allWords.size() / allSentences.size();
            double averageSentenceComplexity = (double) phrases.size() / allSentences.size();

            Map<FeatureType, Double> lingua = new HashMap<>();
            lingua.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexity);
            lingua.put(FeatureType.AVERAGE_SENTENCE_LENGTH, averageSentenceLength);
            lingua.put(FeatureType.AVERAGE_WORD_LENGTH, averageWordLength);
            lingua.put(FeatureType.HAPAX_LEGOMENA_RATIO, hapaxLegomenaToken);
            lingua.put(FeatureType.TYPE_TOKEN_RATIO, typeTokenRation);

            return new LinguisticSignature(lingua);

        }
        catch (IOException exception) {
            System.out.println("Invalid data input stream");
        }
        return null;
    }

    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {
        if (firstSignature == null) {
            throw new IllegalArgumentException("First signature is null!");
        } else if (secondSignature == null) {
            throw new IllegalArgumentException("Second signature is null");
        }
        double numberToReturn = Math.abs(firstSignature.getFeatures().get(FeatureType.AVERAGE_WORD_LENGTH) -
                secondSignature.getFeatures().get(FeatureType.AVERAGE_WORD_LENGTH)) * weights[ZERO]
                + Math.abs(firstSignature.getFeatures().get(FeatureType.TYPE_TOKEN_RATIO) -
                secondSignature.getFeatures().get(FeatureType.TYPE_TOKEN_RATIO)) * weights[ONE]
                + Math.abs(firstSignature.getFeatures().get(FeatureType.HAPAX_LEGOMENA_RATIO) -
                secondSignature.getFeatures().get(FeatureType.HAPAX_LEGOMENA_RATIO)) * weights[TWO]
                + Math.abs(firstSignature.getFeatures().get(FeatureType.AVERAGE_SENTENCE_LENGTH) -
                secondSignature.getFeatures().get(FeatureType.AVERAGE_WORD_LENGTH)) * weights[THREE]
                + Math.abs(firstSignature.getFeatures().get(FeatureType.AVERAGE_SENTENCE_COMPLEXITY) -
                secondSignature.getFeatures().get(FeatureType.AVERAGE_SENTENCE_COMPLEXITY)) * weights[FOUR];
        return numberToReturn;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException("Stream is null");
        }
        double difference = DIFFERENCE;
        String authorName = null;
        LinguisticSignature signature = this.calculateSignature(mysteryText);
        for (Author current: authors) {
            Map<FeatureType, Double> converter = new HashMap<>();
            converter.put(FeatureType.AVERAGE_WORD_LENGTH, current.getAverageWordLength());
            converter.put(FeatureType.TYPE_TOKEN_RATIO, current.getTypeTokenRatio());
            converter.put(FeatureType.HAPAX_LEGOMENA_RATIO, current.getHapaxLegomenaRatio());
            converter.put(FeatureType.AVERAGE_SENTENCE_LENGTH, current.getAverageSentenceLength());
            converter.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, current.getAverageSentenceComplexity());
            LinguisticSignature currentSignature = new LinguisticSignature(converter);
            double similarity = calculateSimilarity(signature, currentSignature);
            if (similarity < difference) {
                difference = similarity;
                authorName = current.getName();
            }
        }
        return authorName;
    }

    public List<String> getAllWords() {
        return allWords;
    }

    public List<String> getAllSentences() {
        return allSentences;
    }

    public List<String> getPhrases() {
        return phrases;
    }

    public List<String> getDifferentWords() {
        return differentWords;
    }

    public List<String> getUniqueWords() {
        return uniqueWords;
    }
    public boolean containsAuthor(String author) {
        for (Author current: authors) {
            if (current.getName().equals(author)) {
                return true;
            }
        }
        return false;
    }
}
