package object.modification;

import javax.media.opengl.GL2;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class TranslateModification stores a translation modification.
 * 
 * @author zzb13fb
 *
 */
public class TranslateModification implements Modification {
	
	private double x;
	
	private double y;
	
	private double z;
	
	/**
	 * The constructor of class TranslateModification.
	 * 
	 * @param x the translation along the x axis
	 * @param y the translation along the y axis
	 * @param z the translation along the z axis
	 */
	public TranslateModification(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void doModification(GL2 gl) {
		gl.glTranslated(x, y, z);
	}

}
