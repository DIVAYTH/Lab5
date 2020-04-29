import java.io.*;
import java.util.NoSuchElementException;

/**
 * @author Daniil Popov
 */
public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    System.out.println("\nВыход...");
                }
            });
            ManagerCommand managerCommand = new ManagerCommand(new Implementation(new File(args[0])));
            managerCommand.interactiveMod();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы не ввели имя файла");
        } catch (NoSuchElementException e) {
        }
    }
}
