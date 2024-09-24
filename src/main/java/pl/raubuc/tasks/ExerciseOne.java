package pl.raubuc.tasks;
/*
*  Stwórz program, który wylosuje liczbę z zakresu od 0 do 10, a
*  następnie zapyta użytkownika o jej zgadnięcie. Jeśli użytkownik
*  zgadnie, wówczas wyświetli się komunikat o sukcesie. W innym
*  przypadku komunikat o niepowodzeniu oraz dodatkowo wyświetli
*  na ekranie poszukiwaną liczbę.
*/


import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ExerciseOne {
    public static void main(String[] args) {
        Random rand = new Random();
        int number = rand.nextInt(11);
        System.out.println("Wylosowano liczbę z zakresu od 0 do 10. Zgadnij jaką? ");
        Scanner sc = new Scanner(System.in);

        int answer;

        try {
            answer = sc.nextInt();
            if(answer == number) {
                System.out.println("Sukces");
            } else {
                System.out.println("Nie zgadłeś. Wylosowana liczba to: " + number);
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
            System.out.println("Nie podałeś liczby.");
        }
    }
}
