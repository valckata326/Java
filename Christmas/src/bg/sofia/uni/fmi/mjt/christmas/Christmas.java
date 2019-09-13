package bg.sofia.uni.fmi.mjt.christmas;


public class Christmas {
    private final Workshop workshop;
    private int numberOfKids;
    private int toChristmasTime;

    public Christmas(Workshop workshop, int numberOfKids, int toChristmasTime)
    {
        this.workshop = workshop;
        this.numberOfKids = numberOfKids;
        this.toChristmasTime = toChristmasTime;
        this.workshop.setFinalGiftCount(numberOfKids);
    }
    public void celebrateChristmas(){
        Thread[] kids = new Thread[getNumberOfKids()];
        for(int i = 0; i < getNumberOfKids(); i++)
        {
            kids[i] = new Thread(new Kid(workshop));
            kids[i].run();
        }
        for(int i = 0; i < getNumberOfKids(); i++)
        {
            try{
                kids[i].join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        try{
            Thread.sleep(toChristmasTime);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        synchronized (workshop) {
            workshop.setChristmasTime();
        }
        System.out.println("Number of gifts to craft: " + workshop.getWishCounter());
        System.out.println("Number of gifts crafted:" + workshop.getStorage().size());
    }

    public int getNumberOfKids() {
        return numberOfKids;
    }
}
