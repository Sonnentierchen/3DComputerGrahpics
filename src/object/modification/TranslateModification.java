package object.modification;

import javax.media.opengl.GL2;

public class TranslateModification implements Modification {
	
	private double x;
	
	private double y;
	
	private double z;
	
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
