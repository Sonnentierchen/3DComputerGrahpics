package object.modification;

import javax.media.opengl.GL2;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class RotateModification stores a rotation modification.
 * @author zzb13fb
 *
 */
public class RotateModification implements Modification {
	
	double angle;
	
	double x;
	
	double z;
	
	double y;
	
	/**
	 * Constructor of class RotateModification.
	 * 
	 * @param angle the angle of the rotation
	 * @param x the x axis of the rotation
	 * @param y the y axis of the rotation
	 * @param z the z axis of the rotation
	 */
	public RotateModification(double angle, double x, double y, double z) {
		this.angle = angle;
		this.x = x;
		this.z = z;
		this.y = y;
	}

	@Override
	public void doModification(GL2 gl) {
		gl.glRotated(angle, x, y, z);
	}

}
