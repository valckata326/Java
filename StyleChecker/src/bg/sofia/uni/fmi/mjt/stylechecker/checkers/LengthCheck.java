package bg.sofia.uni.fmi.mjt.stylechecker.checkers;

public class LengthCheck extends CodeChecker {
    private int lengthLimit;
    public LengthCheck(String errorMessage, int lengthLimit)
    {
        super(String.format(errorMessage, lengthLimit));
        this.lengthLimit = lengthLimit;
    }
    @Override
    public boolean checkErrors(String lineOfCode)
    {
        lineOfCode = lineOfCode.trim();
        if(lineOfCode.startsWith("import"))
        {
            return false;
        }
        return lineOfCode.trim().length() > getLengthLimit();
    }

    public int getLengthLimit() {
        return lengthLimit;
    }
}
