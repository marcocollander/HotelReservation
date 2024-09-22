package tasks;

public class MinInArray {
    public static void main(String[] args) {
        int[] data = {3, 2, 1, 6, 3, -1};

        System.out.println("Najmniejsza wartość w tablicy to: " + findMin(data));
    }

    private static int findMin(int[] data) {
        int min = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }

}
