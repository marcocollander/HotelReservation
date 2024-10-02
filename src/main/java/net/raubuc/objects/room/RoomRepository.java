package net.raubuc.objects.room;

import net.raubuc.exceptions.PersistenceToFileException;
import net.raubuc.utils.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final List<Room> rooms = new ArrayList<>();
    private final static RoomRepository instance = new RoomRepository();

    private RoomRepository() {
    }

    public static RoomRepository getInstance() {
        return instance;
    }

    Room createNewRoom(int numberRoom, BedType[] bedTypes) {
        Room room = new Room(findNewId(), numberRoom, bedTypes);
        rooms.add(room);
        return room;
    }

    private int findNewId() {
        int max = 0;
        for (Room room : rooms) {
            if (room.getId() > max) {
                max = room.getId();
            }
        }
        return max + 1;
    }

    void addExistingRoom(int id, int number, BedType[] bedTypes) {
        Room room = new Room(id, number, bedTypes);
        rooms.add(room);
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    void saveRooms() {
        String fileName = "rooms.csv";
        Path filePath = Paths.get(Properties.DATA_DIRECTORY.toString(), fileName);
        StringBuilder sb = new StringBuilder();

        for (Room room : rooms) {
            sb.append(room.toCSV());
        }

        try {
            Files.writeString(filePath, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(fileName, "save", "room data");
        }
    }

    void loadRooms() {
        String fileName = "rooms.csv";
        Path filePath = Paths.get(Properties.DATA_DIRECTORY.toString(), fileName);

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            return;
        }

        try {
            String data = Files.readString(filePath, StandardCharsets.UTF_8);

            if (data != null && !data.isEmpty()) {
                String[] rows = data.split(System.lineSeparator());

                for (String row : rows) {
                    String[] cols = row.split(",");
                    int id = Integer.parseInt(cols[0]);
                    int number = Integer.parseInt(cols[1]);

                    BedType[] bedTypes = new BedType[cols.length - 2];
                    for (int i = 0; i < bedTypes.length; i++) {
                        bedTypes[i] = BedType.valueOf(cols[i + 2]);
                    }

                    addExistingRoom(id, number, bedTypes);
                }
            } else {
                System.out.println("Brak danych\n");
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(fileName, "load", "room data");
        }
    }

    public void remove(int id) {
        int roomToBeRemovedIndex = -1;
        for (int i = 0; i < this.rooms.size(); i++) {
            if (this.rooms.get(i).getId() == id) {
                roomToBeRemovedIndex = i;
                break;
            }
        }
        if (roomToBeRemovedIndex > -1) {
            rooms.remove(roomToBeRemovedIndex);
        }
    }

    public void edit(int id, int number, BedType[] bedTypes) {
        this.remove(id);
        this.addExistingRoom(id, number, bedTypes);
    }

    public Room getById(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }
}
