package net.raubuc.objects.guest;

import net.raubuc.objects.guest.dto.GuestDTO;

public class Guest {
    private int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Gender gender;


    Guest(int id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return String.format("%d %s %s (%d) (%s)",
                this.id,
                this.firstName,
                this.lastName,
                this.age,
                this.gender
        );
    }

    String toCSV() {
        return String.format("%d,%s,%s,%d,%s,%s",
                this.id,
                this.firstName,
                this.lastName,
                this.age,
                this.gender,
                System.lineSeparator()
        );
    }

    public GuestDTO getGuestDTO() {
        String gender = "Mężczyzna";
        if (this.gender.equals(Gender.FEMALE)) {
            gender = "Kobieta";
        }
        return new GuestDTO(this.id, this.firstName, this.lastName, this.age, gender);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
