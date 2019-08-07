package bg.sofia.uni.fmi.mjt.stylechecker.checkers;

public abstract class CodeChecker {
    private String errorMessage;
    public CodeChecker(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage()
    {
        return this.errorMessage;
    }
    public abstract boolean checkErrors(String lineOfCode);
}
