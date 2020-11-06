package edu.lewis.cs.kylevbye;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * 
 * @author	Kyle V Bye
 * @see com.badlogic.gdx.graphics.OrthographicCamera
 */
public abstract class CameraEffect {
	
	///
	///	Fields
	///
	protected OrthographicCamera cam;
	protected int duration;
	protected int intensity;
	protected int progress;
	protected boolean isActive;
	
	///
	///	Getters
	///
	public OrthographicCamera getCam() { return cam; }
	public int getDuration() { return duration; }
	public int getIntensity() { return intensity; }
	public int getProgress() { return progress; }
	public boolean isActive() { return isActive; }
	
	///
	///	Setters
	///
	public void setCam(OrthographicCamera camIn) { this.cam = camIn; }
	public void setDuration(int durationIn) { this.duration = durationIn; }
	public void setIntensity(int intensityIn) { this.intensity = intensityIn; }
	public void setProgress(int progressIn) { this.progress = progressIn; }
	public void setActive(boolean activeFlag) { this.isActive = activeFlag; }
	
	///
	///	Functions
	///
	public abstract void start();
	public abstract void perform();
	
	///
	///	toString
	///
	@Override
	public String toString() {
		return new String();
	}
	
	///
	///	Constructors
	///
	public CameraEffect() {
		this(null,0,0);
	}
	
	public CameraEffect(OrthographicCamera camIn, int durationIn, int intensityIn) {
		setActive(false);
		setCam(camIn);
		setDuration(durationIn);
		setIntensity(intensityIn);
		setProgress(0);
	}
	
}
