package bg.sofia.uni.fmi.mjt.christmas;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Workshop {
    private final static int NUMBER_OF_ELVES = 20;

    private List<Gift> storage;
    private volatile boolean isChristmasTime = false;
    private Queue<Gift> gifts;
    private Elf[] elves;
    private int wishCounter = 0;
    private int finalGiftCount;
    private int giftsCraftedByElves = 0;
    public Workshop(){
        gifts = new ArrayBlockingQueue<>(10000);

        this.elves = new Elf[NUMBER_OF_ELVES]; // 20 празни елфи;
        for(int i = 0; i < NUMBER_OF_ELVES;i++)
        {
            elves[i] = new Elf(i,this);
            // 20 вече конкретни елфи
        }
        for(int i = 0; i < NUMBER_OF_ELVES;i++)
        {
            elves[i].start(); //пускане на нишките(елфите) да обработват подаръците;
        }
        storage = new ArrayList<>();
    }
    public synchronized void postWish(Gift gift){
        getGifts().add(gift);
        wishCounter++;
        this.notifyAll(); // някой елф да почне конструирането на този подарък
    }
    public synchronized Gift nextGift() {
        while (!isChristmasYet() && getGifts().isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return  (isChristmasYet() && getGifts().isEmpty()) ? null : getGifts().poll();
    }
    public Elf[] getElves(){
        return this.elves;
    }

    public Queue<Gift> getGifts() {
        return gifts;
    }

    public List<Gift> getStorage() {
        return storage;
    }
    public synchronized void setChristmasTime(){
        this.isChristmasTime = true;
        this.notifyAll();
    }
    public synchronized boolean isChristmasYet()
    {
        return isChristmasTime;
    }
    public int getWishCounter()
    {
        return this.wishCounter;
    }
    public synchronized void addToStorage(Gift gift)
    {
        this.storage.add(gift);
    }

    public int getFinalGiftCount() {
        return finalGiftCount;
    }

    public void setFinalGiftCount(int finalGiftCount) {
        this.finalGiftCount = finalGiftCount;
    }
    public synchronized int getGiftsCraftedByElves()
    {
        return this.giftsCraftedByElves;
    }
}
