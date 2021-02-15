package com.javarush.games.snake;

import com.javarush.engine.cell.*;


import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int x;
    private int y;
    private List<GameObject> snakeParts;
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    private static final String HEAD_SIGN =  "\uD83D\uDC7E";
    private static final String BODY_SIGN =  "\u26AB";

    public Snake(int x, int y) {
        this.snakeParts = new ArrayList<>();
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void draw(Game game) {
        for (GameObject gameObject : snakeParts) {
            if (gameObject.equals(snakeParts.get(0))) {
                game. setCellValueEx(gameObject.x, gameObject.y, Color.NONE,HEAD_SIGN, (isAlive? Color.BLACK : Color.RED), 75);
            } else {
                game. setCellValueEx(gameObject.x, gameObject.y, Color.NONE, BODY_SIGN, (isAlive? Color.BLACK : Color.RED), 75);
            }
        }
    }

    public void setDirection(Direction direction) {
        if ((Direction.LEFT.equals(direction) || (snakeParts.get(0).x == snakeParts.get(1).x)) && Direction.RIGHT.equals(this.direction)) {
            return;
        }
        if ((Direction.RIGHT.equals(direction) || (snakeParts.get(0).x == snakeParts.get(1).x))&& Direction.LEFT.equals(this.direction)) {
            return;
        }
        if ((Direction.UP.equals(direction) || (snakeParts.get(0).y == snakeParts.get(1).y)) && Direction.DOWN.equals(this.direction)) {
            return;
        }
        if ((Direction.DOWN.equals(direction) || (snakeParts.get(0).y == snakeParts.get(1).y)) && Direction.UP.equals(this.direction)) {
            return;
        }
        this.direction = direction;
    }

    public void move(Apple apple) {
        GameObject head = createNewHead();
        if (head.x < 0 || head.x >= SnakeGame.WIDTH || head.y < 0 || head.y >= SnakeGame.HEIGHT) {
            isAlive = false;
            return;
        }
        if (checkCollision(head)) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, head);
        if (head.x == apple.x && head.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
    }

    public GameObject createNewHead() {
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;
        if (direction.equals(Direction.LEFT)) {
            return new GameObject(headX - 1, headY);
        } else if (direction.equals(Direction.DOWN)) {
            return new GameObject(headX, headY + 1);
        } else if (direction.equals(Direction.UP)) {
            return new GameObject(headX, headY - 1);
        } else {
            return new GameObject(headX + 1, headY);
        }
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject telo : snakeParts) {
            if (gameObject.x == telo.x && gameObject.y == telo.y) {
                return true;
            }
        }
        return false;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public int getLength() {
        return snakeParts.size();
    }
}
