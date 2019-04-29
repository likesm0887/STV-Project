package report;

public class ColorHelper {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public String paintingRed(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public String paintingGreen(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public void temp() {
        // TODO code application logic here
        System.out.println("\033[0m BLACK");
        System.out.println("\033[31m RED");
        System.out.println("\033[32m GREEN");
        System.out.println("\033[33m YELLOW");
        System.out.println("\033[34m BLUE");
        System.out.println("\033[35m MAGENTA");
        System.out.println("\033[36m CYAN");
        System.out.println("\033[37m WHITE\033[0m");

//printing the results
        String leftAlignFormat = "| %-20s | %-7d | %-7d | %-7d |%n";

        System.out.format("|---------Test Cases with Steps Summary -------------|%n");
        System.out.format("+----------------------+---------+---------+---------+%n");
        System.out.format("| Test Cases           |Passed   |Failed   |Skipped  |%n");
        System.out.format("+----------------------+---------+---------+---------+%n");

        String formattedMessage = "\033[31m" + "TEST_01".trim() + "\033[0m";

        leftAlignFormat = "| %-29s | %-7d | %-7d | %-7d |%n";
//        System.out.print("\033[31m"); // Open print red
        System.out.printf(leftAlignFormat, formattedMessage, 2, 1, 0);
//        System.out.print("\033[0m"); // Close print red
        System.out.format("+----------------------+---------+---------+---------+%n");
    }
}
