package lamp;

import javax.media.opengl.GL2;

public class JumpLampAnimator implements LampAnimator {
	
	private boolean started = false;
	
	private double sinusDegree = 0.0;
	
	private double sinus = 0.0;
	
	private double rotate = 0.0;
	
	@Override
	public void start() {
		this.started = true;
	}
	
	@Override
	public void pause() {
		this.started = false;
	}

	@Override
	public void stop() {
		this.started = false;
		sinusDegree = 0.0;
	}

	@Override
	public void animateBase(GL2 gl) {
		if (started) {
			sinus =  Math.sin(sinusDegree) + 1;
			double height = Math.exp(-sinus * 7);
			gl.glTranslated(0, height * 1.5, 0);
		}
	}

	@Override
	public void animateLowerArm(GL2 gl) {
		if (started) {
			sinus =  Math.sin(sinusDegree) + 1;
			rotate = Math.exp(-sinus * 2);
			gl.glRotated(rotate * 50 - 40, 0, 0, 1.0);
		}
	}

	@Override
	public void animateJoint(GL2 gl) {
		if (started) {
			sinus =  Math.sin(sinusDegree);
			rotate = Math.exp(-sinus);
			gl.glRotated(-rotate * 40 + 100, 0, 0, 1.0);
		}
	}

	@Override
	public void animateUpperArm(GL2 gl) {
		if (started) {
		}	
	}

	@Override
	public void animateHead(GL2 gl) {
		if (started) {
			sinus =  Math.sin(sinusDegree);
			rotate = Math.exp(-sinus);
			gl.glRotated(-rotate * 5 - 20, 0, 0, 1.0);
			sinusDegree += 0.13;
		}
	}

}
