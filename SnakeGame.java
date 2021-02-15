package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static final int GOAL = 28;
    private int score;
    private Apple apple;
    private Snake snake;
    private int turnDelay;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }

    private void createGame() {
        isGameStopped = false;
        score = 0;
        turnDelay = 300;
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH / 2,HEIGHT / 2);
        createNewApple();
        setScore(score);
        drawScene();
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x,y, Color.GREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (Key.SPACE.equals(key) && isGameStopped) {
            createGame();
        }
        if (Key.LEFT.equals(key)) {
            snake.setDirection(Direction.LEFT);
        }
        if (Key.RIGHT.equals(key)) {
            snake.setDirection(Direction.RIGHT);
        }
        if (Key.UP.equals(key)) {
            snake.setDirection(Direction.UP);
        }
        if (Key.DOWN.equals(key)) {
            snake.setDirection(Direction.DOWN);
        }
    }

    private void createNewApple() {
        Apple newApple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        while (snake.checkCollision(newApple)) {
            newApple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        }
        apple = newApple;
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKGREY, "Проиграл! ХАХАХА!", Color.DARKGREEN, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKGREY, "WIN! EEE!", Color.VIOLET, 75);
    }
}
