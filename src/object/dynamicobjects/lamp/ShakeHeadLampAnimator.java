package object.dynamicobjects.lamp;

import javax.media.opengl.GL2;

public class ShakeHeadLampAnimator implements LampAnimator {
	
	private double sinusDegree = 0.0;

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animateBase(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animateLowerJoint(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animateLowerArm(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animateMiddleJoint(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animateUpperArm(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animateHead(GL2 gl) {
		gl.glRotated(Math.sin(sinusDegree) * 10, 0, 1.0, 0);
		sinusDegree += 0.3;
	}

}
