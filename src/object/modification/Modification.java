package object.modification;

import javax.media.opengl.GL2;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Interface Modification stores a 3D modification performed by the rendering context gl.
 * @author zzb13fb
 *
 */
public interface Modification {
	
	/**
	 * Does the modification performed by gl.
	 * 
	 * @param gl the rendering context
	 */
	public void doModification(GL2 gl);

}
