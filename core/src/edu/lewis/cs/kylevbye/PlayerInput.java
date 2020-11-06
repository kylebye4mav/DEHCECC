package edu.lewis.cs.kylevbye;

/**
 * This class is a container for many booleans
 * that are true or false whether the player
 * is intended to do certain actions when the
 * key for that action is held.
 */
public class PlayerInput {
	
	/**
	 * True when shift is held. Shift in this
	 * case is intended to be used as a modifier
	 * key.
	 */
	public static boolean shiftHeld = false;
	
	//	Movement
	/**
	 * True if the user is holding W. Intended as
	 * an action that involves moving upwards.
	 */
	public static boolean movingUp = false;
	/**
	 * True if the user is holding A. Intended as
	 * an action that involves moving to the left.
	 */
	public static boolean movingLeft = false;
	/**
	 * True if the user is holding S. Intended as
	 * an action that involves moving downwards.
	 */
	public static boolean movingDown = false;
	/**
	 * True if the user is holding D. Intended as
	 * an action that involves moving to the right.
	 */
	public static boolean movingRight = false;
	
	//	Camera Effects
	/**
	 * True if the user is holding G. Intended as
	 * an action that involves flipping the camera
	 * for the duration held.
	 */
	public static boolean inverting = false;
	
	public static boolean isLocking = false;
	
	public static boolean isUnlocking = false;
	

}
