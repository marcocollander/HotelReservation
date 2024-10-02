package net.raubuc.exceptions;

public class WrongOptionException extends ReservationCustomException {
    private final int code = 101;

    public WrongOptionException(String message) {
        super(message);
    }

    @Override
    public int getCode() {
        return code;
    }
}
