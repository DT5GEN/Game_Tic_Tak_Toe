public class Game {


    public static void main(String[] args) {

        final String ansiReset = "\u001B[0m";
        final String ansiRed = "\u001B[31m";

        new MainWindow();
        System.out.println(ansiRed + "\n -= Игра запущена =- " + ansiReset);
    }


}
