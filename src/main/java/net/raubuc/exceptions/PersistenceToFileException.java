package net.raubuc.exceptions;

public class PersistenceToFileException extends ReservationCustomException {
    private final int code = 103;

    public PersistenceToFileException(String fileName, String operation,String message ) {

        super(String.format("Unable perform %s, %s, %s", fileName, operation, message));
    }


    @Override
    public int getCode() {
        return code;
    }
}
