package net.raubuc.objects.guest;

import net.raubuc.exceptions.PersistenceToFileException;
import net.raubuc.utils.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private static final List<Guest> guests = new ArrayList<>();
    private final static GuestRepository instance = new GuestRepository();

    private GuestRepository() {
    }

    public static GuestRepository getInstance() {
        return instance;
    }

    Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest guest = new Guest(findNewId(), firstName, lastName, age, gender);
        guests.add(guest);

        return guest;
    }

    private int findNewId() {
        int max = 0;
        for (Guest guest : guests) {
            if (guest.getId() > max) {
                max = guest.getId();
            }
        }
        return max + 1;
    }

    void addExistingGuest(int id, String firstName, String lastName, int age, Gender gender) {
        Guest gest = new Guest(id, firstName, lastName, age, gender);
        guests.add(gest);
    }

    public List<Guest> getGuests() {
        return guests;
    }

    void saveGuests() {
        String fileName = "guests.csv";
        Path filePath = Paths.get(Properties.DATA_DIRECTORY.toString(), fileName);
        StringBuilder sb = new StringBuilder();

        for (Guest guest : guests) {
            sb.append(guest.toCSV());
        }

        try {
            Files.writeString(filePath, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(fileName, "write", "guests data");
        }
    }

    void loadGuests() {
        String fileName = "guests.csv";
        Path filePath = Paths.get(Properties.DATA_DIRECTORY.toString(), fileName);

        if (!Files.exists(filePath)) {
            return;
        }

        try {
            String data = Files.readString(filePath, StandardCharsets.UTF_8);

            if (data != null && !data.isEmpty()) {
                String[] rows = data.split(System.lineSeparator());
                for (String row : rows) {
                    String[] cols = row.split(",");
                    addExistingGuest(
                            Integer.parseInt(cols[0]),
                            cols[1], cols[2],
                            Integer.parseInt(cols[3]),
                            Gender.valueOf(cols[4])
                    );
                }
            } else {
                System.out.println("Brak danych\n");
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(fileName, "read", "guests data");
        }
    }

    public void remove(int id) {
        int guestToBeRemovedIndex = -1;

        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getId() == id) {
                guestToBeRemovedIndex = i;
                break;
            }
        }

        if (guestToBeRemovedIndex > -1) {
            guests.remove(guestToBeRemovedIndex);
        }
    }

    public void edit(int id, String firstName, String lastName, int age, Gender gender) {
        this.remove(id);
        this.addExistingGuest(id, firstName, lastName, age, gender);
    }

    public Guest findById(int id) {
        for (Guest guest : guests) {
            if (guest.getId() == id) {
                return guest;
            }
        }

        return null;
    }
}
