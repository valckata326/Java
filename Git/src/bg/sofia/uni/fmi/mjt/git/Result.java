package bg.sofia.uni.fmi.mjt.git;

public class Result {
    private boolean typeOfResult;
    private String message;
    public Result(String message, boolean isSuccessful) {
        this.message = message;
        this.typeOfResult = isSuccessful;
    }
    public boolean isSuccessful()
    {
        return this.typeOfResult;
    }
    public String getMessage()
    {
        return this.message;
    }
    public static Result successful(String message)
    {
        return new Result(message,true);
    }
    public static Result failure(String message)
    {
        return new Result(message,false);
    }
}
