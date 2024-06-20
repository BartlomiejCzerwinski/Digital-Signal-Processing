package pl.bcpr.cps.logic.exception;

public class NotSameLengthException extends RuntimeException {
    public NotSameLengthException() {
    }

    public NotSameLengthException(String message) {
        super(message);
    }

    public NotSameLengthException(Throwable cause) {
        super(cause);
    }
}
    