package object.modification;

import javax.media.opengl.GL2;

public class ScaleModification implements Modification {
	
	private double x;
	
	private double y;
	
	private double z;
	
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
