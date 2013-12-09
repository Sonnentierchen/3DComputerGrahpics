package object.modification;

import javax.media.opengl.GL2;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class NullModification performs no action.
 *  
 * @author zzb13fb
 *
 */
public class NullModification implements Modification {

	@Override
	public void doModification(GL2 gl) {
	}

}
