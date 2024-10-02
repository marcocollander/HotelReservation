package net.raubuc.objects.room;

public enum BedType {
    SINGLE(1),
    DOUBLE(2),
    KING_SIZE(2);


    private final int size;

    BedType(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}