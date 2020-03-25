/**
 * class exception при неправильном вводе элементов
 */
public class BadArgument extends Exception {
    public BadArgument(String message) {
        super(message);
    }
}
