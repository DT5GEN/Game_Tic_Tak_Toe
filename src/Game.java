public class Game {


    public static void main(String[] args) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        new MainWindow();
        System.out.println(ANSI_RED + "\n -= Игра запущена =- " + ANSI_RESET);
    }


}
