package com.pong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.pong.MyGdxGame;
import com.pong.sprites.Ball;
import com.pong.sprites.Player;

import java.util.Random;


public class PlayState extends State implements InputProcessor {

    public final static Rectangle BOUNDS = new Rectangle(0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
    private Texture bg;
    private Ball ball;
    private Player paddle1, paddle2;
    private BitmapFont font;
    boolean winner = false;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg.png");
        initBall();
        paddle1 = new Player(true, true);
        paddle2 = new Player(false, false);
        Gdx.input.setInputProcessor(this);
        font = new BitmapFont();
    }

    public void initBall() {
        int x = randomNumber(300, 600);
        x = x%2 == 0 ? -x : x;
        int y = randomNumber(100, 300);
        y = y%2 == 0 ? -y : y;
        ball = new Ball(x, y);
    }

    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {
        ball.update(dt);
        paddle1.update(dt, ball.getPosition().y);
        paddle2.update(dt, ball.getPosition().y);
        checkCollisions();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg, 0,0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        if (winner) {
            String s = paddle1.getScore() == 21 ? "Player 1 wins" : "Player 2 wins";
            font.draw(sb, s, 5, MyGdxGame.HEIGHT- 10);
            ball.dispose();
            paddle1.dispose();
            paddle2.dispose();
        } else {
            font.draw(sb, ""+paddle1.getScore(), 5, MyGdxGame.HEIGHT- 10);
            font.draw(sb, ""+paddle2.getScore(), MyGdxGame.WIDTH - 20, MyGdxGame.HEIGHT- 10);
            sb.draw(ball.getTexture(), ball.getPosition().x, ball.getPosition().y);
            sb.draw(paddle1.getTexture(), paddle1.getPosition().x, paddle1.getPosition().y);
            sb.draw(paddle2.getTexture(), paddle2.getPosition().x, paddle2.getPosition().y);
        }
        sb.end();
    }

    public void checkCollisions() {
        boolean out = false;
        if (ball.getPosition().y <= 0 || ball.getPosition().y + ball.getTexture().getHeight() >= BOUNDS.getHeight()) {
            ball.setVelocity(ball.getVelocity().x, -ball.getVelocity().y);
        } else if (ball.getPosition().x < 0) {
            paddle2.incrementScore();
            out = true;
        } else if (ball.getPosition().x + ball.getTexture().getWidth() > MyGdxGame.WIDTH){
            paddle1.incrementScore();
            out = true;
        } else {
            checkPaddleCollision(paddle1.getBounds(), ball.getBounds(), paddle1.isLeft());
            checkPaddleCollision(paddle2.getBounds(), ball.getBounds(), paddle2.isLeft());
        }
        if(out) {
            float x = MyGdxGame.WIDTH/2 - ball.getTexture().getWidth()/2;
            float y = MyGdxGame.HEIGHT/ 2 - ball.getTexture().getHeight()/2;
            ball.getPosition().x = x;
            ball.getPosition().y = y;
            if (paddle1.getScore() == 21 || paddle2.getScore() == 21) {
                winner = true;
            }  else {
                initBall();
            }

        }
    }

    public void checkPaddleCollision(Rectangle paddleBounds, Rectangle ballBounds, boolean isLeft) {
        if (ballBounds.overlaps(paddleBounds)) {
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(ballBounds, paddleBounds, intersection);
            if (!isLeft) {
                ball.setVelocity(-Math.abs(ball.getVelocity().x), ball.getVelocity().y) ;
            } else {
                ball.setVelocity(Math.abs(ball.getVelocity().x), ball.getVelocity().y) ;
            }
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        ball.dispose();
        paddle1.dispose();
        paddle2.dispose();
    }

    public static int randomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min)+ min;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Rectangle paddle1Bounds = paddle1.getBounds();
        Rectangle paddle2Bounds = paddle2.getBounds();
        if (paddle1.isHuman() && screenX > 0 && screenX < paddle1.getTexture().getWidth()*4) {
            paddle1.setPosition(MyGdxGame.HEIGHT - screenY);
        }
        if (paddle2.isHuman() && (screenX < MyGdxGame.WIDTH && screenX > (MyGdxGame.WIDTH - (paddle2.getTexture().getWidth()* 4)))) {
            paddle2.setPosition(MyGdxGame.HEIGHT - screenY);
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
