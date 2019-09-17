package bg.sofia.uni.fmi.mjt.christmas;
import java.util.Random;

public class Kid extends Thread{
    private final int MAX_THINKING_TIME = 30;

    private Workshop workshop;
    private Gift wishedGift;
    public Kid(Workshop workshop){
        try{
            this.workshop = workshop;
        }
        catch(IllegalArgumentException e){
            throw new UnsupportedOperationException(e);
        }
    }

    public void makeWish(){
        wishedGift = Gift.getGift();
        workshop.postWish(wishedGift);
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
        if(!workshop.isChristmasYet())
        {
            makeWish();
            //System.out.println("Kid made his wish!");
        }
    }
}
