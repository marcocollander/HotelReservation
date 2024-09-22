import java.util.Arrays;

public class Room {

    private final int number;
    private final BedType[] bedType;

    public Room(int number, BedType[] bedType) {
        this.number = number;
        this.bedType = bedType;
    }

    public String getInfo() {
        System.out.println("Rodzaje łóżek w pokoju: ");
        for (BedType bedType : bedType) {
            System.out.println(bedType);
        }

        return String.format("Dodano nowy pokój - numer %d (%s)", this.number, Arrays.toString(this.bedType));
    }
}