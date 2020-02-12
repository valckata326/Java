package bg.sofia.uni.fmi.mjt.stylechecker.checkers;

public class BracketCheck extends CodeChecker {
    public BracketCheck(String errorMessage)
    {
        super(errorMessage);
    }
    @Override
    public boolean checkErrors(String lineOfCode)
    {
        return lineOfCode.trim().startsWith("{");
    }
}
