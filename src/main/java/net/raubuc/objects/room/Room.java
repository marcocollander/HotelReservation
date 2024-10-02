package net.raubuc.objects.room;


import net.raubuc.objects.room.dto.RoomDTO;

public class Room {
    private final int id;
    private final int number;
    private final BedType[] bedTypes;

    Room(int id, int number, BedType[] bedTypes) {
        this.id = id;
        this.number = number;
        this.bedTypes = bedTypes;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        StringBuilder bedInfo = new StringBuilder("Rodzaje łóżek w pokoju:");
        for (BedType bedType : bedTypes) {
            bedInfo.append(" ").append(bedType);
        }

        return String.format("%d Numer pokoju: %d %s",
                this.id,
                this.number,
                bedInfo
        );
    }

    String toCSV() {
        String[] bedTypesAsStrings = new String[this.bedTypes.length];

        for (int i = 0; i < this.bedTypes.length; i++) {
            bedTypesAsStrings[i] = this.bedTypes[i].toString();
        }

        String bedTypes = String.join(",", bedTypesAsStrings);

        return String.format("%d,%d,%s,%s", this.id, this.number, bedTypes, System.lineSeparator());
    }

    private String[] getBedsAsStrings() {
        String[] bedTypesAsStrings = new String[this.bedTypes.length];
        for (int i = 0; i < this.bedTypes.length; i++) {
            bedTypesAsStrings[i] = this.bedTypes[i].toString();
        }

        return bedTypesAsStrings;
    }

    public RoomDTO generateDTO() {
        String[] bedTypesAsStrings = getBedsAsStrings();
        String bedTypes = String.join(",", bedTypesAsStrings);

        int roomSize = 0;

        for (BedType bedType : this.bedTypes) {
            roomSize += bedType.getSize();
        }

        return new RoomDTO(this.id, this.number, bedTypes, this.bedTypes.length, roomSize);
    }


    public int getNumber() {
        return number;
    }
}