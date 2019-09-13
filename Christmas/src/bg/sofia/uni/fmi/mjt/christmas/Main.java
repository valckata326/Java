package bg.sofia.uni.fmi.mjt.christmas;

public class Main {

    public static void main(String[] args){
        Workshop workshop = new Workshop();
        Christmas christmas = new Christmas(workshop, 1000, 2000);
        christmas.celebrateChristmas();
    }
}
