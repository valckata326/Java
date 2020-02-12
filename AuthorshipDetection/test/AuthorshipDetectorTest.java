package bg.sofia.uni.fmi.mjt.authorship.detection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AuthorshipDetectorTest {
    private static final double averageWordLength = 11;
    private static final double typeTokenRatio = 33;
    private static final double hapaxLegomenaRatio = 50;
    private static final double averageSentenceLength = 0.4;
    private static final double averageSentenceComplexity = 4;
    private double[] sampleWeights = {averageWordLength, typeTokenRatio,
            hapaxLegomenaRatio, averageSentenceLength, averageSentenceComplexity};
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Test
    public void testHasPhraseMethodPositive() {
        String phraseString = "This string might have a phrase, I believe!";
        boolean hasPhrase = Functions.hasPhrase(phraseString);
        Assert.assertTrue(hasPhrase);
    }
    @Test
    public void testHasPhraseMethodNegative() {
        String nonPhraseString = "This string obviously does not have a phrase in it!";
        boolean hasPhrase = Functions.hasPhrase(nonPhraseString);
        Assert.assertFalse(hasPhrase);
    }

    @Test
    public void testHasAlphabetOrNumberPositive() {
        String alphabetString = "This string has many alphabets in it!";
        String numberString = "And this string even has 0 numbers in it!";
        boolean hasAlphabets = Functions.containsAlphabetOrNumber(alphabetString);
        boolean hasNumber = Functions.containsAlphabetOrNumber(numberString);
        Assert.assertTrue(hasAlphabets);
        Assert.assertTrue(hasNumber);
    }
    @Test
    public void testHasAlphabetOrNumberNegative() {
        String firstTest = ":|:::|::\\./[]";
        String secondTest = "";
        String thirdTest = "!?.";
        boolean firstBool = Functions.containsAlphabetOrNumber(firstTest);
        boolean secondBool = Functions.containsAlphabetOrNumber(secondTest);
        boolean thirdBool = Functions.containsAlphabetOrNumber(thirdTest);
        Assert.assertFalse(firstBool);
        Assert.assertFalse(secondBool);
        Assert.assertFalse(thirdBool);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureWhenSignatureIsNull() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl authorshipDetector = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        authorshipDetector.calculateSignature(null);
    }
    @Test
    public void testCalculateSignatureWhenSignatureIsNotNull() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetector authorshipDetector = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        authorshipDetector.calculateSignature(new FileInputStream("./resources/Sample"));
    }
    @Test
    public void testCountingFeaturesOnSampleText() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl autDet = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        LinguisticSignature test = autDet.calculateSignature(new FileInputStream("./resources/Sample"));

        final int expectedAllWords = 26;
        final int expectedDifferentWords = 20;
        final int expectedAllSentences = 4;
        final int expectedAllPhrases = 3;
        final int expectedUniqueWords = 15;

        Assert.assertEquals(expectedAllPhrases, autDet.getPhrases().size());
        Assert.assertEquals(expectedAllSentences, autDet.getAllSentences().size());
        Assert.assertEquals(expectedAllWords, autDet.getAllWords().size());
        Assert.assertEquals(expectedDifferentWords, autDet.getDifferentWords().size());
        Assert.assertEquals(expectedUniqueWords, autDet.getUniqueWords().size());
    }
    @Test
    public void testCountingFeaturesOnEMMASample() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl autDet = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        LinguisticSignature test = autDet.calculateSignature(new FileInputStream("./resources/EMMA"));

        final int expectedAllWords = 121;
        final int expectedDifferentWords = 82;
        final int expectedAllSentences = 3;
        final int expectedAllPhrases = 15;
        final int expectedUniqueWords = 65;

        Assert.assertEquals(expectedAllPhrases, autDet.getPhrases().size());
        Assert.assertEquals(expectedAllSentences, autDet.getAllSentences().size());
        Assert.assertEquals(expectedAllWords, autDet.getAllWords().size());
        Assert.assertEquals(expectedDifferentWords, autDet.getDifferentWords().size());
        Assert.assertEquals(expectedUniqueWords, autDet.getUniqueWords().size());
    }
    @Test
    public void testCountingOnSpecialFile() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl autDet = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        LinguisticSignature test = autDet.calculateSignature(new FileInputStream("./resources/SpecialFile"));

        final double awl = 3.9130434782608696;
        final double ttr = 1.0;
        final double hlr = 1.0;
        final double asl = 23.0;
        final double asc = 3.0;

        Assert.assertEquals(test.getFeatures().get(FeatureType.AVERAGE_WORD_LENGTH), Double.valueOf(awl));
        Assert.assertEquals(test.getFeatures().get(FeatureType.TYPE_TOKEN_RATIO), Double.valueOf(ttr));
        Assert.assertEquals(test.getFeatures().get(FeatureType.HAPAX_LEGOMENA_RATIO), Double.valueOf(hlr));
        Assert.assertEquals(test.getFeatures().get(FeatureType.AVERAGE_SENTENCE_LENGTH), Double.valueOf(asl));
        Assert.assertEquals(test.getFeatures().get(FeatureType.AVERAGE_SENTENCE_COMPLEXITY), Double.valueOf(asc));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSimilarityWhenFirstSignatureIsNull() throws IOException {
        LinguisticSignature first = null;
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl autDet = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        LinguisticSignature second = autDet.calculateSignature(new FileInputStream("./resources/SpecialFile"));
        double answer = autDet.calculateSimilarity(first, second);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSimilarityWhenSecondSignatureIsNull() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl autDet = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        LinguisticSignature first = autDet.calculateSignature(new FileInputStream("./resources/SpecialFile"));
        LinguisticSignature second = null;
        double answer = autDet.calculateSimilarity(first, second);
    }
    @Test
    public void testCalculateSimilarityWhenNoneOfSignaturesIsNull() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl autDet = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        LinguisticSignature first = autDet.calculateSignature(new FileInputStream("./resources/SpecialFile"));
        LinguisticSignature second = autDet.calculateSignature(new FileInputStream("./resources/Sample"));
        final int expectedAnswer = 45;
        double answer = autDet.calculateSimilarity(first, second);
        int intAnswer = (int) answer;
        Assert.assertEquals(expectedAnswer, intAnswer);
    }
    @Test(expected = FileNotFoundException.class)
    public void testFindAuthorWhenInputIsNotFound() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl testing = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        String author = testing.findAuthor(new FileInputStream("null"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWhenInputIsNull() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl testing = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        InputStream input = null;
        String author = testing.findAuthor(input);
    }
    @Test
    public void testContainsAuthorMethodPositive() throws IOException {
        String sampleAuthor = "Jane Austen";
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl testing = new AuthorshipDetectorImpl(authorsInput, sampleWeights);

        Assert.assertTrue(testing.containsAuthor(sampleAuthor));
    }
    @Test
    public void testContainsAuthorMethodNegative() throws IOException {
        String sampleAuthor = "Valentin Iliev";
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl testing = new AuthorshipDetectorImpl(authorsInput, sampleWeights);

        Assert.assertFalse(testing.containsAuthor(sampleAuthor));
    }
    @Test
    public void testFindAuthorReturningAnAuthorFromTheList() throws IOException {
        InputStream authorsInput = new FileInputStream("./resources/authors");
        AuthorshipDetectorImpl testing = new AuthorshipDetectorImpl(authorsInput, sampleWeights);
        InputStream mysteryText = new FileInputStream("resources/EMMA");
        String author = testing.findAuthor(mysteryText);

        Assert.assertTrue(testing.containsAuthor(author));
    }

}
