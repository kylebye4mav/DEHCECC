package edu.lewis.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class DEHCECC extends ApplicationAdapter {
	int WIDTH, HEIGHT;
	int WORLDWIDTH, WORLDHEIGHT;
	InvertCamera iCam;
	OrthographicCamera cam;
	Image player;
	InputHandler inputHandler;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	ArrayList<Image> images;
	BitmapFont font;
	boolean locked, locking;
	Rectangle jail;
	
	///
	///	String Constants
	///
	final String camCordForm = "Cam: [X:%.2f; Y:%.2f]";
	//				W			U
	//	Shift	A	S	D	G	J
	final String playerInputForm = "                     \t%s            %s\n%5s     %s     %s     %s     %s     %s";
	
	@Override
	public void create () {
		
		//	720p
		Gdx.graphics.setWindowedMode(1280, 720);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//	World
		WORLDWIDTH = (int)((float)WIDTH*1.5);
		WORLDHEIGHT = (int)((float)HEIGHT*1.5f);
		
		images = new ArrayList<Image>();
		batch = new SpriteBatch();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		
		//	Load player image
		Texture playerTex = new Texture("player.png");
		player = new Image(
				new TextureRegion(playerTex), 
				(WIDTH/2) - playerTex.getWidth()/2, (HEIGHT/2) - playerTex.getHeight()/2, 
				playerTex.getWidth(), playerTex.getHeight(), 0, 0, 0, 0f, 1f, 1f
				);
		images.add(player);		
		
		//	Load Map Images
		loadMapImages();
		
		cam.translate(WIDTH/2, HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		font = new BitmapFont();
		iCam = new InvertCamera(cam, 0, 0);
		
		//	Input Handler
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
		
		//	Locking Mechanism
		locking = false;
		locked = false;
		jail = new Rectangle();
		shapeRenderer = new ShapeRenderer();
		
	}

	@Override
	public void render () {
		
		System.out.format("Player: [%d,%d]\n", player.getX(), player.getY());
		
		handleInput();
		iCam.perform();
		wrapCameraX(WORLDWIDTH);
		lockCameraY(WORLDHEIGHT);
		if (locking) {
			handleLocking();
			locking = false;
		}
		if (locked) jailCamera(jail);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		//	Draw Images
		for (Image image : images) {
			batch.draw(
					image.getTexture(), image.getX(), image.getY(), 
					image.getOrgX(), image.getOrgY(), image.getWidth(), 
					image.getHeight(), image.getScaleX(), image.getScaleY(), 
					image.getAngle()
					);
		}
		
		//	Draw Text
		font.draw(batch, String.format(camCordForm, cam.position.x, cam.position.y), cam.position.x-(WIDTH/2), cam.position.y+(HEIGHT/2)-(HEIGHT/100));
		drawInputs();
		drawControls();
		
		//	Draw Jail if needed.
		if (locked) {
			shapeRenderer.rect(jail.getX(), jail.getY(), jail.getWidth(), jail.getHeight());
		}
		
		//	Reset toggle keys
		PlayerInput.isLocking = false;
		PlayerInput.isUnlocking = false;
		
		batch.end();
		shapeRenderer.end();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (Image i : images) i.dispose();
	}
	
	///
	///	Helper Methods
	///
	private void loadMapImages() {
		
		//	house 1
		Texture house1Text = new Texture("house1.png");
		Image house1 = new Image(
				new TextureRegion(house1Text), 700, 0, 
				house1Text.getWidth(), house1Text.getHeight(), 0, 0, 0, 0f, 1f, 1f
				);
		images.add(house1);
		
		//	house 2
		Texture house2Text = new Texture("house2.png");
		Image house2 = new Image(
				new TextureRegion(house2Text), -100, 0, 
				house2Text.getWidth(), house2Text.getHeight(), 0, 0, 0, 0f, 1f, 1f
				);
		images.add(house2);		
		
		//	house 3
		Texture house3Text = new Texture("house3.png");
		Image house3 = new Image(
				new TextureRegion(house3Text), -700, 500, 
				house3Text.getWidth(), house3Text.getHeight(), 0, 0, 0, 0f, 1f, 1f
				);
		images.add(house3);
		
		//	house 4
		Texture house4Text = new Texture("house4.png");
		Image house4 = new Image(
				new TextureRegion(house4Text), 1200, 500, 
				house4Text.getWidth(), house4Text.getHeight(), 0, 0, 0, 0f, 1f, 1f
				);
		images.add(house4);
		
		//	Same houses, diffent spots
		images.add(new Image(new TextureRegion(house1Text), 0, 680, 
				house1Text.getWidth(), house1Text.getHeight(), 0, 0, 0, 0f, 1f, 1f
				));
		images.add(new Image(new TextureRegion(house2Text), 0, -680, 
				house1Text.getWidth(), house1Text.getHeight(), 0, 0, 0, 0f, 1f, 1f
				));
		
		
	}
	
	/*
	 *	Takes actions based on input provided.
	 */
	private void handleInput() {
		
		//	Player Movement
		int dx, dy;
		dx = 0; dy = 0;
		if (PlayerInput.movingUp) dy += 3;
		if (PlayerInput.movingLeft) dx -= 3;
		if (PlayerInput.movingDown) dy -= 3;
		if (PlayerInput.movingRight) dx += 3;
		if (PlayerInput.shiftHeld) {
			dx *= 3; dy *= 3;
		}
		player.setX(player.getX() + dx);
		player.setY(player.getY() + dy);
		cam.translate(dx, dy);
		
		//	Cam Effects
		if (PlayerInput.inverting) iCam.setDuration(iCam.getDuration()+1);
		if (iCam.getDuration() > 0 && !PlayerInput.inverting && !iCam.isActive()) iCam.start();
		
		//	Locking Mechanism
		if (PlayerInput.isLocking && !locked) {
			locking = true;
			locked = true;
		}
		
		//	Unlocking Mechanism
		if (PlayerInput.isUnlocking) {
			locked = false;
		}
		
	}
	
	/*
	 * Draws what controls are activated within this render.
	 */
	private void drawInputs() {
		//				W			U
		//	Shift	A	S	D	G	J
		
		String w, u, shift, a, s, d, g, j;
		
		//	Default
		w = "_"; u = "_"; shift = "___";
		a = "_"; s = "_"; d = "_";
		g = "_"; j = "_";
		
		//	Shift
		if (PlayerInput.shiftHeld) shift = "Shift";
		
		//	Movement
		if (PlayerInput.movingUp) w = "W";
		if (PlayerInput.movingLeft) a = "A";
		if (PlayerInput.movingDown) s = "S";
		if (PlayerInput.movingRight) d = "D";
		
		//	Cam Effects
		if (PlayerInput.inverting) g = "G";
		
		//	Locking Mechanism
		if (PlayerInput.isLocking) j = "J";
		if (PlayerInput.isUnlocking) u = "U";
		
		
		//
		//	Draw
		//
		float dX = WIDTH/45;
		float wPositionX = cam.position.x-(WIDTH/2)+(WIDTH/10);
		float frontRowY = cam.position.y-(HEIGHT/2)+(HEIGHT/4);
		float bottomRowY = frontRowY-(HEIGHT/30);
		
		//	Shift
		font.draw(batch, shift, wPositionX-(3.5f*dX), bottomRowY);
		
		//	Movement
		font.draw(batch, w, wPositionX, frontRowY);
		font.draw(batch, a, wPositionX-dX, bottomRowY);
		font.draw(batch, s, wPositionX, bottomRowY);
		font.draw(batch, d, wPositionX+dX, bottomRowY);
		
		//	Cam Effects
		font.draw(batch, g, wPositionX+(2*dX), bottomRowY);
		
		//	Coord
		font.draw(batch, u, wPositionX+(3*dX), frontRowY);
		font.draw(batch, j, wPositionX+(3*dX), bottomRowY);
	}
	
	/*
	 * Draws the controls on the screen.
	 */
	private void drawControls() {
		
		//	WASD - Move
		//	Shift + WASD - Sprint
		//	G - Flip (Hold for longer duration)
		//	U - Unjail
		//	J - Jail
		
		String controlString = "WASD - Move\nShift + WASD - Sprint\nG - Flip (Hold for longer duration)\nU - Unjail\nJ - Jail";
		font.draw(batch, controlString, cam.position.x-(WIDTH/2)+(WIDTH/10)-(3*WIDTH/45), cam.position.y-(HEIGHT/2)+(HEIGHT/4)-(HEIGHT/10));
		
	}
	
	/*
	 * Wraps the camera's x and y position based on the given parameters
	 * if needed.
	 */
	private void wrapCamera(int targetWidth, int targetHeight) {
		
		wrapCameraX(targetWidth);
		wrapCameraY(targetHeight);
		
	}
	
	/*
	 * Wraps the camera's x position based on the given parameters
	 * if needed.
	 */
	private void wrapCameraX(int targetWidth) {
		
		if (cam.position.x > targetWidth) {
			cam.translate(-2*targetWidth, 0);
			player.setX(player.getX()-(2*targetWidth));
		}
		else if (cam.position.x < -1*targetWidth) {
			cam.translate(2*targetWidth, 0);
			player.setX(player.getX() + 2*targetWidth);
		}
		
	}
	
	/*
	 * Wraps the camera's y position based on the given parameters
	 * if needed.
	 */
	private void wrapCameraY(int targetHeight) {
		
		if (cam.position.y > targetHeight) {
			cam.translate(0, -2*targetHeight);
			player.setY(player.getY()-(2*targetHeight));
		}
		else if (cam.position.y < -1*targetHeight) {
			cam.translate(0, 2*targetHeight);
			player.setY(player.getY() + 2*targetHeight);
		}
		
	}
	
	/*
	 * Locks the camera's x and y position based on the given parameters.
	 */
	private void lockCamera(int targetWidth, int targetHeight) {
		
		lockCameraX(targetWidth);
		lockCameraY(targetHeight);
		
	}
	
	/*
	 * Locks the camera's x position based on the given parameters.
	 */
	private void lockCameraX(int targetWidth) {
		
		//	Unbox
		int x = (int)cam.position.x;
		
		if (x > targetWidth) {
			cam.translate(0, targetWidth-x);
			player.setX(player.getX()+targetWidth-x);
		}
		else if (x < -targetWidth) {
			cam.translate(0, -targetWidth-x);
			player.setX(player.getX()-x-targetWidth);
		}
		
	}
	
	/*
	 * Locks the camera's y position based on the given parameters.
	 */
	private void lockCameraY(int targetHeight) {
		
		//	Unbox
		int y = (int)cam.position.y;
		
		if (y > targetHeight) {
			cam.translate(0, targetHeight-y);
			player.setY(player.getY()+targetHeight-y);
		}
		else if (y < -targetHeight) {
			cam.translate(0, -targetHeight-y);
			player.setY(player.getY()-y-targetHeight);
		}
		
	}
	
	private void handleLocking() {
		
		if (!locked) return;
		
		//	Right Corner
		float x, y;
		x = cam.position.x - player.getWidth();
		y = cam.position.y - player.getHeight();
		
		float width, height;
		width = player.getWidth()*2f;
		height = player.getHeight()*2f;
		jail.setBounds((int)x, (int)y, (int)width, (int)height);
		
		
	}
	
	private void jailCamera(Rectangle rect) {
		
		int x = (int) cam.position.x;
		int y = (int) cam.position.y;
		
		if (x < rect.getX()) {
			cam.translate(rect.getX()-x, 0);
			player.setX(player.getX()+(rect.getX()-x));
		}
		else if (x > rect.getX()+rect.getWidth()) {
			cam.translate(rect.getWidth()+rect.getX()-x, 0);
			player.setX(player.getX()+(rect.getWidth()+rect.getX()-x));
		}
		
		if (y < rect.getY()) {
			cam.translate(0, rect.getY()-y);
			player.setY(player.getY()+(rect.getY()-y));
		}
		else if (y > rect.getY()+rect.getHeight()) {
			cam.translate(0, rect.getHeight()+rect.getY()-y);
			player.setY(player.getY()+(rect.getHeight()+rect.getY()-y));
		}
		
	}
	
}
