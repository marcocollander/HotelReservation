package net.raubuc.objects.room.dto;

public class RoomDTO {
    private final int id;
    private final int number;
    private final String beds;
    private final int bedsCount;
    private final int roomSize;

    public RoomDTO(int id, int number, String beds, int bedsCount, int roomSize) {
        this.id = id;
        this.number = number;
        this.beds = beds;
        this.bedsCount = bedsCount;
        this.roomSize = roomSize;
    }

    public int getId() {
        return id;
    }

    public String getBeds() {
        return beds;
    }

    public int getNumber() {
        return number;
    }

    public int getBedsCount() {
        return bedsCount;
    }

    public int getRoomSize() {
        return roomSize;
    }
}
