import java.io.IOException;
import java.util.Scanner;

/**
 * Класс управляет командами коллекции
 */
public class ManagerCommand {
    private Implementation implement;
    private String userCommand = "";

    public ManagerCommand(Implementation implement) {
        this.implement = implement;
    }

    public void interactiveMod() throws IOException {
        try (Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                String[] finalUserCommand = userCommand.trim().split(" ", 3);
                try {
                    switch (finalUserCommand[0]) {
                        case "":
                            break;
                        case "help":
                            implement.help();
                            break;
                        case "info":
                            implement.info();
                            break;
                        case "show":
                            implement.show();
                            break;
                        case "add":
                            implement.add();
                            break;
                        case "update":
                            implement.update(finalUserCommand[1]);
                            break;
                        case "remove_by_id":
                            implement.remove_by_id(finalUserCommand[1]);
                            break;
                        case "clear":
                            implement.clear();
                            break;
                        case "save":
                            try {
                                implement.save();
                            } catch (Exception e) {
                                System.out.println("Нет прав для записи");
                            }
                            break;
                        case "execute_script":
                            implement.execute_script(finalUserCommand[1]);
                            break;
                        case "add_if_max":
                            implement.add_if_max();
                            break;
                        case "add_if_min":
                            implement.add_if_min();
                            break;
                        case "remove_greater":
                            implement.remove_greater(finalUserCommand[1]);
                            break;
                        case "remove_any_by_students_count":
                            implement.remove_any_by_students_count(finalUserCommand[1]);
                            break;
                        case "print_field_ascending_students_count":
                            implement.print_field_ascending_students_count();
                            break;
                        case "print_field_descending_form_of_education":
                            implement.print_field_descending_form_of_education();
                            break;
                        case "exit":
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Неизвестная команда. Введите снова");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Отсутствует аргумент");
                }
            }
        }
    }
}