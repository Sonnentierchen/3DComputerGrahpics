package lamp;

import javax.media.opengl.GL2;

public class ShakeHeadLampAnimator implements LampAnimator {
	
	private double sinusDegree = 0.0;

	@Override
	public void start() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void animateBase(GL2 gl) {
	}

	@Override
	public void animateLowerArm(GL2 gl) {
	}

	@Override
	public void animateJoint(GL2 gl) {
	}

	@Override
	public void animateUpperArm(GL2 gl) {
		gl.glRotated(Math.sin(sinusDegree) * 10, 0, 1.0, 0);
		sinusDegree += 0.3;
	}

	@Override
	public void animateHead(GL2 gl) {
	}

}
