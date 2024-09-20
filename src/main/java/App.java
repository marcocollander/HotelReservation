import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String hotelName = "Overlook";
        int systemVersion = 2;
        boolean isDeveloperVersion = true;

        System.out.print("Witam w systemie rezerwacji dla hotelu: " + hotelName);
        System.out.print("Aktualna wersja systemu: " + systemVersion);
        System.out.print("Wersja developerska: " + isDeveloperVersion);


        System.out.println("\n***************************\n");

        Scanner input = new Scanner(System.in);

        System.out.println("1. Dodaj nowego gościa.");
        System.out.println("2. Dodaj nowy pokój.");
        System.out.println("3. Wyszukaj gościa.");
        System.out.println("Wybierz opcję: ");

        int option = 0;

        try {
            option = input.nextInt();
        } catch (Exception e) {
            System.out.println("Niepoprawne dane wejściowe, wprowadź liczbę.");
            e.printStackTrace();
        }

        if (option == 1) {
            System.out.println("Tworzymy nowego gościa.");
            try {
                System.out.println("Podaj imię: ");
                String firstName = input.next();
                System.out.println("Podaj nazwisko: ");
                String lastName = input.next();
                System.out.println("Podaj wiek: ");
                int age = Integer.parseInt(input.next());
            } catch (Exception e){

            }


            Guest createdGuest = new Guest("Marco", "Collander", 44);
        } else if (option == 2) {
            System.out.println("Wybrano opcję 2.");
        } else if (option == 3) {
            System.out.println("Wybrano opcję 3.");
        } else {
            System.out.println("Wybrano niepoprawną akcję.");
        }

        input.close();
    }
}
