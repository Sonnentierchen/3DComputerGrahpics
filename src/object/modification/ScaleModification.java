package object.modification;

import javax.media.opengl.GL2;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class ScaleModification stores a scaling modification.
 * 
 * @author zzb13fb
 * 
 */
public class ScaleModification implements Modification {
	
	private double x;
	
	private double y;
	
	private double z;
	
	/**
	 * Constructor of class ScaleModification.
	 * 
	 * @param x the scaling on the x axis
	 * @param y the scaling on the y axis
	 * @param z the scaling on the z axis
	 */
	public ScaleModification(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void doModification(GL2 gl) {
		gl.glScaled(x, y, z);
	}

}
