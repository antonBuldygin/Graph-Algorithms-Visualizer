import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        Deque<Integer> staks = new ArrayDeque<>();

        for (int i = 0; i < number; i++) {
            staks.addFirst(scanner.nextInt());
        }

        // put your code here
        staks.stream().forEach(s -> System.out.println(staks.pop()));
    }
}