package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class YoutubeTrendsExplorer {
    private static final long THREE = 3;
    private static final int HUNDRED_THOUSAND = 100000;
    private List<TrendingVideo> trendingVideos;

    public YoutubeTrendsExplorer(InputStream input) {
        trendingVideos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            reader.readLine();
            List<String> inputToStringLines = reader.lines().collect(Collectors.toList());
            for (String singleLine: inputToStringLines) {
                TrendingVideo current = TrendingVideo.createTrendingVideo(singleLine);
                trendingVideos.add(current);
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public Collection<TrendingVideo> getTrendingVideos() {
        return this.trendingVideos;
    }

    public String findIdOfLeastLikedVideo() {
        return trendingVideos.stream()
                .min(Comparator.comparing(TrendingVideo::getLikes))
                .get()
                .getId();
    }

    public String findIdOfMostLikedLeastDislikedVideo() {
        return trendingVideos.stream()
                .max(Comparator.comparing(x->x.getLikes() - x.getDislikes()))
                .get()
                .getId();
    }

    public List<String> findDistinctTitlesOfTop3VideosByViews() {
        return trendingVideos.stream()
                .sorted(Comparator.comparing(TrendingVideo::getViews).reversed())
                .map(v -> v.getTitle())
                .distinct()
                .limit(THREE)
                .collect(Collectors.toList());
    }

    public String findIdOfMostTaggedVideo() {
        return trendingVideos.stream()
                .max(Comparator.comparing(v -> v.getTags().size()))
                .get()
                .getId();

    }

    public String findTitleOfFirstVideoTrendingBefore100KViews() {
        return trendingVideos.stream()
                .filter(v -> v.getViews() < HUNDRED_THOUSAND)
                .min(Comparator.comparing(TrendingVideo::getPublishDate))
                .get().getTitle();
    }

    public String findIdOfMostTrendingVideo() {
        Map<String, Long> videosByTitle = trendingVideos.stream()
                .collect(Collectors.groupingBy(TrendingVideo::getId, Collectors.counting()));
        return videosByTitle.entrySet()
                .stream()
                .max(Comparator.comparing(v->v.getValue()))
                .get()
                .getKey();
    }
    public static void main(String[] args) {

        String myText = "I hope this work out!" + System.lineSeparator()
                + "1\t07.12.19\t8thDecemberParty\t2019-12-07T18:55:42.00Z\tnon-alcohol" +
                "|non-java|homework...\t100\t200\t300"
                + System.lineSeparator()
                + "2\t07.12.19\t7thDecemberShopping\t2019-12-07T18:55:42.00Z\talcohol" +
                "|plans|JavaHomework...|happyholidays\t6\t6\t6"
                + System.lineSeparator()
                + "3\t08.12.19\t8thDecemberTravelling\t2019-12-07T18:55:42.00Z\tgaaz" +
                "|jokingOfTheDriver|\t500\t50\t30"
                + System.lineSeparator()
                + "1\t07.12.19\t8thDecemberParty\t2019-12-07T18:55:42.00Z\tnon-alcohol" +
                "|non-java|homework...\t90000\t500\t30000"
                + System.lineSeparator()
                + "5\t08.12.19\tToniStoraro\t2019-12-08T19:02:33.00Z\tchalga" +
                "|djinche|vseoshteobshtaka\t100000\t50000\t3"
                + System.lineSeparator()
                + "6\t08.12.19\t8thDecemberParty\t2019-12-08T19:23:33.00Z\tstartDrinking" +
                "|homeworkDone|rakiika\t99999\t5555\t3333";
        System.out.println(myText);
    }
}
