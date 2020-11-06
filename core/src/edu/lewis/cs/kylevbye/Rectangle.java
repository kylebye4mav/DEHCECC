package edu.lewis.cs.kylevbye;

import com.badlogic.gdx.math.Vector2;

/**
 * This class encapsulates a 2d coordinate (x,y)
 * and its dimensions (width, height). Represents
 * a two dimensional rectangle.
 * 
 * @author	Kyle V Bye
 *
 */
public class Rectangle {
	
	///
	///	Fields
	///
	private int x, y;
	private int width, height;
	
	///
	///	Getters
	///
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	///
	///	Setters
	///
	public void setX(int xIn) { this.x = xIn; }
	public void setY(int yIn) { this.y = yIn; }
	public void setWidth(int widthIn) { this.width = widthIn; }
	public void setHeight(int heightIn) { this.height = heightIn; }
	
	///
	///	Functions
	///
	/**
	 * Sets this instance's x, y, width, height with its corresponding
	 * parameters.
	 * 
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 * @param	widthIn	width
	 * @param	heightIn	height
	 */
	public void setBounds(int xIn, int yIn, int widthIn, int heightIn) {
		setX(xIn); setY(yIn);
		setWidth(widthIn); setHeight(heightIn);
	}
	
	/**
	 * Returns true if the coordinate parameters are inside the
	 * rectangle of this instance.
	 * 
	 * Note: (0,0) will always return true. (width, height) will return false.
	 * (width-1, height-1) will return true.
	 * 
	 * @param	xIn	x coordinate to check.
	 * @param	yIn	y coordinate to check.
	 * @return	true/false
	 */
	public boolean coordinateWithin(int xIn, int yIn) {
		return (yIn >= y && yIn <= height-1) && (xIn >= x && xIn <= width-1) ;
	}
	

	/**
	 * Unboxes the parameter and calls <code>coordinateWith(int,int)</code>
	 * Note: values will be casted to 32 bit integers.
	 * 
	 * Returns true if the coordinate parameters are inside the
	 * rectangle of this instance.
	 * 
	 * Note: (0,0) will always return true. (width, height) will return false.
	 * (width-1, height-1) will return true.
	 * 
	 * @param	xIn	x coordinate to check.
	 * @param	yIn	y coordinate to check.
	 * @return	true/false
	 * @see	Rectangle#coordinateWithin(int, int)
	 */
	public boolean coordinateWith(Vector2 vectorIn) {
		
		int xIn, yIn;
		
		//	Unbox
		xIn = (int)vectorIn.x;
		yIn = (int)vectorIn.y;
		
		return coordinateWithin(xIn, yIn);
		
	}
	
	
	///
	///	Constructors
	///
	/**
	 * Initializes this instance with all its fields to 0.
	 */
	public Rectangle() {
		setBounds(0, 0, 0, 0);
	}
	
	/**
	 * Initializes this instance using the <code>setBounds(int,int,int,int)</code>.
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 * @param	widthIn	width
	 * @param	heightIn	height
	 * @see	Rectangle#setBounds(int, int, int, int)
	 */
	public Rectangle(int xIn, int yIn, int widthIn, int heightIn) {
		setBounds(xIn, yIn, widthIn, heightIn);
	}

}
