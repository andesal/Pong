package com.pong.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pong.MyGdxGame;

public class Player {

    private Texture texture;
    private Vector3 position;
    private Rectangle bounds;
    private boolean human, left;
    private int score = 0;

    public Player(boolean human, boolean left) {
        this.human = human;
        this.left = left;
        this.texture = new Texture("paddle.png");
        float x = left ? 0 : MyGdxGame.WIDTH - texture.getWidth();
        float y = MyGdxGame.HEIGHT / 2 - texture.getHeight() / 2;
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float dt, float posBall) {
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        if (!human && posBall + texture.getHeight() > 0 && posBall + texture.getHeight() < MyGdxGame.HEIGHT) {
            position.y = posBall;
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(int y) {
        if (y <= 0) {
            position.y = 0;
        } else if (y + texture.getHeight() >= MyGdxGame.HEIGHT) {
            position.y = MyGdxGame.HEIGHT - texture.getHeight();
        } else {
            this.position.y = y;
        }
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score+=1;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isHuman() {
        return human;
    }

    public boolean isLeft() {
        return left;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
