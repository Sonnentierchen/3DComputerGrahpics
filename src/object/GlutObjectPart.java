package object;

import com.jogamp.opengl.util.gl2.GLUT;

import object.modification.Modification;
import assignment.Material;

public class GlutObjectPart extends ObjectPart {

	public GlutObjectPart(Modification[] modifications,
			Modification[] globalModifications) {
		super(modifications, globalModifications);
	}

	public GlutObjectPart(Material material,
			Modification[] modifications, Modification[] globalModifications) {
		super(material, modifications, globalModifications);
	}
	
	public void doGlutObject(GLUT glut) {
		glut.glutSolidSphere(0.20, 20, 20);
	}

}
