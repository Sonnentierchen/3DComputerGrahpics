package object.dynamicobjects.lamp;

import java.util.Iterator;
import java.util.LinkedList;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import object.modification.RotateModification;

public class ShakeHeadLampAnimator implements Animator {

	private double sinusDegree = 0.0;

	private boolean started = false;

	@Override
	public Iterator<AnimationStep> getAnimationStepIterator() {
		LinkedList<AnimationStep> steps = new LinkedList<AnimationStep>();
		
		for (int i = 0; i < 4; i++) {
			steps.add(new AnimationStep(new Modification[0]));
		}
		
		Modification rotate = new RotateModification(
				Math.sin(sinusDegree) * 10, 0, 1.0, 0);
		AnimationStep animationStep = new AnimationStep(
				new Modification[] { rotate });
		if (started) {
			this.sinusDegree += 0.3;
		}
		steps.add(animationStep);
		
		for (int i = 0; i < 3; i++) {
			steps.add(new AnimationStep(new Modification[0]));
		}
		
		return steps.iterator();
	}

	@Override
	public void start() {
		this.started = true;
	}

	@Override
	public void pause() {
		this.started = false;
	}

	@Override
	public void reset() {
		this.started = false;
		this.sinusDegree = 0.0;
	}

	@Override
	public void setAnimationSpeed(double speed) {
		// TODO Auto-generated method stub
		
	}

}
