import CollectionClasses.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Класс реализует обработку всех команд и хранение коллекции
 */
public class Implementation {
    boolean count = false;
    int oldID = 0;
    long id = 0;
    int j = 1;
    private PriorityQueue<StudyGroup> col;
    private Date initDate;
    private Gson gson;
    private HashMap<String, String> map;

    {
        gson = new Gson();
        col = new PriorityQueue<>();
        map = new HashMap<>();
        map.put("help", "вывести справку по доступным командам");
        map.put("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.");
        map.put("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        map.put("add", "добавить новый элемент в коллекцию");
        map.put("update id", "обновить значение элемента коллекции, id которого равен заданному");
        map.put("remove_by_id id", "удалить элемент из коллекции по его id");
        map.put("clear", "очистить коллекцию");
        map.put("save", "сохранить коллекцию в файл");
        map.put("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        map.put("exit", "завершить программу (без сохранения в файл)");
        map.put("add_if_max", "добавить новый элемент в коллекцию, если его значение height превышает значение height наибольшего элемента этой коллекции");
        map.put("add_if_min", "добавить новый элемент в коллекцию, если его значение height меньше, чем height у наименьшего элемента этой коллекции");
        map.put("remove_greater height", "удалить из коллекции все элементы, превышающие заданный");
        map.put("remove_any_by_students_count studentsCount", "удалить из коллекции один элемент, значение поля studentsCount которого эквивалентно заданному");
        map.put("print_field_ascending_students_count", "вывести значения поля studentsCount в порядке возрастания");
        map.put("print_field_descending_form_of_education", "вывести значения поля formOfEducation в порядке убывания");
    }

    /**
     * Метод загружает коллекцию и устанавливает дату инициаллизации
     *
     * @param file
     * @throws IOException
     */
    public Implementation(File file) throws IOException {
        this.load(file);
        this.initDate = new Date();
    }

    /**
     * Метод вызывает справку по командам
     */
    public void help() {
        System.out.println("Команды: ");
        map.forEach((key, value) -> System.out.println(key + " - " + value));
    }

    /**
     * Метод показывает информацию о коллекции
     */
    public void info() {
        System.out.println("Тип коллекции - PriorityQueue");
        System.out.println("Дата инициализации " + initDate);
        System.out.println("Размер коллекции " + col.size());
    }

    /**
     * Метод показывает элементы коллекции
     */
    public void show() {
        if (col.size() != 0) {
            col.forEach(p -> System.out.println(gson.toJson(p)));
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    /**
     * Метод осуществляющий выбор элемента типа Integer для add
     *
     * @param arr
     * @return
     */
    int checkInt(String arr) {
        Integer values = null;
        String str;
        Scanner scanner = new Scanner(System.in);
        while (values == null) {
            System.out.println("Введите значение " + arr);
            str = scanner.nextLine().trim();
            if (str.equals("")) {
                System.out.println(arr + " не может быть null. Введите снова");
            } else {
                try {
                    values = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    System.out.println("Вы ввели строку или число выходит за пределы. Введите снова");
                }
            }
        }
        return values;
    }

    /**
     * Метод осуществляющий выбор элемента типа Double для add
     *
     * @param arr
     * @return
     */
    Double checkDouble(String arr) {
        Double values = null;
        String str;
        Scanner scanner = new Scanner(System.in);
        while (values == null) {
            System.out.println("Введите значение " + arr);
            str = scanner.nextLine().trim();
            if (str.equals("")) {
                System.out.println(arr + " не может быть null. Введите снова");
            } else {
                try {
                    values = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    System.out.println("Вы ввели строку или число выходит за пределы. Введите снова");
                }
            }
        }
        return values;
    }

    /**
     * Метод осуществляющий выбор элемента типа String для add
     *
     * @param arr
     * @return
     */
    String checkName(String arr) {
        Scanner scanner = new Scanner(System.in);
        String str;
        do {
            System.out.println("Введите " + arr);
            str = scanner.nextLine().trim();
            if (str.equals("")) {
                System.out.println(arr + " не может быть null. Введите снова");
            }
        } while (str.equals(""));
        return str;
    }

    /**
     * Метод добавляет элементы коллекции
     * Считывая их с терминала
     */
    public void add() {
        StudyGroup studyGroup;
        FormOfEducation formOfEducation = null;
        Semester semesterEnum = null;
        Color hairColor = null;
        Country nationality = null;
        Scanner scanner = new Scanner(System.in);
        String[] arr = {"Имя группы", "x", "y", "studentsCount", "Имя главы группы", "x", "y", "z"};
        String name = checkName(arr[0]);
        Integer x = checkInt(arr[1]);
        Double y = checkDouble(arr[2]);
        Integer studentsCount = checkInt(arr[3]);

        String s_formOfEducation = "";
        do {
            try {
                System.out.println("Выберите форму обученя из: DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES;");
                s_formOfEducation = scanner.nextLine().trim().toUpperCase();
                if (s_formOfEducation.equals("")) {
                    formOfEducation = null;
                } else {
                    formOfEducation = FormOfEducation.valueOf(s_formOfEducation);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Такой формы обучения нет");
            }
        } while (!s_formOfEducation.equals("DISTANCE_EDUCATION") && !s_formOfEducation.equals("FULL_TIME_EDUCATION") &&
                !s_formOfEducation.equals("EVENING_CLASSES") && !s_formOfEducation.equals(""));

        String s_semesterEnum = "";
        do {
            try {
                System.out.println("Выберите семестр из: FIRST, THIRD, FIFTH, EIGHTH;");
                s_semesterEnum = scanner.nextLine().trim().toUpperCase();
                if (s_semesterEnum.equals("")) {
                    semesterEnum = null;
                } else {
                    semesterEnum = Semester.valueOf(s_semesterEnum);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Такого семестра нет");
            }
        } while (!s_semesterEnum.equals("FIRST") && !s_semesterEnum.equals("THIRD") && !s_semesterEnum.equals("FIFTH")
                && !s_semesterEnum.equals("EIGHTH") && !s_semesterEnum.equals(""));

        String per_name = checkName(arr[4]);

        String s_height;
        Integer height = null;
        while (height == null) {
            try {
                System.out.println("Введите рост:");
                s_height = scanner.nextLine().trim();
                if (s_height.equals("")) {
                    break;
                } else {
                    height = Integer.parseInt(s_height);
                    if (height <= 0) {
                        System.out.println("Рост не может быть меньше 0");
                        height = null;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
            }
        }

        String s_hairColor = "";
        do {
            try {
                System.out.println("Выберите цвет волос: RED, BLUE, YELLOW, ORANGE, WHITE");
                s_hairColor = scanner.nextLine().trim().toUpperCase();
                if (s_hairColor.equals("")) {
                    hairColor = null;
                } else {
                    hairColor = Color.valueOf(s_hairColor);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Такого цвета нет. Введите снова");
            }
        } while (!s_hairColor.equals("RED") && !s_hairColor.equals("BLUE") && !s_hairColor.equals("YELLOW") && !s_hairColor.equals("ORANGE")
                && !s_hairColor.equals("WHITE") && !s_hairColor.equals(""));

        String s_nationality = "";
        do {
            try {
                System.out.println("Выберите откуда она:  USA, CHINA, INDIA, VATICAN");
                s_nationality = scanner.nextLine().trim().toUpperCase();
                if (s_nationality.equals("")) {
                    System.out.println("nationality не может быть null. Введите снова");
                } else {
                    nationality = Country.valueOf(s_nationality);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Такой страны нет. Введите снова");
            }
        } while (!s_nationality.equals("USA") && !s_nationality.equals("CHINA") &&
                !s_nationality.equals("INDIA") && !s_nationality.equals("VATICAN"));

        Double loc_x = checkDouble(arr[5]);
        Integer loc_y = checkInt(arr[6]);
        Integer loc_z = checkInt(arr[7]);

        if (!count) {
            id++;
            studyGroup = new StudyGroup(id, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                    new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
        } else {
            studyGroup = new StudyGroup(oldID, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                    new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
            count = false;
        }
        col.add(studyGroup);
        System.out.println("Элемент коллекции добавлен");
    }

    /**
     * Метод осуществляющий выбор элемента типа Integer для add(перегруженного)
     *
     * @param arr
     * @param str
     * @return
     */
    Integer checkInt(String arr, String str) {
        Integer values;
        if (str.equals("")) {
            System.out.println(arr + " не может быть null. Введите снова");
            return null;
        } else {
            try {
                values = Integer.parseInt(str);
                return values;
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы. Введите снова");
                return null;
            }
        }
    }

    /**
     * Метод осуществляющий выбор элемента типа Double для add(перегруженного)
     *
     * @param arr
     * @param str
     * @return
     */
    Double checkDouble(String arr, String str) {
        Double values;
        if (str.equals("")) {
            System.out.println(arr + " не может быть null. Введите снова");
            return null;
        } else {
            try {
                values = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы. Введите снова");
                return null;
            }
        }
        return values;
    }

    /**
     * Метод осуществляющий выбор элемента типа String для add(перегруженного)
     *
     * @param arr
     * @param str
     * @return
     */
    String checkName(String arr, String str) {
        String name;
        if (str.equals("")) {
            System.out.println(arr + " не может быть null. Введите снова");
            return null;
        } else {
            name = str;
            return name;
        }
    }

    public void add(String str1, String str2, String str3, String str4, String str5, String str6, String str7,
                    String str8, String str9, String str10, String str11, String str12, String str13) {
        StudyGroup studyGroup;
        FormOfEducation formOfEducation;
        Semester semesterEnum;
        Color hairColor;
        Country nationality;
        String[] arr = {"Имя группы", "x", "y", "studentsCount", "Имя главы группы", "x", "y", "z"};
        String name;
        int x;
        double y;
        int studentsCount;
        String per_name;
        double loc_x;
        int loc_y;
        int loc_z;
        try {
            name = checkName(arr[0], str1);
            x = checkInt(arr[1], str2);
            y = checkDouble(arr[2], str3);
            studentsCount = checkInt(arr[3], str4);
            per_name = checkName(arr[4], str7);
            loc_x = checkDouble(arr[5], str11);
            loc_y = checkInt(arr[6], str12);
            loc_z = checkInt(arr[7], str13);
        } catch (NullPointerException e) {
            System.out.println("Ошибка при добавлении элемента");
            return;
        }

        try {
            if (str5.equals("")) {
                formOfEducation = null;
            } else {
                formOfEducation = FormOfEducation.valueOf(str5);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Такой формы обучения нет");
            return;
        }

        try {
            if (str6.equals("")) {
                semesterEnum = null;
            } else {
                semesterEnum = Semester.valueOf(str6);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Такого семестра нет");
            return;
        }

        Integer height;
        try {
            if (str8.equals("")) {
                height = null;
            } else {
                height = Integer.parseInt(str8);
                if (height <= 0) {
                    System.out.println("Рост не модет быть меньше 0. Коллекция не сохранена");
                    return;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Коллекция не сохранена");
            return;
        }

        try {
            if (str9.equals("")) {
                hairColor = null;
            } else {
                hairColor = Color.valueOf(str9);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Такого цвета нет. Введите снова");
            return;
        }

        try {
            if (str10.equals("")) {
                System.out.println("nationality не может быть null. Введите снова");
                return;
            } else {
                nationality = Country.valueOf(str10);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Такой страны нет. Введите снова");
            return;
        }

        if (!count) {
            id++;
            studyGroup = new StudyGroup(id, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                    new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
        } else {
            studyGroup = new StudyGroup(oldID, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                    new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
            count = false;
        }
        col.add(studyGroup);
        System.out.println("Элемент коллекции добавлен");
    }

    /**
     * Метод обновляет значение элемента по его id
     *
     * @param str
     */
    public void update(String str) {
        count = true;
        try {
            if (!(col.size() == 0)) {
                int idNew = Integer.parseInt(str);
                if (col.removeIf(col -> col.getId() == idNew)) {
                    oldID = idNew;
                    add();
                } else {
                    System.out.println("Элемента с таким id нет");
                }
            } else {
                System.out.println("Коллекция пуста");
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
        }
    }

    /**
     * Метод удаляет элемент по его id
     *
     * @param str
     */
    public void remove_by_id(String str) {
        try {
            if (!(col.size() == 0)) {
                int id = Integer.parseInt(str);
                if (col.removeIf(col -> col.getId() == id)) {
                    System.out.println("Элемент удален");
                } else System.out.println("Нет элемента с таким id");
            } else {
                System.out.println("Коллекция пуста");
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
        }
    }

    /**
     * Метод очищает коллекцию
     */
    public void clear() {
        col.clear();
        System.out.println("Коллекция очищена");
    }

    /**
     * Метод сохранят коллекцию в файл NewGroups.json
     *
     * @throws IOException
     */
    public void save() throws IOException {
        File outfile = new File("Groups.json");
        FileOutputStream fileOutputStream = new FileOutputStream(outfile);
        String out = gson.toJson(col);
        fileOutputStream.write(out.getBytes());
        fileOutputStream.close();
        System.out.println("Коллекция сохранена");
    }

    /**
     * Метод исполняет скрипт из файла Script.txt
     * Если файл вызывается из файла, то ограничивает его выполнение до 3
     *
     * @param str
     * @throws IOException
     */
    public void execute_script(String str) throws IOException {
        String userCommand = "";
        String[] finalUserCommand;
        try {
            BufferedInputStream script = new BufferedInputStream(new FileInputStream(str));
            try (Scanner commandReader = new Scanner(script)) {
                while (commandReader.hasNextLine() && !userCommand.equals("exit")) {
                    userCommand = commandReader.nextLine();
                    finalUserCommand = userCommand.trim().split(" ", 2);
                    try {
                        switch (finalUserCommand[0]) {
                            case "":
                                break;
                            case "help":
                                help();
                                break;
                            case "info":
                                info();
                                break;
                            case "show":
                                show();
                                break;
                            case "add":
                                String[] arr = new String[13];
                                for (int i = 0; i < arr.length; i++) {
                                    userCommand = commandReader.nextLine();
                                    arr[i] = userCommand;
                                }
                                add(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12]);
                                break;
                            case "update_id":
                                update(finalUserCommand[1]);
                                break;
                            case "remove_by_id":
                                remove_by_id(finalUserCommand[1]);
                                break;
                            case "clear":
                                clear();
                                break;
                            case "save":
                                try {
                                    save();
                                } catch (Exception e) {
                                    System.out.println("Нет прав для записи");
                                }
                                break;
                            case "execute_script":
                                j++;
                                if (j == 3) {
                                    j = 0;
                                    break;
                                } else {
                                    execute_script(finalUserCommand[1]);
                                }
                                break;
                            case "add_if_max":
                                add_if_max();
                                break;
                            case "add_if_min":
                                add_if_min();
                                break;
                            case "remove_greater":
                                remove_greater(finalUserCommand[1]);
                                break;
                            case "remove_any_by_students_count":
                                remove_any_by_students_count(finalUserCommand[1]);
                                break;
                            case "print_field_ascending_students_count":
                                print_field_ascending_students_count();
                                break;
                            case "print_field_descending_form_of_education":
                                print_field_descending_form_of_education();
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
        } catch (FileNotFoundException e) {
            System.out.println("Файла по указанному пути не существует.");
        }
    }

    /**
     * Метод добавляет новый элемент в коллекцию, если его height превышает height наибольшего элемента этой коллекции
     * Метод находит наибольшее height и сравнивает его с новым элементом
     */
    public void add_if_max() {
        if (!(col.size() == 0)) {
            long heightMAX = 0;
            for (StudyGroup s : col) {
                if (s.getGroupAdmin().getHeight() > heightMAX) {
                    heightMAX = s.getId();
                }
            }
            List<StudyGroup> list = new ArrayList<>(col);
            col.clear();
            add();
            long finalHeightMAX = heightMAX;
            if (col.removeIf(col -> col.getId() < finalHeightMAX)) {
                col.addAll(list);
                System.out.println("Элемент коллекции не сохранен, так как его height меньше height других элементов коллекции ");
            } else {
                col.addAll(list);
                System.out.println("Элемент коллекции сохранен, так как его height больше height других элементов коллекции");
            }
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    /**
     * Метод добавляет новый элемент в коллекцию, если его height меньше, чем у наименьшего элемента этой коллекции
     * Метод находит наименьшее height и сравнивает его с новым элементом
     */
    public void add_if_min() {
        if (!(col.size() == 0)) {
            long heightMIN = 9223372036854775807L;
            for (StudyGroup s : col) {
                if (s.getGroupAdmin().getHeight() < heightMIN) {
                    heightMIN = s.getGroupAdmin().getHeight();
                }
            }
            List<StudyGroup> list = new ArrayList<>(col);
            col.clear();
            add();
            long finalHeightMIN = heightMIN;
            if (col.removeIf(col -> col.getId() < finalHeightMIN)) {
                col.addAll(list);
                System.out.println("Элемент коллекции не сохранен, так как его height больше height других элементов коллекции ");
            } else {
                col.addAll(list);
                System.out.println("Элемент коллекции сохранен, так как его height меньше height других элементов коллекции");
            }
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    /**
     * Метод удаляет из коллекции все элементы, превышающие заданный id
     * id вводится после вызова метода
     *
     * @param str
     */
    public void remove_greater(String str) {
        try {
            if (!(col.size() == 0)) {
                int oldSize = col.size();
                int height = Integer.parseInt(str);
                if (col.removeIf(col -> col.getGroupAdmin().getHeight() > height)) {
                    int newSize = oldSize - col.size();
                    if (newSize == 1) {
                        System.out.println("Был удален " + newSize + " элемент коллекции");
                    } else {
                        System.out.println("Было удалено " + newSize + " элемента коллекции");
                    }
                } else {
                    System.out.println("Коллекция не изменина, так как height всех элементов меньше указанного");
                }
            } else {
                System.out.println("Коллекция пуста");
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
        }
    }

    /**
     * Метод удаляет из коллекции один элемент, значение поля studentsCount которого эквивалентно заданному
     *
     * @param str
     */
    public void remove_any_by_students_count(String str) {
        try {
            if (!(col.size() == 0)) {
                int students_count = Integer.parseInt(str);
                if (col.removeIf(col -> col.getStudentsCount() == students_count)) {
                    System.out.println("Элемент удален");
                } else System.out.println("Нет элемента с таким student_count");
            } else {
                System.out.println("Коллекция пуста");
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
        }
    }

    /**
     * Метод выводит studentsCount в порядке возрастания
     */
    public void print_field_ascending_students_count() {
        if (!(col.size() == 0)) {
            List<StudyGroup> list = new ArrayList<>(col);
            list.sort(new ComparatorByStudentCount());
            for (StudyGroup s : list) {
                System.out.println("studentsCount" + " - " + s.getStudentsCount());
            }
        } else {
            System.out.println("Коллекция пустая");
        }
    }

    /**
     * Метод выводит значение formOfEducation в порядке убывания
     */
    public void print_field_descending_form_of_education() {
        if (!(col.size() == 0)) {
            List<StudyGroup> list = new ArrayList<>(col);
            list.sort(new ComparatorByFormOfEducation());
            for (StudyGroup s : list) {
                System.out.println("formOfEducation" + " - " + s.getFormOfEducation());
            }
        } else {
            System.out.println("Коллекция пустая");
        }
    }

    /**
     * Метод загружает файл и проверяет его на права доступа
     * Метод загружает коллекцию проверяет поля и устанавливает случайное id и и датту инициаллизации
     *
     * @param file
     * @throws IOException
     */
    public void load(File file) throws IOException {
        int beginSize = col.size();
        if (!file.exists()) {
            System.out.println("Файла по указанному пути не существует.");
            System.exit(1);
        }
        if (!file.canRead() || !file.canWrite()) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            System.exit(1);
        }
        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            System.out.println("Файл загружен");
            StringBuilder result = new StringBuilder();
            String nextL;
            while ((nextL = inputStreamReader.readLine()) != null) {
                result.append(nextL);
            }
            Type collectionType = new TypeToken<PriorityQueue<StudyGroup>>() {
            }.getType();
            Type collectionType2 = new TypeToken<List<StudyGroup>>() {
            }.getType();
            try {
                List<StudyGroup> groupList = gson.fromJson(String.valueOf(result), collectionType2);
                try {
                    for (StudyGroup s : groupList) {
                        if (s.getName() == null) {
                            throw new BadArgument("name не может быть null");
                        }
                        if (s.getName().equals("")) {
                            throw new BadArgument("Строка name не может быть пустой");
                        }
                        if (s.getCoordinates() == null) {
                            throw new BadArgument("coordinates не может быть null");
                        }
                        if (s.getCoordinates().getX() == null) {
                            throw new BadArgument("x не может быть null");
                        }
                        if (s.getStudentsCount() == null) {
                            throw new BadArgument("studentCount не может быть null");
                        }
                        if (s.getStudentsCount() < 0) {
                            throw new BadArgument("StudentsCount должен быть больше нуля");
                        }
                        if (s.getFormOfEducation() == null) {
                            throw new BadArgument("formOfEducation не может быть null");
                        }
                        if (s.getSemesterEnum() == null) {
                            throw new BadArgument("SemesterEnum не может быть null");
                        }
                        if (s.getGroupAdmin() == null) {
                            throw new BadArgument("groupAdmin не может быть null");
                        }
                        if (s.getGroupAdmin().getName() == null) {
                            throw new BadArgument("name не может быть null");
                        }
                        if (s.getGroupAdmin().getName().equals("")) {
                            throw new BadArgument("Строка name не может быть пустой");
                        }
                        if (s.getGroupAdmin().getHeight() == null) {
                            throw new BadArgument("height не может быть null");
                        }
                        if (s.getGroupAdmin().getHeight() <= 0) {
                            throw new BadArgument("height должен быть больше 0");
                        }
                        if (s.getGroupAdmin().getHairColor() == null) {
                            throw new BadArgument("hairColor не может быть null");
                        }
                        if (s.getGroupAdmin().getNationality() == null) {
                            throw new BadArgument("nationality не может быть null");
                        }
                        if (s.getGroupAdmin().getLocation() == null) {
                            throw new BadArgument("location не может быть null");
                        }
                        if (s.getGroupAdmin().getLocation().getZ() == null) {
                            throw new BadArgument("z не может быть null");
                        }
                    }
                    PriorityQueue<StudyGroup> priorityQueue = gson.fromJson(result.toString(), collectionType);
                    for (StudyGroup s : priorityQueue) {
                        s.setCreationDate();
                        col.add(s);
                        if (s.getId() > id) {
                            id = s.getId();
                        }
                    }
                    System.out.println("Коллекция успешно загружена. Добавлено " + (col.size() - beginSize) + " элементов.");
                } catch (BadArgument badArgument) {
                    System.out.println(badArgument.getMessage());
                    System.out.println("Загружена пустая коллекция");
                }
            } catch (JsonSyntaxException e) {
                System.out.println("Ошибка синтаксиса Json. Коллекция не может быть загружена.");
                System.exit(1);
            }
        }
    }
}






