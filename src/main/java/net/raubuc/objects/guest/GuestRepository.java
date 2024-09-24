package net.raubuc.objects.guest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private final List<Guest> guests = new ArrayList<>();

    Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest guest = new Guest(firstName, lastName, age, gender);
        guests.add(guest);

        return guest;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    void saveGuests() {
        String fileName = "guests.csv";

        Path filePath = Paths.get(System.getProperty("user.home"), "reservation_system", fileName);
        StringBuilder sb = new StringBuilder();

        for (Guest guest : guests) {
            sb.append(guest.toCSV());
        }

        try {
            Path reservationSystemDirPath = Paths.get(System.getProperty("user.home"), "reservation_system");

            if (!Files.isDirectory(reservationSystemDirPath)) {
                Files.createDirectory(reservationSystemDirPath);
            }
            Files.writeString(filePath, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadGuests() {
        String fileName = "guests.csv";
        Path filePath = Paths.get(
                System.getProperty("user.home"),
                "reservation_system",
                fileName
        );

        String data;
        try {
            data = Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            if (data != null && !data.isEmpty()) {
                String[] rows = data.split(System.lineSeparator());

                for (String row : rows) {
                    String[] cols = row.split(",");
                    createNewGuest(
                            cols[0],
                            cols[1],
                            Integer.parseInt(cols[2]),
                            Gender.valueOf(cols[3])
                    );
                }
            } else {
                System.out.println("Brak danych\n");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }
}
