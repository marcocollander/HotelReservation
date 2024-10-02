package net.raubuc.exceptions;

public abstract class ReservationCustomException extends RuntimeException {
    public abstract int getCode();

    public ReservationCustomException(String message) {
        super(message);
    }
}
