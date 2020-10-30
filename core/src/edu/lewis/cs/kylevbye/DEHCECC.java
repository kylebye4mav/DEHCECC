package edu.lewis.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DEHCECC extends ApplicationAdapter {
	int WIDTH, HEIGHT;
	OrthographicCamera cam;
	Image player;
	InputHandler inputHandler;
	SpriteBatch batch;
	ArrayList<Image> images;
	
	@Override
	public void create () {
		
		//	480p
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		images = new ArrayList<Image>();
		batch = new SpriteBatch();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		Texture playerTex = new Texture("badlogic.jpg");
		player = new Image(
				new TextureRegion(playerTex), 
				(WIDTH/2) - playerTex.getWidth()/2, (HEIGHT/2) - playerTex.getHeight()/2, 
				playerTex.getWidth(), playerTex.getHeight(), 0, 0, 0, 0f, 1f, 1f
				);
		images.add(player);		
		cam.translate(WIDTH/2, HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		//	Input Handler
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
		
	}

	@Override
	public void render () {
		
		handleInput();
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (Image image : images) {
			batch.draw(
					image.getTexture(), image.getX(), image.getY(), 
					image.getOrgX(), image.getOrgY(), image.getWidth(), 
					image.getHeight(), image.getScaleX(), image.getScaleY(), 
					image.getAngle()
					);
		}
		batch.end();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
	}
	
	///
	///	Helper Methods
	///
	private void handleInput() {
		
		//	Player Movement
		int dx, dy;
		dx = 0; dy = 0;
		if (PlayerInput.movingUp) dy += 5;
		if (PlayerInput.movingLeft) dx -= 5;
		if (PlayerInput.movingDown) dy -= 5;
		if (PlayerInput.movingRight) dx += 5;
		player.setX(player.getX() + dx);
		player.setY(player.getY() + dy);
		cam.translate(dx, dy);
		
	}
	
}
