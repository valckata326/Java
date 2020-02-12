package bg.sofia.uni.fmi.mjt.movies;

public class InvalidStreamException extends RuntimeException {
    public InvalidStreamException(String streamException)
    {
        super(streamException);
    }
}
