import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Settings extends JFrame {

    private static final int WIN_WIDTH = 350;
    private static final int WIN_HEIGHT = 270;

    private static final int MIN_WIN_LENGTH = 3;
    private static final int MIN_FIELD_LENGTH = 3;
    private static final int MAX_FIELD_LENGTH = 10;

    private static final String FIELD_SIZE_TEXT_PREFIX = "Размер поля: ";
    private static final String WIN_LENGTH_TEXT_PREFIX = "Выигрышная длина: ";





    private MainWindow mainWindow;
    private JRadioButton humVsAI;
    private JRadioButton humVsHum;
    private JSlider sliderWinLen;
    private JSlider sliderFieldSize;



    Settings(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setSize(WIN_WIDTH, WIN_HEIGHT);

        Rectangle gameWindowBounds = mainWindow.getBounds();
        int posX = (int)gameWindowBounds.getCenterX() - WIN_WIDTH / 2;
        int posY = (int) gameWindowBounds.getCenterY() - WIN_HEIGHT / 2;
        setLocation(posX, posY);
        setResizable(false);
        setTitle(" -= Настройки новой игры =-");

        setLayout(new GridLayout(10,1));
        addGameModeSettings();
        addFieldSizeControl();
        Font BigFontTR = new Font("TimesRoman", Font.BOLD, 16);//Тут все про шрифт)

        JButton btnStartGame = new JButton("Начать новую игру");
        btnStartGame.setFont(BigFontTR);

       btnStartGame.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
               btnStartClick();
          }
      });

        add(btnStartGame);
    }

    private void addGameModeSettings() {
        add(new JLabel("Выберите режим игры:"));
        humVsAI = new JRadioButton("Человек vs. ИИ");
        humVsHum = new JRadioButton("Человек vs. Человек", true);
        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(humVsAI);
        gameMode.add(humVsHum);
        add(humVsAI);
        add(humVsHum);
    }

    private void addFieldSizeControl() {
        JLabel lbFieldSize = new JLabel(FIELD_SIZE_TEXT_PREFIX + MIN_FIELD_LENGTH);
        JLabel lbWinLength = new JLabel(WIN_LENGTH_TEXT_PREFIX + MIN_WIN_LENGTH);

        sliderFieldSize = new JSlider(MIN_FIELD_LENGTH, MAX_FIELD_LENGTH, MIN_FIELD_LENGTH);
        sliderFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = sliderFieldSize.getValue();
                lbFieldSize.setText(FIELD_SIZE_TEXT_PREFIX + currentValue);
                sliderWinLen.setMaximum(currentValue);
            }
        });

        sliderWinLen = new JSlider(MIN_WIN_LENGTH, MIN_FIELD_LENGTH, MIN_FIELD_LENGTH);
        sliderWinLen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lbWinLength.setText(WIN_LENGTH_TEXT_PREFIX + sliderWinLen.getValue());
            }
        });

        add(new JLabel("Выберите размер поля"));
        add(lbFieldSize);
        add(sliderFieldSize);
        add(new JLabel("Выберите длину победной линии"));
        add(lbWinLength);
        add(sliderWinLen);

    }


    private void btnStartClick() {
        int gameMode;

        if (humVsAI.isSelected()) {
            gameMode = GameMap.GAME_MODE_HVA;
        } else if (humVsHum.isSelected()) {
            gameMode = GameMap.GAME_MODE_HVH;
        } else {
            throw new RuntimeException("Неизвестный режим игры");
        }

        int fieldSize = sliderFieldSize.getValue();
        int winLen = sliderWinLen.getValue();

        mainWindow.startNewGame(gameMode, fieldSize, fieldSize, winLen);
        setVisible(false);
    }







}
