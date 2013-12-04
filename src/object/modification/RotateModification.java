package object.modification;

import javax.media.opengl.GL2;

public class RotateModification implements Modification {
	
	double angle;
	
	double x;
	
	double z;
	
	double y;
	
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
