package net.raubuc.objects.guest;

import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.guest.dto.GuestDTO;

import java.util.ArrayList;
import java.util.List;

public class GuestService {
    private final static GuestRepository repository = ObjectPool.getGuestRepository();
    private final static GuestService instance = new GuestService();

    private GuestService() {
    }

    public static GuestService getInstance() {
        return instance;
    }

    public Guest createNewGuest(String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;

        if (isMale) {
            gender = Gender.MALE;
        }

        return repository.createNewGuest(firstName, lastName, age, gender);
    }

    public List<Guest> getAllGuests() {
        return repository.getGuests();
    }

    public void saveAllGuests() {
        repository.saveGuests();
    }

    public void readAllGuests() {
        repository.loadGuests();
    }

    public void removeGuest(int id) {
        repository.remove(id);
    }

    public void editGuest(int id, String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;
        if (isMale) {
            gender = Gender.MALE;
        }
        repository.edit(id, firstName, lastName, age, gender);
    }

    public Guest getGuestById(int id) {
        return repository.findById(id);
    }

    public List<GuestDTO> getGuestsAsDTO() {
        List<GuestDTO> result = new ArrayList<>();
        List<Guest> guests = repository.getGuests();

        for (Guest guest : guests) {
            GuestDTO dto = guest.getGuestDTO();
            result.add(dto);
        }
        return result;
    }
}
