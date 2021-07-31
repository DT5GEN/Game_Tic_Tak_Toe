import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private static final int WIN_WIDTH = 800;
    private static final int WIN_HEIGHT = 500;
    private static final int WIN_POSX = 500;
    private static final int WIN_POSY = 250;

    MainWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLocation(WIN_POSX, WIN_POSY);
        setTitle("                                                                                                 -= КРЕСТИКИ-НОЛИКИ =-");
        setResizable(true);

        JButton btnStartGame = new JButton("New game");
        JButton btnExitGame = new JButton("Exit");

        add(btnExitGame, BorderLayout.PAGE_END);
        add(btnStartGame, BorderLayout.BEFORE_FIRST_LINE);


        setVisible(true);


    }

}
