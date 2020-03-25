import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *@author Daniil Popov
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("\nКоллекция не сохранена");
            }
        });
        System.out.println("Введите путь до файла");
        ///home/s282747/Proga5/Groups.json
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = in.readLine().trim();
        ManagerCommand managerCommand = new ManagerCommand(new Implementation(new File(firstLine)));
        managerCommand.interactiveMod();
    }
}
