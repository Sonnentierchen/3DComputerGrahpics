package object.dynamicobjects.lamp;

import java.util.Iterator;
import java.util.LinkedList;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import object.modification.RotateModification;
import object.modification.TranslateModification;

public class BasicJumpLampAnimator implements Animator {

	private boolean started = false;

	private double sinusDegree = 0.0;
	
	@Override
	public Iterator<AnimationStep> getAnimationStepIterator() {
		LinkedList<AnimationStep> animationSteps = new LinkedList<AnimationStep>();
		animationSteps.add(animateBase());
		animationSteps.add(animateLowerJoint());
		animationSteps.add(animateLowerArm());
		animationSteps.add(animateMiddleJoint());
		animationSteps.add(animateUpperArm());
		animationSteps.add(animateHead());
		animationSteps.add(new AnimationStep(new Modification[0]));
		animationSteps.add(new AnimationStep(new Modification[0]));
		if (started) {
			sinusDegree += 0.13;
		}
		return animationSteps.iterator();
	}
	
	private AnimationStep animateBase() {
		LinkedList<Modification> steps = new LinkedList<Modification>();
		double heightSinus = Math.sin(sinusDegree) + 1;
		double height = Math.exp(-heightSinus * 7.5);
		Modification translate = new TranslateModification(0, height * 1.5, 0);
		steps.add(translate);
		
		double needToRotateBase = Math.sin(sinusDegree + Math.PI) - 0.5;
		if (needToRotateBase > 0) {
			double rotate = Math.sin(3 * (sinusDegree - 0.5));
			Modification rotateMod = new RotateModification(-rotate * 30, 0, 0, 1.0);
			steps.add(rotateMod);
		}
		return new AnimationStep(steps);
	}
	
	private AnimationStep animateLowerJoint() {
		LinkedList<Modification> steps = new LinkedList<Modification>();
		double needToRotateBase = Math.sin(sinusDegree + Math.PI) - 0.5;
		if (needToRotateBase > 0) {
			double rotate = Math.sin(3 * (sinusDegree - 0.5));
			Modification rotation = new RotateModification(rotate * 30, 0, 0, 1.0);
			steps.add(rotation);
		}

		double bendingAngle = Math.sin(sinusDegree) + 1;
		double rotate = Math.exp(-bendingAngle * 3);
		Modification rotateMod = new RotateModification(rotate * 60 - 40, 0, 0, 1.0);
		steps.add(rotateMod);
		return new AnimationStep(steps);
	}
	
	private AnimationStep animateLowerArm() {
		return new AnimationStep(new Modification[0]);
	}
	
	private AnimationStep animateMiddleJoint() {
		double bendingAngle = Math.sin(sinusDegree);
		double rotate = Math.exp(-bendingAngle);
		Modification rotateMod = new RotateModification(-rotate * 45 + 95, 0, 0, 1.0);
		return new AnimationStep(new Modification[] {rotateMod});
	}
	
	private AnimationStep animateUpperArm() {
		return new AnimationStep(new Modification[0]);
	}
	
	private AnimationStep animateHead() {
		double bendingAngle = Math.sin(sinusDegree);
		Modification rotate = new RotateModification(bendingAngle * 30 - 20, 0, 0, 1.0);
		return new AnimationStep(new Modification[]{rotate});
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
	}

	@Override
	public void setAnimationSpeed(double speed) {
		// TODO Auto-generated method stub
		
	}


}
