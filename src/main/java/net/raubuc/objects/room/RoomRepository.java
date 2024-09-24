package net.raubuc.objects.room;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final List<Room> rooms = new ArrayList<>();

    Room createNewRoom(int numberRoom, BedType[] bedTypes) {
        Room room = new Room(numberRoom, bedTypes);
        rooms.add(room);
        return room;
    }

    public List<Room> getAllRooms() {
        return rooms;
    }
}
