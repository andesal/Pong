package com.pong.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pong.MyGdxGame;
import com.pong.states.PlayState;

public class Ball {

    private Texture texture;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    public Ball(float velX, float velY) {
        this.texture = new Texture("ball.png");
        float x = MyGdxGame.WIDTH/2 - texture.getWidth()/2;
        float y = MyGdxGame.HEIGHT/ 2 - texture.getHeight()/2;
        position = new Vector3(x, y, 0);
        velocity = new Vector3(velX, velY, 0);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float dt) {
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        position.add(this.velocity.x * dt, this.velocity.y * dt, 0);

    }

    public void dispose() {
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y) {
        this.velocity = new Vector3(x, y, 0);
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
