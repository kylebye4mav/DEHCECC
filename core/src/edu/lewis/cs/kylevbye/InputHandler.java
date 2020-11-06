package edu.lewis.cs.kylevbye;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class InputHandler extends InputAdapter {
	
	///
	///	Functions
	///
	
	@Override
	public boolean keyDown(int keyCode) {
			
		switch (keyCode) {
		
		//	Shift
		case Keys.SHIFT_LEFT:
			PlayerInput.shiftHeld = true;
			break;
		case Keys.SHIFT_RIGHT:
			PlayerInput.shiftHeld = true;
			break;
		
		//	Movement WASD (with Image) (On held)
		case Keys.W:
			System.out.println("W Down!");
			PlayerInput.movingUp = true;
			break;
		case Keys.A:
			System.out.println("A Down!");
			PlayerInput.movingLeft = true;
			break;
		case Keys.S:
			System.out.println("S Down!");
			PlayerInput.movingDown = true;
			break;
		case Keys.D:
			System.out.println("D Down!");
			PlayerInput.movingRight = true;
			break;
		
		//	Camera Effects (G --> Invert)
		case Keys.G:
			System.out.println("G Down!");
			PlayerInput.inverting = true;
			break;
		
		//	Locking Mechanism
		case Keys.U:
			System.out.println("U Pressed!");
			PlayerInput.isUnlocking = true;
			break;
		case Keys.J:
			System.out.println("J Pressed!");
			PlayerInput.isLocking = true;
			break;
			
		}
		
		
		return true;
			
	}

	@Override 
	public boolean keyUp(int keyCode) {
		
		switch (keyCode) {
		
		//	Shift
		case Keys.SHIFT_LEFT:
			PlayerInput.shiftHeld = false;
			break;
		case Keys.SHIFT_RIGHT:
			PlayerInput.shiftHeld = false;
			break;
		
		//	Movement WASD (with Image) (On held)
		case Keys.W:
			System.out.println("W Lifted!");
			PlayerInput.movingUp = false;
			break;
		case Keys.A:
			System.out.println("A Lifted!");
			PlayerInput.movingLeft = false;
			break;
		case Keys.S:
			System.out.println("S Lifted!");
			PlayerInput.movingDown = false;
			break;
		case Keys.D:
			System.out.println("D Lifted!");
			PlayerInput.movingRight = false;
			break;
		
		//	Camera Effects (G --> Invert)
		case Keys.G:
			System.out.println("G Lifted!");
			PlayerInput.inverting = false;
			break;
			
		}
		
		return true;
		
	}
	
}
