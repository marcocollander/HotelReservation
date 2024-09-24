package net.raubuc.ui.text;

import net.raubuc.objects.guest.Guest;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.exceptions.OnlyNumberException;
import net.raubuc.exceptions.WrongOptionException;
import net.raubuc.objects.room.Room;
import net.raubuc.objects.room.RoomService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUI {
    private final GuestService guestService = new GuestService();
    private final RoomService roomService = new RoomService();

    public void showSystemInfo(String hotelName, int systemVersion, boolean isDeveloperVersion) {
        System.out.println("\n=========================\n");
        System.out.printf("Witam w systemie rezerwacji dla hotelu %s.\n", hotelName);
        System.out.println("Aktualna wersja systemu: " + systemVersion);
        System.out.println("Wersja developerska: " + isDeveloperVersion);
        System.out.println("\n=========================\n");
    }

    public void showMainMenu() {
        System.out.println("Trwa ładowanie danych...");
        guestService.readAllGuests();

        try {
            performAction();

        } catch (WrongOptionException | OnlyNumberException e) {
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
                System.out.println("Wyjście z aplikacji");
            } else if (option == 1) {
                readNewGuestData(input);
            } else if (option == 2) {
                readNewRoomData(input);
            } else if (option == 3) {
                showAllGuests();
            } else if (option == 4) {
                showAllRooms();
            }
            else {
                throw new WrongOptionException("Wrong option in main menu");
            }
        }

        input.close();
    }



    private int getActionFromUser(Scanner in) {
        System.out.println("Wybierz opcję: ");
        System.out.println("0 - Wyjście z aplikacji ");
        System.out.println("1 - Dodaj nowego gościa.");
        System.out.println("2 - Dodaj nowy pokój.");
        System.out.println("3 - Pokaż wszystkich gości.");
        System.out.println("4 - Pokaż wszystkie pokoje.");

        int option;

        try {
            option = in.nextInt();
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers in the range [0, 4] ");
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
            System.out.println(newGuest.getInfo());

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
            System.out.println(newRoom.getInfo());

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
}
