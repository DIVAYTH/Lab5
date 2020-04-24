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
    long id = 0;
    int j = 1;
    private PriorityQueue<StudyGroup> col;
    private Date initDate;
    private Gson gson;
    private boolean wasStart;
    private HashMap<String, String> map;

    {
        gson = new Gson();
        col = new PriorityQueue<>(new ComparatorDefault());
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
        wasStart = true;
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

        String name;
        do {
            System.out.println("Введите имя группы");
            name = scanner.nextLine().trim();
            if (name.equals("")) {
                System.out.println("Имя группы не может быть null. Введите снова");
            }
        } while (name.equals(""));

        id++;
        String cor_x;
        Integer x = null;
        while (x == null) {
            try {
                System.out.println("Введите координаты, x:");
                cor_x = scanner.nextLine().trim();
                if (cor_x.equals("")) {
                    System.out.println("x не может быть null. Введите снова");
                } else {
                    x = Integer.parseInt(cor_x);
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
            }
        }

        String cor_y;
        Double y = null;
        while (y == null) {
            try {
                System.out.println("Введите координаты, y:");
                cor_y = scanner.nextLine().trim();
                if (cor_y.equals("")) {
                    System.out.println("y не может быть null. Введите снова");
                } else {
                    y = Double.parseDouble(cor_y);
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы double. Введите снова");
            }
        }

        String s_studentsCount;
        Integer studentsCount = null;
        while (studentsCount == null) {
            try {
                System.out.println("Введите studentsCount:");
                s_studentsCount = scanner.nextLine().trim();
                if (s_studentsCount.equals("")) {
                    System.out.println("studentsCount не может быть null. Введите снова");
                } else {
                    studentsCount = Integer.parseInt(s_studentsCount);
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
            }
        }

        String s_formOfEducation;
        do {
            System.out.println("Выберите форму обучения из: DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES");
            s_formOfEducation = scanner.nextLine().trim().toUpperCase();
            switch (s_formOfEducation) {
                case "DISTANCE_EDUCATION":
                    formOfEducation = FormOfEducation.DISTANCE_EDUCATION;
                    break;
                case "FULL_TIME_EDUCATION":
                    formOfEducation = FormOfEducation.FULL_TIME_EDUCATION;
                    break;
                case "EVENING_CLASSES":
                    formOfEducation = FormOfEducation.EVENING_CLASSES;
                    break;
                case "":
                    System.out.println("formOfEducation не может быть null. Введите снова");
                    break;
                default:
                    System.out.println("Такой формы обучения нет. Введите снова");
                    break;
            }
        } while (!s_formOfEducation.equals("DISTANCE_EDUCATION") && !s_formOfEducation.equals("FULL_TIME_EDUCATION") &&
                !s_formOfEducation.equals("EVENING_CLASSES"));

        String s_semesterEnum;
        do {
            System.out.println("Выберите семестр из: FIRST, THIRD, FIFTH, EIGHTH;");
            s_semesterEnum = scanner.nextLine().trim().toUpperCase();
            switch (s_semesterEnum) {
                case "FIRST":
                    semesterEnum = Semester.FIRST;
                    break;
                case "THIRD":
                    semesterEnum = Semester.THIRD;
                    break;
                case "FIFTH":
                    semesterEnum = Semester.FIFTH;
                    break;
                case "EIGHTH":
                    semesterEnum = Semester.EIGHTH;
                    break;
                case "":
                    System.out.println("semesterEnum не может быть null. Введите снова");
                    break;
                default:
                    System.out.println("Такого семестра нет. Введите снова");
                    break;
            }
        } while (!s_semesterEnum.equals("FIRST") && !s_semesterEnum.equals("THIRD") &&
                !s_semesterEnum.equals("FIFTH") && !s_semesterEnum.equals("EIGHTH"));

        String per_name;
        do {
            System.out.println("Введите имя главы группы");
            per_name = scanner.nextLine().trim();
            if (per_name.equals(""))
                System.out.println("Вы не ввели значение. Введите снова");
        } while (per_name.equals(""));

        String s_height;
        Integer height = null;
        while (height == null) {
            try {
                System.out.println("Введите рост:");
                s_height = scanner.nextLine().trim();
                if (s_height.equals("")) {
                    System.out.println("Рост не может быть null. Введите снова");
                } else {
                    height = Integer.parseInt(s_height);
                    if (height <= 0) {
                        System.out.println("Рост не модет быть меньше 0");
                        height = null;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
            }
        }

        String s_hairColor;
        do {
            System.out.println("Выберите цвет волос: RED, BLUE, YELLOW, ORANGE, WHITE");
            s_hairColor = scanner.nextLine().trim().toUpperCase();
            switch (s_hairColor) {
                case "RED":
                    hairColor = Color.RED;
                    break;
                case "BLUE":
                    hairColor = Color.BLUE;
                    break;
                case "YELLOW":
                    hairColor = Color.YELLOW;
                    break;
                case "ORANGE":
                    hairColor = Color.ORANGE;
                    break;
                case "WHITE":
                    hairColor = Color.WHITE;
                    break;
                case "":
                    System.out.println("hairColor не может быть null. Введите снова");
                    break;
                default:
                    System.out.println("Такого цвета нет. Введите снова");
                    break;
            }
        } while (!s_hairColor.equals("RED") && !s_hairColor.equals("BLUE") &&
                !s_hairColor.equals("YELLOW") && !s_hairColor.equals("ORANGE")
                && !s_hairColor.equals("WHITE"));

        String s_nationality;
        do {
            System.out.println("Выберите откуда она:  USA, CHINA, INDIA, VATICAN");
            s_nationality = scanner.nextLine().trim().toUpperCase();
            switch (s_nationality) {
                case "USA":
                    nationality = Country.USA;
                    break;
                case "CHINA":
                    nationality = Country.CHINA;
                    break;
                case "INDIA":
                    nationality = Country.INDIA;
                    break;
                case "VATICAN":
                    nationality = Country.VATICAN;
                    break;
                case "":
                    System.out.println("nationality не может быть null. Введите снова");
                    break;
                default:
                    System.out.println("Такой страны нет. Введите снова");
                    break;
            }
        } while (!s_nationality.equals("USA") && !s_nationality.equals("CHINA") &&
                !s_nationality.equals("INDIA") && !s_nationality.equals("VATICAN"));

        String s_loc_x;
        Double loc_x = null;
        while (loc_x == null) {
            try {
                System.out.println("Введите координаты, x:");
                s_loc_x = scanner.nextLine().trim();
                if (s_loc_x.equals("")) {
                    System.out.println("x не может быть null. Введите снова");
                } else {
                    loc_x = Double.parseDouble(s_loc_x);
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы double. Введите снова");
            }
        }

        String s_loc_y;
        Integer loc_y = null;
        while (loc_y == null) {
            try {
                System.out.println("Введите координаты, y:");
                s_loc_y = scanner.nextLine().trim();
                if (s_loc_y.equals("")) {
                    System.out.println("y не может быть null. Введите снова");
                } else {
                    loc_y = Integer.parseInt(s_loc_y);
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
            }
        }

        String s_loc_z;
        Integer loc_z = null;
        while (loc_z == null) {
            try {
                System.out.println("Введите координаты, z:");
                s_loc_z = scanner.nextLine().trim();
                if (s_loc_z.equals("")) {
                    System.out.println("z не может быть null. Введите снова");
                } else {
                    loc_z = Integer.parseInt(s_loc_z);
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели строку или число выходит за пределы int. Введите снова");
            }
        }

        studyGroup = new StudyGroup(id, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
        col.add(studyGroup);
        System.out.println("Элемент коллекции добавлен");
    }
    
    public void add(String str1, String str2, String str3, String str4, String str5, String str6, String str7,
                    String str8, String str9, String str10, String str11, String str12, String str13) {
        StudyGroup studyGroup;
        FormOfEducation formOfEducation;
        Semester semesterEnum;
        Color hairColor;
        Country nationality;

        id++;
        String name;
        if (str1.equals("")) {
            System.out.println("Имя группы не может быть null. Коллекция не сохранена");
            return;
        } else {
            name = str1;
        }

        int x;
        try {
            if (str2.equals("")) {
                System.out.println("x не может быть null. Коллекция не сохранена");
                return;
            } else {
                x = Integer.parseInt(str2);
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Коллекция не сохранена");
            return;
        }

        double y;
        try {
            if (str3.equals("")) {
                System.out.println("y не может быть null. Коллекция не сохранена");
                return;
            } else {
                y = Double.parseDouble(str3);
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы double. Коллекция не сохранена");
            return;
        }

        int studentsCount;
        try {
            if (str4.equals("")) {
                System.out.println("studentsCount не может быть null. Коллекция не сохранена");
                return;
            } else {
                studentsCount = Integer.parseInt(str4);
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Коллекция не сохранена");
            return;
        }

        switch (str5.toUpperCase()) {
            case "DISTANCE_EDUCATION":
                formOfEducation = FormOfEducation.DISTANCE_EDUCATION;
                break;
            case "FULL_TIME_EDUCATION":
                formOfEducation = FormOfEducation.FULL_TIME_EDUCATION;
                break;
            case "EVENING_CLASSES":
                formOfEducation = FormOfEducation.EVENING_CLASSES;
                break;
            case "":
                System.out.println("formOfEducation не может быть null. Коллекция не сохранена");
                return;
            default:
                System.out.println("Такой формы обучения нет. Коллекция не сохранена");
                return;
        }

        switch (str6.toUpperCase()) {
            case "FIRST":
                semesterEnum = Semester.FIRST;
                break;
            case "THIRD":
                semesterEnum = Semester.THIRD;
                break;
            case "FIFTH":
                semesterEnum = Semester.FIFTH;
                break;
            case "EIGHTH":
                semesterEnum = Semester.EIGHTH;
                break;
            case "":
                System.out.println("semesterEnum не может быть null. Коллекция не сохранена");
                return;
            default:
                System.out.println("Такого семестра нет. Коллекция не сохранена");
                return;
        }

        String per_name;
        if (str7.equals("")) {
            System.out.println("Вы не ввели значение. Коллекция не сохранена");
            return;
        } else {
            per_name = str7;
        }

        int height;
        try {
            if (str8.equals("")) {
                System.out.println("Рост не может быть null. Коллекция не сохранена");
                return;
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

        switch (str9.toUpperCase()) {
            case "RED":
                hairColor = Color.RED;
                break;
            case "BLUE":
                hairColor = Color.BLUE;
                break;
            case "YELLOW":
                hairColor = Color.YELLOW;
                break;
            case "ORANGE":
                hairColor = Color.ORANGE;
                break;
            case "WHITE":
                hairColor = Color.WHITE;
                break;
            case "":
                System.out.println("hairColor не может быть null. Коллекция не сохранена");
                return;
            default:
                System.out.println("Такого цвета нет. Коллекция не сохранена");
                return;
        }

        switch (str10.toUpperCase()) {
            case "USA":
                nationality = Country.USA;
                break;
            case "CHINA":
                nationality = Country.CHINA;
                break;
            case "INDIA":
                nationality = Country.INDIA;
                break;
            case "VATICAN":
                nationality = Country.VATICAN;
                break;
            case "":
                System.out.println("nationality не может быть null. Коллекция не сохранена");
                return;
            default:
                System.out.println("Такой страны нет. Коллекция не сохранена");
                return;
        }

        double loc_x;
        try {
            if (str11.equals("")) {
                System.out.println("x не может быть null. Коллекция не сохранена");
                return;
            } else {
                loc_x = Double.parseDouble(str11);
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы double. Коллекция не сохранена");
            return;
        }

        int loc_y;
        try {
            if (str12.equals("")) {
                System.out.println("y не может быть null. Коллекция не сохранена");
                return;
            } else {
                loc_y = Integer.parseInt(str12);
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Коллекция не сохранена");
            return;
        }

        int loc_z;
        try {
            if (str13.equals("")) {
                System.out.println("z не может быть null. Коллекция не сохранена");
                return;
            } else {
                loc_z = Integer.parseInt(str13);
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели строку или число выходит за пределы int. Коллекция не сохранена");
            return;
        }

        studyGroup = new StudyGroup(id, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
        col.add(studyGroup);
        System.out.println("Элемент коллекции добавлен");
    }

    /**
     * Метод обновляет значение элемента по его id
     *
     * @param str
     */
    public void update(String str) {
        try {
            if (!(col.size() == 0)) {
                int id = Integer.parseInt(str);
                if (col.removeIf(col -> col.getId() == id)) {
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
                                save();
                                break;
                            case "execute_script":
                                j++;
                                if (j == 3 ){
                                    j = 0;
                                    break;
                                }else{
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
        }else{
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
        }else {
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
        try {
            if (!file.exists()) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файла по указанному пути не существует.");
            if (!wasStart) System.exit(1);
            else return;
        }
        try {
            if (!file.canRead() || !file.canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            if (!wasStart) System.exit(1);
            else return;
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
                                throw new NullPointerException("name не может быть null");
                            }
                            if (s.getName().equals("")) {
                                throw new BadArgument("Строка name не может быть пустой");
                            }
                            if (s.getCoordinates() == null) {
                                throw new NullPointerException("coordinates не может быть null");
                            }
                            if (s.getCoordinates().getX() == null) {
                                throw new NullPointerException("x не может быть null");
                            }
                            if (s.getStudentsCount() == null) {
                                throw new NullPointerException("studentCount не может быть null");
                            }
                            if (s.getStudentsCount() < 0) {
                                throw new BadArgument("StudentsCount должен быть больше нуля");
                            }
                            if (s.getFormOfEducation() == null) {
                                throw new NullPointerException("formOfEducation не может быть null");
                            }
                            if (s.getSemesterEnum() == null) {
                                throw new NullPointerException("SemesterEnum не может быть null");
                            }
                            if (s.getGroupAdmin() == null) {
                                throw new NullPointerException("groupAdmin не может быть null");
                            }
                            if (s.getGroupAdmin().getName() == null) {
                                throw new NullPointerException("name не может быть null");
                            }
                            if (s.getGroupAdmin().getName().equals("")) {
                                throw new BadArgument("Строка name не может быть пустой");
                            }
                            if (s.getGroupAdmin().getHeight() == null) {
                                throw new NullPointerException("height не может быть null");
                            }
                            if (s.getGroupAdmin().getHeight() <= 0) {
                                throw new BadArgument("height должен быть больше 0");
                            }
                            if (s.getGroupAdmin().getHairColor() == null) {
                                throw new NullPointerException("hairColor не может быть null");
                            }
                            if (s.getGroupAdmin().getNationality() == null) {
                                throw new NullPointerException("nationality не может быть null");
                            }
                            if (s.getGroupAdmin().getLocation() == null) {
                                throw new NullPointerException("location не может быть null");
                            }
                            if (s.getGroupAdmin().getLocation().getZ() == null) {
                                throw new NullPointerException("z не может быть null");
                            }
                        }
                        PriorityQueue<StudyGroup> priorityQueue = gson.fromJson(result.toString(), collectionType);
                        for (StudyGroup s : priorityQueue) {
                            s.setCreationDate(s.getCreationDate());
                            col.add(s);
                            if (s.getId() > id) {
                                id = s.getId();
                            }
                        }
                        System.out.println("Коллекция успешно загружена. Добавлено " + (col.size() - beginSize) + " элементов.");
                    } catch (NullPointerException e) {
                        System.out.println("Загружена пустая коллекция");
                    } catch (BadArgument badArgument) {
                        System.out.println(badArgument.getMessage());
                    }
                } catch (JsonSyntaxException e) {
                    System.out.println("Ошибка синтаксиса Json. Коллекция не может быть загружена.");
                    System.exit(1);
                }
            }
        }
    }






