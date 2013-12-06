package object.dynamicobjects.lamp;

import java.util.Iterator;
import java.util.LinkedList;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import object.modification.RotateModification;
import object.modification.TranslateModification;

public class JumpLampAnimator implements Animator {

	private boolean started = false;

	private double sinusDegree = 0.0;

	private double degreeInc = 0.15;

	private double speed = 1.0;

	private double jumpHeight = 1.0;

	private double scheduledJumpHeight = -1.0;

	private double currentHeight = 0.0;

	private double currentRotationLowerJoint = 0.0;

	private double currentRotationUpperJoint = 0.0;

	private double currentRotationHead = 0.0;

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
			sinusDegree += degreeInc * speed;
		}
		return animationSteps.iterator();
	}

	private AnimationStep animateBase() {
		LinkedList<Modification> steps = new LinkedList<Modification>();
		double height = Math.abs(Math.sin(sinusDegree));
		currentHeight = height;
		if (height < 0.1 && scheduledJumpHeight != -1) {
			jumpHeight = scheduledJumpHeight;
			scheduledJumpHeight = -1.0;
		}

		Modification translate = new TranslateModification(0, height * 1.5
				* jumpHeight, 0);

		double needToRotateBase = Math.sin(sinusDegree + Math.PI);
		if (needToRotateBase > 0) {
			steps.add(translate);
			double rotate = Math.sin(3 * (sinusDegree));
			Modification rotateMod = new RotateModification(-rotate * 30, 0, 0,
					1.0);
			//Modification translateFeet = new TranslateModification(
					//Math.sin(sinusDegree) / 2, 0, 0);
			//steps.add(translateFeet);
			if (needToRotateBase - 0.5 > 0) {
			}
			steps.add(rotateMod);
		} else {
		}
		return new AnimationStep(steps);
	}

	private AnimationStep animateLowerJoint() {
		LinkedList<Modification> steps = new LinkedList<Modification>();
		double needToRotateBase = Math.sin(sinusDegree + Math.PI);
		if (needToRotateBase > 0) {
			double rotate = Math.sin(3 * (sinusDegree));
			Modification rotation = new RotateModification(rotate * 30, 0, 0,
					1.0);
			Modification translateFeet = new RotateModification(
					Math.sin(sinusDegree) * 40, 0, 0, 1.0);
			steps.add(translateFeet);
			steps.add(rotation);
		}

		double rotate = Math.sin(sinusDegree);
		// double rotate = Math.exp(-bendingAngle * 3);
		Modification rotateMod;
		if (Math.sin(sinusDegree) >= 0) {
			rotateMod = new RotateModification(-rotate * 60, 0, 0, 1.0);
			currentRotationLowerJoint = rotate * 20;
		} else {
			rotateMod = new RotateModification(rotate * 60 * 0, 0, 0, 1.0);
			// Modification baseRotationCompensation = new
			// TranslateModification(0, Math.sin(sinusDegree) * 2, 0);
			// steps.add(baseRotationCompensation);
		}
		steps.add(rotateMod);
		return new AnimationStep(steps);
	}

	private AnimationStep animateLowerArm() {
		return new AnimationStep(new Modification[0]);
	}

	private AnimationStep animateMiddleJoint() {
		double rotate = Math.sin(sinusDegree);
		// double rotate = Math.exp(-bendingAngle);
		Modification rotateMod;
		if (Math.sin(sinusDegree) >= 0) {
			rotateMod = new RotateModification(rotate * 70, 0, 0, 1.0);
		} else {

			rotateMod = new RotateModification(-rotate * 70, 0, 0, 1.0);
		}
		return new AnimationStep(new Modification[] { rotateMod });
	}

	private AnimationStep animateUpperArm() {
		return new AnimationStep(new Modification[0]);
	}

	private AnimationStep animateHead() {
		double bendingAngle = Math.sin(2 * sinusDegree);
		Modification rotate;
		if (Math.sin(sinusDegree) >= 0) {
			rotate = new RotateModification(bendingAngle * 30 - 20, 0, 0, 1.0);
			currentRotationHead = bendingAngle * 30 - 20;
		} else {
			rotate = new RotateModification(currentRotationHead, 0, 0, 1.0);
		}
		return new AnimationStep(new Modification[] { rotate });
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
		this.speed = speed;
	}

	public void setJumpHeight(double jumpHeight) {
		this.jumpHeight = jumpHeight;
	}

	public void scheduleSetJumpHeight(double jumpHeight) {
		this.scheduledJumpHeight = jumpHeight;
	}

	public boolean isOnGround() {
		return Math.abs(currentHeight) < 0.1;
	}

}
