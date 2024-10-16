package design;

public class Color {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String WHITE_BOLD = "\033[1;37m";

    public static void menu() {
        System.out.println(WHITE_BOLD+
                "┳┓    • ┓    ┏┓\n"+
                "┃┃┏┓┏┓┓┏┫    ┃┓┏┓┏┳┓┏┓\n"+
                "┻┛┛ ┗┛┗┗┻    ┗┛┗┻┛┗┗┗\n"
        );
    }
}
