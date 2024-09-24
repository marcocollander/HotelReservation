package net.raubuc.objects.room;

import java.util.Arrays;

public class Room {

    private final int number;
    private final BedType[] bedTypes;

    Room(int number, BedType[] bedTypes) {
        this.number = number;
        this.bedTypes = bedTypes;
    }

    public String getInfo() {
        return String.format(
                "Numer pokoju: %d typ łóżek: (%s)",
                this.number, Arrays.toString(this.bedTypes)
        );
    }
}