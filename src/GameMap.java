import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

@SuppressWarnings("ALL")
public class GameMap extends JPanel {

    public static final int GAME_MODE_HVA = 0;
    public static final int GAME_MODE_HVH = 1;


    private static final int DOT_EMPTY = 0;
    private static final int DOT_HUMAN = 1;
    private static final int DOT_HUM2 = 3;
    private static final int DOT_AI = 2;
    private boolean action2player = false;


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

    protected int mode;



    GameMap () {

        setBackground(Color.PINK);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                update(e);
            }
        });
        initializedMap = false;
    }

    void startNewGame  (int mode, int fieldSizeX, int fieldSizeY,int winLength){
        this.mode = mode;
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLength = winLength;
        field = new int[fieldSizeX][fieldSizeY];
        isGameOver = false;
        initializedMap = true;
        repaint();

    }

    private void setGameOver(int gameOverState) {
        stateGameOver = gameOverState;
        isGameOver = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        render(g);

    }


    private void update(MouseEvent e) {


        if (!initializedMap) return;
        if (isGameOver) return;

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;


        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) {
            return;
        }

        field[cellY][cellX] = (!action2player && mode == 1) ? DOT_HUM2 : DOT_HUMAN;

        if (checkWin(DOT_HUMAN)) {
            setGameOver(STATE_WIN_HUMAN);
            return;
        }

        if (isFullMap()){
            setGameOver(STATE_DROW);
            return;
        }

        action2player = !action2player;

        if (checkWin(DOT_HUM2)) {
            setGameOver(STATE_WIN_HUMAN);
            return;
        }

        if (isFullMap()){
            setGameOver(STATE_DROW);
            return;
        }

        repaint();

        if (mode == 0){
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

        if (mode == 1) {



            if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) {
                return;
            }

            field[cellY][cellX] = DOT_HUM2;

            if (checkWin(DOT_HUM2)) {
                setGameOver(STATE_WIN_HUMAN);
                return;
            }

            if (isFullMap()){
                setGameOver(STATE_DROW);
                return;
            }

            repaint();

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


                    g.setColor(new Color(0, 105, 0));
                    g.fillOval(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                    g.setColor(Color.PINK);
                    g.fillOval((int) (x * cellWidth + cellWidth*0.15), (int) (y * cellHeight + cellHeight*0.15), (int) (cellWidth * 0.7), (int) (cellHeight * 0.7));


                }  else if (field[y][x] == DOT_AI) {

                    g.setColor(Color.gray);
                    g.fillRect((int) (x * cellWidth +cellWidth*0.1), (int) (y * cellHeight +cellHeight*0.45), (int) (cellWidth * 0.815), (int) (cellHeight* 0.17));
                    g.fillRect((int) (x * cellWidth +cellWidth*0.45), (int) (y * cellHeight +cellHeight*0.1), (int) (cellWidth * 0.17), (int) (cellHeight* 0.82));

                    g.setColor(Color.ORANGE);
                    g.fillRect((int) (x * cellWidth +cellWidth*0.1), (int) (y * cellHeight +cellHeight*0.45), (int) (cellWidth * 0.8), (int) (cellHeight* 0.15));
                    g.fillRect((int) (x * cellWidth +cellWidth*0.45), (int) (y * cellHeight +cellHeight*0.1), (int) (cellWidth * 0.15), (int) (cellHeight* 0.8));



                } else if (field[y][x] == DOT_HUM2) {

                    g.setColor(Color.gray);
                    g.fillRect((int) (x * cellWidth +cellWidth*0.1), (int) (y * cellHeight +cellHeight*0.45), (int) (cellWidth * 0.815), (int) (cellHeight* 0.17));
                    g.fillRect((int) (x * cellWidth +cellWidth*0.45), (int) (y * cellHeight +cellHeight*0.1), (int) (cellWidth * 0.17), (int) (cellHeight* 0.82));

                    g.setColor(Color.green);
                    g.fillRect((int) (x * cellWidth +cellWidth*0.1), (int) (y * cellHeight +cellHeight*0.45), (int) (cellWidth * 0.8), (int) (cellHeight* 0.15));
                    g.fillRect((int) (x * cellWidth +cellWidth*0.45), (int) (y * cellHeight +cellHeight*0.1), (int) (cellWidth * 0.15), (int) (cellHeight* 0.8));



                }

                else {
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
            case STATE_DROW -> g.drawString("НИЧЬЯ", 230, getHeight() / 2);
            case STATE_WIN_HUMAN -> g.drawString("Ты выиграл!", 160, getHeight() / 2);
            case STATE_WIN_AI -> g.drawString("КОМП ПОБЕДИЛ!", 155, getHeight() / 2);
            default -> throw new RuntimeException("Произошла какая-то ерунда " + stateGameOver);
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
