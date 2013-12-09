package object;

import com.jogamp.opengl.util.gl2.GLUT;

import object.modification.Modification;
import assignment.Material;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class GlutObjectPart represents a glut object, and therefore renders one.
 * 
 * @author zzb13fb
 *
 */
public class GlutObjectPart extends ObjectPart {

	/**
	 * Constructor of class GlutObject Part.
	 * 
	 * @param modifications the modifications of this object part
	 * @param globalModifications the global modifications of this object part
	 */
	public GlutObjectPart(Modification[] modifications,
			Modification[] globalModifications) {
		super(modifications, globalModifications);
	}

	/**
	 * Constructor of class GlutObject Part.
	 * 
	 * @param material the material of this object part
	 * @param modifications the modifications of this object part
	 * @param globalModifications the global modifications of this object part
	 */
	public GlutObjectPart(Material material,
			Modification[] modifications, Modification[] globalModifications) {
		super(material, modifications, globalModifications);
	}
	
	/**
	 * Show the glut object.
	 * 
	 * @param glut the glut context to show the glut object.
	 */
	public void doGlutObject(GLUT glut) {
		glut.glutSolidSphere(1.00, 20, 20);
	}

}
