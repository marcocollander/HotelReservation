# Zakres zmiennych w kontekście if-else

```java

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Dodaj nowego gościa.");
        System.out.println("2. Dodaj nowy pokój.");
        System.out.println("3. Wyszukaj gościa."); 
        System.out.println("Wybierz opcję: ");

        int option = input.nextInt();

        if (option == 1) {
            int x = 22;
            System.out.println("Wybrano opcję 1.");
        } else if (option == 2) {
            System.out.println("Wybrano opcję 2.");
//            System.out.println(x); Ta zmienna nie będzie widoczna, ze względu na istnienie zakresu blokowego.
        } else if (option == 3) {
            System.out.println("Wybrano opcję 3.");
        } else {
            System.out.println("Wybrano niepoprawną akcję.");
        }

        input.close();
    }
}
```
