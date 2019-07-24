package bg.sofia.uni.fmi.mjt.carstore.enums;

import java.util.Random;

public enum Region {
    SOFIA("CB"), BURGAS("A"), VARNA("B"), PLOVDIV("PB"), RUSE("P"), GABROVO("EB"), VIDIN("BH"), VRATSA("BP");
    private String prefix;
    private int registrationNumber = 1000;

    private Region(String prefix)
    {
        setPrefix(prefix);
    }
    private void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }
    public String getPrefix()
    {
        return this.prefix;
    }

    public String getRegistrationNumber()
    {
        return Integer.toString(registrationNumber);
    }

    public void setRegistrationInt() {
        this.registrationNumber++;
    }
}
