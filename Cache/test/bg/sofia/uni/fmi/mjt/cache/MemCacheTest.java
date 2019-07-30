package bg.sofia.uni.fmi.mjt.cache;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static junit.framework.TestCase.*;

public class MemCacheTest {
    private final LocalDateTime specificTime = LocalDateTime.of(2019, Month.SEPTEMBER,30,03,03);
    @Test
    public void testEmptyDefaultCache() {
        MemCache<String, Integer> cache = new MemCache<>();

        assertEquals("Empty cache's size must be 0.", 0, cache.size());
        assertEquals("Empty cache's hit rate must be 0.", 0, cache.getHitRate(), 0.1);
    }

    @Test
    public void testCapacityCache()
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        assertEquals("Cache with certain capacity must have size = 0", 0, cache.size());
        assertEquals("Cache with certain capacity must have a hit rate = 0", 0, cache.getHitRate(), 0.1);
    }

    @Test
    public void testNullKeyAndValue() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set(null, null, LocalDateTime.now());
        assertEquals("Must not add null key with null value.", 0,cache.size());
    }
    @Test
    public void testOneKeyWithTwoValues() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set("test_key1",3, LocalDateTime.now());
        cache.set("test_key1",5, LocalDateTime.now().plusDays(2));
        assertEquals("Cache's size must be 1.", 1, cache.size());
        assertEquals("Value of the key must be updated to 5", 5, (int) cache.get("test_key1"));
    }
    @Test
    public void testExpiredReplacement() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set("testKey1",1,LocalDateTime.now().plusHours(2));
        cache.set("testKey2",2,LocalDateTime.now().minusHours(2));
        cache.set("testKey3",3,LocalDateTime.now().plusHours(2));
        assertEquals("Cache's size must be 2", 2, cache.size());
        assertEquals("Second key is expired - should be replaced by third",3,(int) cache.get("testKey3"));
        assertNull("Second key should not exist in the cache", cache.get("testKey2"));
    }
    @Test(expected = CapacityExceededException.class)
    public void testOverfilledCapacity () throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set("testKey1",1,LocalDateTime.now().plusHours(2));
        cache.set("testKey2",2,LocalDateTime.now().plusHours(2));
        cache.set("testKey3",3,LocalDateTime.now());
    }

    @Test
    public void testRightContaining() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set("testKey1",1,LocalDateTime.now().plusHours(2));
        assertEquals("Didn't manage to get a key presence", 1,(int) cache.get("testKey1"));

    }
    @Test
    public void testWrongContaining() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set("testKey1",1,LocalDateTime.now().plusHours(2));
        assertNull("Invalid key caught inside cache.",cache.get("testKey"));
    }
    @Test
    public void testIfDeletedContained() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set("testKey1",1,LocalDateTime.now().minusHours(2));
        cache.get("testKey1");
        assertEquals("Cache not empty.",0,cache.size());
        assertNull("Expired key not deleted properly.", cache.get("testKey1"));
    }
    @Test
    public void testGetWithProperKey() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(2);
        cache.set("testKey1",1,LocalDateTime.now().plusHours(2));
        assertEquals("Contained key in cache not found.",1,(int) cache.get("testKey1"));
    }
    @Test
    public void testHitRate() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, LocalDateTime.now().plusHours(2));
        cache.set("2", 2, LocalDateTime.now().minusDays(2));
        cache.set("3", 3, LocalDateTime.now().plusHours(1));
        cache.set("4", 4, LocalDateTime.now().minusDays(4));
        cache.get("1"); //++
        cache.get("2"); // --
        cache.get("3"); // ++
        cache.get("4"); // --
        assertEquals("Invalid hit rate.",  0.5,cache.getHitRate());
    }
    @Test
    public void testHitRateCaseMissZero() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, LocalDateTime.now().plusHours(2));
        cache.set("2", 2, LocalDateTime.now().minusDays(2));
        cache.get("1");
        assertEquals("Hit = 1, miss = 0.", (double) 1,cache.getHitRate());
    }
    @Test
    public void testHitRateDefaultCase() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, LocalDateTime.now().plusHours(2));
        cache.set("2", 2, LocalDateTime.now().minusDays(2));
        cache.get("1");
        cache.get("3");
        assertEquals("Hit = 1, miss = 1.",  0.5,cache.getHitRate());
    }
    @Test
    public void testClearCache() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, LocalDateTime.now().plusHours(2));
        cache.set("2", 2, LocalDateTime.now().minusDays(2));
        cache.set("3", 3, LocalDateTime.now().plusHours(1));
        cache.clear();
        assertEquals("Cache is cleared so it has to have size = 0", 0, cache.size());
        assertEquals("Cleared cache must have a hit rate = 0", (double) 0, cache.getHitRate());
    }
    @Test
    public void testRemoveNotAvailable() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, LocalDateTime.now().plusHours(2));
        assertFalse(cache.remove("2"));
    }
    @Test
    public void testRemoveAvailable() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, LocalDateTime.now().plusHours(2));
        assertTrue(cache.remove("1"));
    }
    @Test
    public void testExpiration() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, specificTime);
        assertEquals("Not expired cache not found", specificTime,cache.getExpiration("1"));
    }
    @Test
    public void testInvalidExpiration() throws CapacityExceededException
    {
        MemCache<String, Integer> cache = new MemCache<>(3);
        cache.set("1", 1, specificTime);
        assertNull(cache.getExpiration("2"));
    }
}
