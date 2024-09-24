package net.raubuc.objects.room;

import net.raubuc.exceptions.WrongOptionException;

import java.util.List;

public class RoomService {
    private final RoomRepository repository = new RoomRepository();

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
}


