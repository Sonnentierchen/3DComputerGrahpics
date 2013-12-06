package object.dynamicobjects.lamp;

import javax.media.opengl.GL2;

public class JumpLampAnimator_Old implements LampAnimator {

	private boolean started = false;

	private double sinusDegree = 0.0;

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
		double heightSinus = Math.sin(sinusDegree) + 1;
		double height = Math.exp(-heightSinus * 7.5);
		gl.glTranslated(0, height * 1.5, 0);

		double needToRotateBase = Math.sin(sinusDegree + Math.PI) - 0.5;
		if (needToRotateBase > 0) {
			double rotate = Math.sin(3 * (sinusDegree - 0.5));
			gl.glRotated(-rotate * 30, 0, 0, 1.0);
		}

	}

	@Override
	public void animateLowerJoint(GL2 gl) {
		double needToRotateBase = Math.sin(sinusDegree + Math.PI) - 0.5;
		if (needToRotateBase > 0) {
			double rotate = Math.sin(3 * (sinusDegree - 0.5));
			gl.glRotated(rotate * 30, 0, 0, 1.0);
		}

		double bendingAngle = Math.sin(sinusDegree) + 1;
		double rotate = Math.exp(-bendingAngle * 3);
		gl.glRotated(rotate * 60 - 40, 0, 0, 1.0);
	}

	@Override
	public void animateLowerArm(GL2 gl) {
	}

	@Override
	public void animateMiddleJoint(GL2 gl) {
		double bendingAngle = Math.sin(sinusDegree);
		double rotate = Math.exp(-bendingAngle);
		gl.glRotated(-rotate * 45 + 95, 0, 0, 1.0);
	}

	@Override
	public void animateUpperArm(GL2 gl) {
	}

	@Override
	public void animateHead(GL2 gl) {
		double bendingAngle = Math.sin(sinusDegree);
		gl.glRotated(bendingAngle * 30 - 20, 0, 0, 1.0);
		if (started) {
			sinusDegree += 0.13;
		}
	}

}
