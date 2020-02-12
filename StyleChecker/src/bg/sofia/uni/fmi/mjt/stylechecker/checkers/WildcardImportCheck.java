package bg.sofia.uni.fmi.mjt.stylechecker.checkers;

public class WildcardImportCheck extends CodeChecker{
    public WildcardImportCheck(String errorMessage)
    {
        super(errorMessage);
    }
    @Override
    public boolean checkErrors(String lineOfCode)
    {
        return lineOfCode.startsWith("import")
                && lineOfCode.endsWith(".*;");
    }
}
