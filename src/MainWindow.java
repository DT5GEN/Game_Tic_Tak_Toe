import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private static final int WIN_WIDTH = 800;
    private static final int WIN_HEIGHT = 800;
    private static final int WIN_POSX = 716;
    private static final int WIN_POSY = 200;

    private Settings settingsWindow;
    private GameMap gameMap;


    MainWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLocation(WIN_POSX, WIN_POSY);
        setTitle("                                                                                                 -= КРЕСТИКИ-НОЛИКИ =-");
        setResizable(false);
        Font BigFontTR = new Font("TimesRoman", Font.BOLD, 30);//Тут все про шрифт)


        settingsWindow = new Settings(this);
        gameMap = new GameMap();


        JButton btnStartGame = new JButton("Новая игра");
        btnStartGame.setFont(BigFontTR);
        JButton btnExitGame = new JButton("Выход");
        btnExitGame.setFont(BigFontTR);


        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                settingsWindow.setVisible(true);


            }
        });

        btnExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1,2));
        panelButtons.add(btnStartGame);
        panelButtons.add(btnExitGame);

        add(panelButtons, BorderLayout.SOUTH);

        add(gameMap);

//        add(btnExitGame, BorderLayout.PAGE_END);
//        add(btnStartGame, BorderLayout.BEFORE_FIRST_LINE);


        setVisible(true);


    }

    void startNewGame(int mode, int fieldSizeX, int fieldSizeY,int winLength){

        gameMap.startNewGame(mode, fieldSizeX, fieldSizeY, winLength);

    }

}
