package net.raubuc.objects.guest;

import java.util.List;

public class GuestService {
    private final GuestRepository repository = new GuestRepository();

    public Guest createNewGuest(String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;

        if(isMale) {
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
}
