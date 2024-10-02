package net.raubuc.exceptions;

public class OnlyNumberException extends ReservationCustomException {
    private final int code = 102;

    public OnlyNumberException(String message) {
        super(message);
    }

    @Override
    public int getCode() {
        return code;
    }
}
