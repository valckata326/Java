package bg.sofia.uni.fmi.mjt.christmas;

public class Santa extends Thread  {
    private Workshop workshop;
    private int counterOfCoursesMade = 0;
    private int indexOfGift;
    private Gift[] santaSleigh;

    public Santa(Workshop workshop){
        this.workshop = workshop;
        santaSleigh = new Gift[100];
        indexOfGift = 0;
    }
    @Override
    public void run(){
        while(workshop.getFinalGiftCount() / 100 != this.getCounterOfCoursesMade())
        {
            if(workshop.getStorage().size() >= 100)
            {

                for(int i = 0; i < 100; i++)
                {
                    santaSleigh[i] = workshop.getStorage().get(indexOfGift);
                    indexOfGift++;
                }
                counterOfCoursesMade++;
            }
        }
        //System.out.println("Santa made " + this.getCounterOfCoursesMade() + " courses");
        //System.out.println("and delivered " + this.indexOfGift + " gifts!");
    }

    public int getCounterOfCoursesMade() {
        return counterOfCoursesMade;
    }
}
