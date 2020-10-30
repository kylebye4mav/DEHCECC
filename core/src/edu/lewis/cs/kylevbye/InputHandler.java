package edu.lewis.cs.kylevbye;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class InputHandler extends InputAdapter {
	
	///
	///	Functions
	///
	
	@Override
	public boolean keyDown(int keyCode) {
			
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) PlayerInput.shiftHeld = true;
			
		//	Movement WASD (with Image) (On held)
		if (keyCode == Keys.W) PlayerInput.movingUp = true;
		if (keyCode == Keys.A) PlayerInput.movingLeft = true;
		if (keyCode == Keys.S) PlayerInput.movingDown = true;
		if (keyCode == Keys.D) PlayerInput.movingRight = true;
		
		return true;
			
	}

	@Override 
	public boolean keyUp(int keyCode) {
		
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) PlayerInput.shiftHeld = false;
		
		//	Movement
		if (keyCode == Keys.W) PlayerInput.movingUp = false;
		if (keyCode == Keys.A) PlayerInput.movingLeft = false;
		if (keyCode == Keys.S) PlayerInput.movingDown = false;
		if (keyCode == Keys.D) PlayerInput.movingRight = false;
		
		return true;
		
	}
	
}
