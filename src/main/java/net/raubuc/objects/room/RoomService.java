package net.raubuc.objects.room;

import net.raubuc.exceptions.WrongOptionException;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.room.dto.RoomDTO;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private final RoomRepository repository = ObjectPool.getRoomRepository();
    private final static RoomService instance = new RoomService();

    private RoomService() {
    }

    public static RoomService getInstance() {
        return instance;
    }


    public Room createNewRoom(int number, List<String> bedTypesAsString) {
        BedType[] bedTypes = new BedType[bedTypesAsString.size()];
        for (int i = 0; i < bedTypesAsString.size(); i = i + 1) {
            BedType bedType;
            if (bedTypesAsString.get(i).equals("Pojedyncze")) {
                bedType = BedType.SINGLE;
            } else if (bedTypesAsString.get(i).equals("Podwójne")) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesAsString.get(i).equals("Królewskie")) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        return repository.createNewRoom(number, bedTypes);
    }

    public Room createNewRoom(int numberRoom, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i = 0; i < bedTypesOptions.length; i++) {
            if (bedTypesOptions[i] == 1) {
                bedTypes[i] = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedTypes[i] = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedTypes[i] = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting a bed type");
            }
        }

        return repository.createNewRoom(numberRoom, bedTypes);
    }

    public List<Room> getAllRooms() {
        return repository.getAllRooms();
    }

    public void saveAllRooms() {
        repository.saveRooms();
    }

    public void readAllRooms() {
        repository.loadRooms();
    }

    public void editRoom(int id, int number, int[] bedTypesOptions) {
        BedType[] bedTypes = new BedType[bedTypesOptions.length];
        for (int i = 0; i < bedTypesOptions.length; i = i + 1) {
            BedType bedType;
            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        repository.edit(id, number, bedTypes);
    }

    public void removeRoom(int id) {
        repository.remove(id);
    }

    public Room getRoomById(int roomId) {
        return repository.getById(roomId);
    }

    public List<RoomDTO> getAllRoomsDTO() {
        List<RoomDTO> roomDTOS = new ArrayList<>();
        List<Room> rooms = repository.getAllRooms();

        for (Room room : rooms) {
            roomDTOS.add(room.generateDTO());
        }

        return roomDTOS;
    }
}
