package bg.sofia.uni.fmi.mjt.christmas;
import java.util.Random;

public class Kid extends Thread{
    private final int MAX_THINKING_TIME = 30;

    private Workshop workshopToReceive;
    private Gift wishedGift;
    public Kid(Workshop workshop){
        try{
            workshopToReceive = workshop;
        }
        catch(IllegalArgumentException e){
            throw new UnsupportedOperationException(e);
        }
    }

    public void makeWish(){
        wishedGift = Gift.getGift();
        workshopToReceive.postWish(wishedGift);
    }
    @Override
    public void run()
    {
        try{
            Random rand = new Random();
            int timeToChoose = rand.nextInt(MAX_THINKING_TIME) + 1;
            Thread.sleep(timeToChoose);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        if(!workshopToReceive.isChristmasYet())
        {
            makeWish();
            //System.out.println("Kid made his wish!");
        }
    }
}
