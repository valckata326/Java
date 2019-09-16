package bg.sofia.uni.fmi.mjt.christmas;

public class Main {

    public static void main(String[] args){
        Workshop workshop = new Workshop();
        Christmas christmas = new Christmas(workshop, 2666, 3000);
        christmas.celebrateChristmas();
        Santa santa = new Santa(workshop);
        santa.run();

    }
}
