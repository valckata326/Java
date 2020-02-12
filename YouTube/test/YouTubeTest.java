package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class YouTubeTest {
    YoutubeTrendsExplorer explorer;
    @Before
    public void doBefore() {
        String testString = "I hope this work out!" + System.lineSeparator()
                + "1\t07.12.19\t8thDecemberParty\t2019-12-07T18:55:42.00Z\tnon-alcohol" +
                "|non-java|homework...\t100\t200\t300"
                + System.lineSeparator()
                + "2\t06.12.19\t6thDecemberShopping\t2019-12-07T18:55:42.00Z\talcohol" +
                "|plans|JavaHomework...|happyholidays\t6\t6\t6"
                + System.lineSeparator()
                + "3\t08.12.19\t8thDecemberTravelling\t2019-12-07T18:55:42.00Z\tgaaz" +
                "|jokingOfTheDriver|\t99992\t50\t30"
                + System.lineSeparator()
                + "1\t07.12.19\t8thDecemberParty\t2019-12-07T18:55:42.00Z\tnon-alcohol" +
                "|non-java|homework...\t100\t200\t300"
                + System.lineSeparator()
                + "5\t08.12.19\tToniStoraro\t2019-12-08T19:02:33.00Z\tchalga" +
                "|djinche|vseoshteobshtaka\t100001\t50000\t3"
                + System.lineSeparator()
                + "6\t08.12.19\t8thDecemberParty\t2019-12-08T19:23:33.00Z\tstartDrinking" +
                "|homeworkDone|rakiika\t99999\t5555\t3333";
        try (InputStream input = new ByteArrayInputStream(testString.getBytes())) {
            explorer = new YoutubeTrendsExplorer(input);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @Test
    public void findIDLeastLikedVideoTest() {
        assertEquals("2", explorer.findIdOfLeastLikedVideo());
    }
    @Test
    public void findIDMostLikedLeastUnlikedVideoTest() {
        assertEquals("5", explorer.findIdOfMostLikedLeastDislikedVideo());
    }
    @Test
    public void findIdMostTaggedVideoTest() {
        assertEquals("2", explorer.findIdOfMostTaggedVideo());
    }
    @Test
    public void findTop3MostViewedVideosTest() {
        List<String> titles = List.of(new String[]{"ToniStoraro", "8thDecemberParty", "8thDecemberTravelling"});
        List<String> actual = explorer.findDistinctTitlesOfTop3VideosByViews();
        Assert.assertArrayEquals(titles.toArray(), actual.toArray());
    }
    @Test
    public void findTheFirstVideoWithLessThan100kTest() {
        assertEquals("8thDecemberParty", explorer.findTitleOfFirstVideoTrendingBefore100KViews());
    }
    @Test
    public void findTheVideoThatBecameTrendyMostTimesTest() {
        String actual = explorer.findIdOfMostTrendingVideo();
        assertEquals("1", actual);

    }
}
