import java.io.*;
import java.util.NoSuchElementException;

/**
 *@author Daniil Popov
 */
public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    System.out.println("\nКоллекция не сохранена");
                }
            });
            ManagerCommand managerCommand = new ManagerCommand(new Implementation(new File(args[0])));
            managerCommand.interactiveMod();
        }catch (NoSuchElementException e){
        }
    }
}
