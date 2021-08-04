import javax.swing.*;
import java.awt.*;

public class GameMap extends JPanel {

    public static final int GAME_MODE_HVA = 0;
    public static final int GAME_MODE_HVH = 1;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    GameMap () {

        setBackground(Color.PINK);
    }

    void startNewGame (int mode, int fieldSizeX, int fieldSizeY,int winLength){

        System.out.println( ANSI_CYAN  + " Режим игры  - " + mode +
                             "\n Ширина поля  - " + fieldSizeX +
                              "\n Высота поля  - " + fieldSizeY +
                              "\n Выигрышная длина  - " + winLength + ANSI_RESET);
    }
}
