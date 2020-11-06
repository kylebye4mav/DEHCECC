package edu.lewis.cs.kylevbye;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * 
 * @author	Kyle V Bye
 * @see com.badlogic.gdx.graphics.OrthographicCamera
 * @see	CameraEffect
 */
public class InvertCamera extends CameraEffect {
	
	///
	///	Functions
	///
	@Override
	public void start() {
		cam.rotate(180);
		isActive = true;
		cam.update();
	}
	
	@Override
	public void perform() {
		
		if (progress < duration && isActive) {
			++progress;
		}
		else if (isActive) {
			progress = 0;
			isActive = false;
			duration = 0;
			cam.rotate(180);
		}
		
	}
	
	///
	///	Constructors
	///
	public InvertCamera() {
		super();
	}
	
	public InvertCamera(OrthographicCamera camIn, int durationIn, int intensityIn) {
		super(camIn, durationIn, intensityIn);
	}

}
