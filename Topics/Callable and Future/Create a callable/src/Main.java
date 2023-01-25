import java.util.Scanner;
import java.util.concurrent.Callable;

class CallableUtil {
    public static Callable<String> getCallable() {
        // implement method
        Callable<String> callab= ()->{
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
         return s;
        };
        return callab;
    }
}