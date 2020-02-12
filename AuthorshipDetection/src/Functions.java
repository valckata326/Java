package bg.sofia.uni.fmi.mjt.authorship.detection;

public  class Functions {
    public static boolean containsAlphabetOrNumber(String word) {
        return word.matches("(?<=\\s|^).*[a-z0-9].*(?=[.,;:]?\\s|$)");
    }
    public static boolean hasPhrase(String word) {
        return word.matches("(?<=\\s|^).*[:;,].*(?=[.,;:]?\\s|$)");
    }
    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll( "^[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]" +
                        "+|[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+$", "");
    }
}
