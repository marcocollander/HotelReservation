package net.raubuc.ui.text;

import net.raubuc.exceptions.OnlyNumberException;
import net.raubuc.exceptions.PersistenceToFileException;
import net.raubuc.exceptions.WrongOptionException;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.guest.Guest;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.objects.reservation.Reservation;
import net.raubuc.objects.reservation.ReservationService;
import net.raubuc.objects.room.Room;
import net.raubuc.objects.room.RoomService;
import net.raubuc.utils.Properties;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUI {
    private final GuestService guestService = ObjectPool.getGuestService();
    private final RoomService roomService = ObjectPool.getRoomService();
    private final ReservationService reservationService = ObjectPool.getReservationService();


    public void showSystemInfo() {
        System.out.println("\n=========================\n");
        System.out.printf("Witam w systemie rezerwacji dla hotelu %s.\n", Properties.HOTEL_NAME);
        System.out.println("Aktualna wersja systemu: " + Properties.SYSTEM_VERSION);
        System.out.println("Wersja developerska: " + Properties.IS_DEVELOPER_VERSION);
        System.out.println("\n=========================\n");
    }

    public void showMainMenu() {
        System.out.println("Trwa ładowanie danych...");
        guestService.readAllGuests();
        roomService.readAllRooms();
        reservationService.readAllReservations();

        try {
            performAction();

        } catch (WrongOptionException | OnlyNumberException | PersistenceToFileException e) {
            System.out.println("Wystąpił niespodziewany błąd");
            System.out.println("Kod błędu: " + e.getCode());
            System.out.println("Komunikat błędu: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Wystąpił niespodziewany błąd");
            System.out.println("Unknown error");
            System.out.println("Komunikat błędu: " + e.getMessage());

        } finally {
            System.out.println("Zakończono pracę aplikacji");
        }
    }

    private void performAction() throws WrongOptionException {
        Scanner input = new Scanner(System.in);

        int option = -1;

        while (option != 0) {
            option = getActionFromUser(input);

            if (option == 0) {
                guestService.saveAllGuests();
                roomService.saveAllRooms();
                reservationService.saveAllReservations();
                System.out.println("Wyjście z aplikacji\n oraz zapisywanie danych");
            } else if (option == 1) {
                readNewGuestData(input);
            } else if (option == 2) {
                readNewRoomData(input);
            } else if (option == 3) {
                showAllGuests();
            } else if (option == 4) {
                showAllRooms();
            } else if (option == 5) {
                removeGuest(input);
            } else if (option == 6) {
                editGuest(input);
            } else if (option == 7) {
                removeRoom(input);
            } else if (option == 8) {
                editRoom(input);
            } else if (option == 9) {
                createReservation(input);
            } else {
                throw new WrongOptionException("Podano złe opcje w menu głównym");
            }
        }

        input.close();
    }

    private int getActionFromUser(Scanner in) {
        System.out.println("Wybierz jedną z opcji: ");
        System.out.println("0 - Wyjście z aplikacji ");
        System.out.println("1 - Dodaj nowego gościa.");
        System.out.println("2 - Dodaj nowy pokój.");
        System.out.println("3 - Pokaż wszystkich gości.");
        System.out.println("4 - Pokaż wszystkie pokoje.");
        System.out.println("5 - Usuń gościa.");
        System.out.println("6 - Edytuj dane gościa.");
        System.out.println("7 - Usuń pokój.");
        System.out.println("8 - Edytuj pokój.");
        System.out.println("9 - Dokonaj rezerwacji.");

        int option;

        try {
            option = in.nextInt();
        } catch (InputMismatchException e) {
            throw new OnlyNumberException(
                    "Użyj tylko liczb całkowitych z zakresu [0,9] w menu głównym"
            );
        }

        return option;
    }

    private void readNewGuestData(Scanner input) {
        System.out.println("Tworzymy nowego gościa.");
        try {
            System.out.println("Podaj imię: ");
            String firstName = input.next();
            System.out.println("Podaj nazwisko: ");
            String lastName = input.next();
            System.out.println("Podaj wiek: ");
            int age = input.nextInt();
            System.out.println("Podaj płeć (1. Mężczyzna, 2. Kobieta)");
            int genderOption = input.nextInt();

            if (genderOption != 1 && genderOption != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            boolean isMale = genderOption == 1;

            Guest newGuest = guestService.createNewGuest(firstName, lastName, age, isMale);
            System.out.println("Dodano nowego gościa: " + newGuest.getInfo() + "\n");

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when choosing gender");
        }
    }

    private void readNewRoomData(Scanner input) {
        System.out.println("Tworzymy nowy pokój.");

        try {
            System.out.println("Numer: ");
            int number = input.nextInt();
            int[] bedTypeOptions = readBedType(input);
            Room newRoom = roomService.createNewRoom(number, bedTypeOptions);
            System.out.println("Dodano nowy pokój: " + newRoom.getInfo() + "\n");

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when creating new room");
        } catch (WrongOptionException e) {
            throw new RuntimeException(e);
        }
    }

    private int[] readBedType(Scanner input) {
        System.out.println("Ile łóżek w pokoju:");
        int bedNumber = input.nextInt();
        int[] bedTypeOptions = new int[bedNumber];

        for (int i = 0; i < bedNumber; i++) {

            System.out.println("Typy łóżek: ");
            System.out.println("\t1. Pojedyncze");
            System.out.println("\t2. Podwójne");
            System.out.println("\t3. Królewskie");

            bedTypeOptions[i] = input.nextInt();
        }

        return bedTypeOptions;
    }

    private void showAllGuests() {
        List<Guest> allGuests = guestService.getAllGuests();

        for (Guest guest : allGuests) {
            System.out.println(guest.getInfo());
        }
    }

    private void showAllRooms() {
        List<Room> allRooms = roomService.getAllRooms();

        for (Room room : allRooms) {
            System.out.println(room.getInfo());
        }
    }

    private void removeGuest(Scanner input) {
        System.out.println("Podaj ID gościa do usunięcia");

        try {
            int id = input.nextInt();
            this.guestService.removeGuest(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void editGuest(Scanner input) {
        System.out.println("Podaj ID gościa do edycji");
        try {
            int id = input.nextInt();

            System.out.println("Podaj imię: ");
            String firstName = input.next();
            System.out.println("Podaj nazwisko: ");
            String lastName = input.next();
            System.out.println("Podaj wiek: ");

            int age = input.nextInt();

            System.out.println("Podaj płeć (1. Mężczyzna, 2. Kobieta)");

            int genderOption = input.nextInt();

            if (genderOption != 1 && genderOption != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            boolean isMale = genderOption == 1;

            guestService.editGuest(id, firstName, lastName, age, isMale);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when editing guest");
        }
    }

    private void editRoom(Scanner input) {
        System.out.println("Podaj ID pokoju do edycji");
        try {
            int id = input.nextInt();
            System.out.println("Numer: ");
            int number = input.nextInt();
            int[] bedTypes = readBedType(input);
            roomService.editRoom(id, number, bedTypes);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when editing room");
        }
    }

    private void removeRoom(Scanner input) {
        System.out.println("Podaj ID pokoju do usunięcia");
        try {
            int id = input.nextInt();
            this.roomService.removeRoom(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void createReservation(Scanner input) {
        System.out.println("Od kiedy (DD.MM.YYYY):");
        String fromAsString = input.next();
        LocalDate from = LocalDate.parse(fromAsString, Properties.DATE_FORMATTER);

        System.out.println("Do kiedy (DD.MM.YYYY):");
        String toAsString = input.next();
        LocalDate to = LocalDate.parse(toAsString, Properties.DATE_FORMATTER);

        System.out.println("ID Pokoju:");
        int roomId = input.nextInt();

        System.out.println("ID Gościa:");
        int guestId = input.nextInt();

        //TODO Handle null reservation?
        Reservation res = this.reservationService.createNewReservation(from, to, roomId, guestId);

        if (res != null) {
            System.out.println("Udało się stworzyć rezerwację");
        }
    }
}
