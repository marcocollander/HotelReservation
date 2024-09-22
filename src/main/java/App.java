import exceptions.WrongOptionException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws WrongOptionException {
        String hotelName = "Overlook";
        int systemVersion = 1;
        boolean isDeveloperVersion = true;

        showSystemInfo(hotelName, systemVersion, isDeveloperVersion);

        try {
            performAction();

        } catch (WrongOptionException e) {
            System.out.println("Wystąpił niespodziewany błąd");
            System.out.println("Kod błędu: " + e.getCode());
            System.out.println("Komunikat błędu: " + e.getMessage());
        }
    }

    private static void performAction() throws WrongOptionException {
        Scanner input = new Scanner(System.in);

        int option = getActionFromUser(input);

        if (option == 1) {
            System.out.println("Tworzymy nowego gościa.");
            Guest newGuest = createNewGuest(input);
        } else if (option == 2) {
            Room newRoom = createNewRoom(input);
        } else if (option == 3) {
            System.out.println("Wybrano opcję 3.");
        } else {
            throw new WrongOptionException("Wrong option in main menu");
        }

        input.close();
    }

    public static void showSystemInfo(String hotelName, int systemVersion, boolean isDeveloperVersion) {
        System.out.print("Witam w systemie rezerwacji dla hotelu " + hotelName);
        System.out.println("Aktualna wersja systemu: " + systemVersion);
        System.out.println("Wersja developerska: " + isDeveloperVersion);
        System.out.println("\n=========================\n");
    }

    public static int getActionFromUser(Scanner in) {
        System.out.println("1. Dodaj nowego gościa.");
        System.out.println("2. Dodaj nowy pokój.");
        System.out.println("3. Wyszukaj gościa.");
        System.out.println("Wybierz opcję: ");

        int option = 0;

        try {
            option = in.nextInt();

        } catch (Exception e) {
            System.out.println("Niepoprawne dane wejściowe, wprowadź liczbę.");
            e.printStackTrace();
        }

        return option;
    }

    public static Guest createNewGuest(Scanner input) {
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

            Gender gender = null;

            if (genderOption == 1) {
                gender = Gender.MALE;
            } else if (genderOption == 2) {
                gender = Gender.FEMALE;
            } else {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            Guest newGuest = new Guest(firstName, lastName, age, gender);
            System.out.println(newGuest.getInfo());
            return newGuest;

        } catch (Exception e) {
            System.out.println("Zły wiek, używaj liczb.");
            e.printStackTrace();
            return null;
        }
    }

    public static Room createNewRoom(Scanner input) {
        System.out.println("Tworzymy nowy pokój.");

        try {
            System.out.println("Numer: ");
            int number = input.nextInt();
            BedType[] bedTypes = chooseBedType(input);
            Room newRoom = new Room(number, bedTypes);
            System.out.println(newRoom.getInfo());
            return newRoom;

        } catch (Exception e) {
            throw new InputMismatchException ("Wrong characters used instead of numbers");
        }
    }

    private static BedType[] chooseBedType(Scanner input) throws WrongOptionException {
        System.out.println("Ile łóżek w pokoju:");
        int bedNumber = input.nextInt();
        BedType[] bedTypes = new BedType[bedNumber];

        for (int i = 0; i < bedNumber; i++) {

            System.out.println("Typy łóżek: ");
            System.out.println("\t1. Pojedyncze");
            System.out.println("\t2. Podwójne");
            System.out.println("\t3. Królewskie");

            BedType bedType = null;
            int bedTypeOption = input.nextInt();

            if (bedTypeOption == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypeOption == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypeOption == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when bed selection");
            }

            bedTypes[i] = bedType;
        }

        return bedTypes;
    }
}
