package bg.sofia.uni.fmi.mjt.christmas;

public class Elf extends Thread {
    private Workshop workshop;
    private int elfId;
    private int totalGiftsCrafted = 0;
    public Elf(int id, Workshop workshop){
        try{
            this.elfId = id;
            this.workshop = workshop;
        }
        catch(IllegalArgumentException e){
            throw new UnsupportedOperationException(e);
        }
    }

    public synchronized void craftGift(){
        Gift gift;
        while((gift = workshop.nextGift()) != null) {

            //gift = workshop.nextGift();
            //workshop.getGifts().remove(workshop.getGifts().peek());
            try{
                Thread.sleep(gift.getCraftTime());
            }
            catch(InterruptedException e){
                e.getStackTrace();
            }
            totalGiftsCrafted++;
            workshop.addToStorage(gift);
        }
        //System.out.println("Elf: " + elfId + " created " + this.getTotalGiftsCrafted() + " gifts!");
    }

    public int getTotalGiftsCrafted()
    {
        return this.totalGiftsCrafted;
    }
    @Override
    public void run(){
        craftGift();
    }
}
