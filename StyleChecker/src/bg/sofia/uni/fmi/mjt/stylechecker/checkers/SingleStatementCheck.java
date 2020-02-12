package bg.sofia.uni.fmi.mjt.stylechecker.checkers;

public class SingleStatementCheck extends CodeChecker {
    public SingleStatementCheck(String errorMessage)
    {
        super(errorMessage);
    }
    @Override
    public boolean checkErrors(String lineOfCode)
    {
        int lastIndexOfMatch = -1;
        for(int i = lineOfCode.length() - 1; i >= 0; i--)
        {
            if(lineOfCode.charAt(i) == ';')
            {
                lastIndexOfMatch = i;
                if(lastIndexOfMatch != lineOfCode.length() - 1 && lineOfCode.charAt(i + 1) != ';')
                {
                    return true;
                }
            }
        }
        if((lastIndexOfMatch == -1 && !lineOfCode.trim().equals("{") && !lineOfCode.trim().equals("}"))
                && lineOfCode.trim().charAt(lineOfCode.length() - 1) != '{')
        {
            return true;
        }
        return false;
    }
}
