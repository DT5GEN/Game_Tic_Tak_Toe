import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameMap extends JPanel {

    public static final int GAME_MODE_HVA = 0;
    public static final int GAME_MODE_HVH = 1;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private static final int DOT_EMPTY = 0;
    private static final int DOT_HUMAN = 1;
    private static final int DOT_AI = 2;

    private static final int STATE_DROW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;

    private boolean isGameOver;
    private boolean initializedMap;

    private int stateGameOver;

    public static final Random RANDOM = new Random();

    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private int [][] field;

    private int cellWidth;
    private int cellHeight;



    GameMap () {

        setBackground(Color.PINK);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                update(e);
            }
        });
        initializedMap = false;
    }

    void startNewGame (int mode, int fieldSizeX, int fieldSizeY,int winLength){

        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLength = winLength;
        field = new int[fieldSizeX][fieldSizeY];
        isGameOver = false;
        initializedMap = true;
        repaint();
//        System.out.println( ANSI_CYAN  + " Режим игры  - " + mode +
//                             "\n Ширина поля  - " + fieldSizeX +
//                              "\n Высота поля  - " + fieldSizeY +
//                              "\n Выигрышная длина  - " + winLength + ANSI_RESET);
    }

    private void setGameOver(int gameOverState) {
        stateGameOver = gameOverState;
        isGameOver = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        System.out.println("Отрисовался!!!");
        render(g);

    }

    private void update(MouseEvent e) {

        if (!initializedMap) return;
        if (isGameOver) return;

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        System.out.println(" х = " + cellX + " y = " + cellY);

        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) {
            return;
        }
        field[cellY][cellX] = DOT_HUMAN;

        if (checkWin(DOT_HUMAN)) {
            setGameOver(STATE_WIN_HUMAN);
            return;
        }

        if (isFullMap()){
            setGameOver(STATE_DROW);
            return;
        }

        aiTurn();
        repaint();

        if (checkWin(DOT_AI)) {
            setGameOver(STATE_WIN_AI);
            return;
        }
        if (isFullMap()) {
            setGameOver(STATE_DROW);
            return;
        }
    }

    private void render(Graphics g){
        if (!initializedMap) return;
        int width = getWidth();
        int height = getHeight();

        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;
        g.setColor(Color.WHITE);

        for (int i = 0; i < fieldSizeY; i++) {

            int y = i * cellHeight;
            g.drawLine(0,y,width,y);
            
        }


        for (int i = 0; i < fieldSizeX; i++) {

            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);

        }

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isEmptyCell(x,y)) {
                    continue;
                }
                if (field[y][x] == DOT_HUMAN) {
                    g.setColor(new Color(1, 100, 200));
                    g.fillOval(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }  else if (field[y][x] == DOT_AI) {
                    g.setColor(Color.green);
                    g.fillOval(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                } else {
                    throw  new RuntimeException(" Can't paint cellX = " + x + " cellY = " + y);

                }

            }

        }
        if (isGameOver) {
            showMessageGameOver(g);
        }

    }

    private void showMessageGameOver (Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 245, getWidth(), 170);
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 60));

        switch (stateGameOver) {
            case STATE_DROW:
                g.drawString("НИЧЬЯ", 230, getHeight() / 2);
                break;
            case STATE_WIN_HUMAN:
                g.drawString("Ты выиграл!", 160, getHeight() / 2);
                break;
            case STATE_WIN_AI:
                g.drawString("КОМП ПОБЕДИЛ!", 155, getHeight() / 2);
                break;
            default:
                throw new RuntimeException("Произошла какая-то ерунда " + stateGameOver);
        }
    }

    public  void aiTurn() {
        if (turnAIWinCell()){
            return;
        }
        if (turnHumanWinCell()) {
            return;
        }
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field [y][x] = DOT_AI;
    }

    private  boolean turnAIWinCell() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if(isEmptyCell(j,i)) {
                    field[i][j] = DOT_AI;
                    if (checkWin(DOT_AI)) {
                        return true;
                    }
                    field[i][j] = DOT_EMPTY;
                }

            }

        }
        return false;
    }

    private  boolean turnHumanWinCell() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(j, i)) {
                    field[i][j] = DOT_HUMAN;
                    if (checkWin(DOT_HUMAN)) {
                        field[i][j] = DOT_AI;
                        return true;
                    }
                    field[i][j] = DOT_EMPTY;
                }

            }

        }
        return false;
    }

    //проверка на победу

    private  boolean checkWin(int с) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLength, с)) {
                    return true; // проверка по оси Х
                }
                if (checkLine(i, j, 1, 1, winLength, с)) {
                    return true; // проверка по диагонали Х У
                }
                if (checkLine(i, j, 0, 1, winLength, с)) {
                    return true; // проверка по оси У
                }
                if (checkLine(i, j, 1, -1, winLength, с)) {
                    return true; // проверка по оси Х -У
                }
            }

        }
        return false;
    }

    // проверка линии

    private  boolean checkLine(int x, int y, int vx, int vy, int len, int c) {
        final int farX = x + (len -1) * vx;     // посчитаем конец проверяемой линии
        final int farY = y + (len -1) * vy;
        if (!isValidCell(farX, farY)) {
            return false;                       // проверяем, не выходит ли вектор за пределы игрового поля?
        }
        for (int i = 0; i < len; i++) {         // проход по проверяемой линии
            if (field[y + i * vy][x + i * vx] != c) {
                return false;                   // проверяем одинаковые ли символы в ячейках?
            }
        }
        return true;
    }

    public  boolean isFullMap() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public  boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    public  boolean isEmptyCell(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

}
