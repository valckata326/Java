package bg.sofia.uni.fmi.mjt.authorship.detection;

public class Author {
    private String name;
    private double averageWordLength;
    private double typeTokenRatio;
    private double hapaxLegomenaRatio;
    private double averageSentenceLength;
    private double averageSentenceComplexity;

    public Author(String name, double averageWordLength,
                  double typeTokenRatio, double hapaxLegomenaRatio,
                  double averageSentenceLength, double averageSentenceComplexity) {
        this.name = name;
        this.averageWordLength = averageWordLength;
        this.typeTokenRatio = typeTokenRatio;
        this.hapaxLegomenaRatio = hapaxLegomenaRatio;
        this.averageSentenceLength = averageSentenceLength;
        this.averageSentenceComplexity = averageSentenceComplexity;
    }

    public String getName() {
        return name;
    }

    public double getAverageWordLength() {
        return averageWordLength;
    }

    public double getTypeTokenRatio() {
        return typeTokenRatio;
    }

    public double getHapaxLegomenaRatio() {
        return hapaxLegomenaRatio;
    }

    public double getAverageSentenceLength() {
        return averageSentenceLength;
    }

    public double getAverageSentenceComplexity() {
        return averageSentenceComplexity;
    }
}
