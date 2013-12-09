package object.dynamicobjects.lamp;

import java.util.Iterator;
import java.util.LinkedList;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import object.modification.RotateModification;
import object.modification.TranslateModification;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class JumpLampAnimator is an animator that makes the lamp jump.
 * 
 * @author zzb13fb
 * 
 */
public class JumpLampAnimator implements Animator {

	/**
	 * Indicates whether the animation has started.
	 */
	private boolean started = false;

	/**
	 * The current degree of the jump.
	 */
	private double sinusDegree = 0.0;

	/**
	 * The increment of the degree.
	 */
	private double degreeInc = 0.15;

	/**
	 * The speed of the animation.
	 */
	private double speed = 1.0;

	/**
	 * The height of the jump.
	 */
	private double jumpHeight = 1.0;

	/**
	 * The scheduled height for the next jump.
	 */
	private double scheduledJumpHeight = -1.0;

	/**
	 * The scheduled speed for the next jump.
	 */
	private double scheduledAnimationSpeed = -1.0;

	/**
	 * The current height of the lamp.
	 */
	private double currentHeight = 0.0;

	/**
	 * The current rotation of the head.
	 */
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
			/*
			 * If animation is started increase degree.
			 */
			sinusDegree += degreeInc * speed;
		}
		return animationSteps.iterator();
	}

	/**
	 * Returns the Animation Step for the base.
	 */
	private AnimationStep animateBase() {
		LinkedList<Modification> steps = new LinkedList<Modification>();
		double height = Math.abs(Math.sin(sinusDegree));
		currentHeight = height;
		/*
		 * When lamp is on the ground, or to be more exact lower than 0.1, set
		 * the next jump height. Same for speed.
		 */
		if (height < 0.1 && scheduledJumpHeight != -1) {
			jumpHeight = scheduledJumpHeight;
			scheduledJumpHeight = -1.0;
		}
		if (height < 0.1 && scheduledAnimationSpeed != -1) {
			speed = scheduledAnimationSpeed;
			scheduledAnimationSpeed = -1.0;
		}

		/*
		 * Translates the whole lamp to the specified height.
		 */
		Modification translate = new TranslateModification(0, height * 1.5
				* jumpHeight, 0);

		/*
		 * When lamp is in mid-air, rotate its base to make it look like feet.
		 */
		double needToRotateBase = Math.sin(sinusDegree + Math.PI);
		if (needToRotateBase > 0) {
			steps.add(translate);
			double rotate = Math.sin(-2 * (sinusDegree));
			Modification rotateMod = new RotateModification(-rotate * 30, 0, 0,
					1.0);
			steps.add(rotateMod);
		}
		return new AnimationStep(steps);
	}

	/**
	 * Returns the Animation Step for the base.
	 */
	private AnimationStep animateLowerJoint() {
		LinkedList<Modification> steps = new LinkedList<Modification>();
		/*
		 * When lamp is in mid-air and base is being rotated, rotate the upper
		 * part of lamp against this rotation
		 */
		double needToRotateBase = Math.sin(sinusDegree + Math.PI);
		if (needToRotateBase > 0) {
			double rotate = Math.sin(-2 * (sinusDegree));
			Modification rotation = new RotateModification(rotate * 30, 0, 0,
					1.0);
			Modification translateFeet = new RotateModification(
					Math.sin(sinusDegree) * 40, 0, 0, 1.0);
			steps.add(translateFeet);
			steps.add(rotation);
		}

		double rotate = Math.sin(sinusDegree);
		Modification rotateMod;
		/*
		 * Moderate the jumpHeight to make it look more realistic.
		 */
		double jumpHeight_ = jumpHeight;
		if (jumpHeight > 1) {
			jumpHeight_ = jumpHeight / 1.5;
		}
		/*
		 * Compress, expand on ground and compress and expand in air again.
		 * Therefore it is being determined whether the lamp is on ground (>=0) or in the air (<0).
		 */
		if (Math.sin(sinusDegree) >= 0) {
			rotateMod = new RotateModification(jumpHeight_ * -rotate * 60, 0,
					0, 1.0);
		} else {
			rotateMod = new RotateModification(jumpHeight_ * rotate * 60 * 0,
					0, 0, 1.0);
		}
		steps.add(rotateMod);
		return new AnimationStep(steps);
	}

	/**
	 * Returns the Animation Step for the base.
	 */
	private AnimationStep animateLowerArm() {
		return new AnimationStep(new Modification[0]);
	}

	/**
	 * Returns the Animation Step for the base.
	 */
	private AnimationStep animateMiddleJoint() {
		double rotate = Math.sin(sinusDegree);
		Modification rotateMod;
		/*
		 * Same principles as for the lower joint.
		 */
		double jumpHeight_ = jumpHeight;
		if (jumpHeight > 1) {
			jumpHeight_ = jumpHeight / 1.5;
		}
		if (Math.sin(sinusDegree) >= 0) {
			rotateMod = new RotateModification(jumpHeight_ * rotate * 80, 0, 0,
					1.0);
		} else {

			rotateMod = new RotateModification(jumpHeight_ * -rotate * 80, 0,
					0, 1.0);
		}
		return new AnimationStep(new Modification[] { rotateMod });
	}

	/**
	 * Returns the Animation Step for the base.
	 */
	private AnimationStep animateUpperArm() {
		return new AnimationStep(new Modification[0]);
	}

	/**
	 * Returns the Animation Step for the base.
	 */
	private AnimationStep animateHead() {
		double bendingAngle = Math.sin(2 * sinusDegree);
		Modification rotate;
		/*
		 * Rotate the head when the lamp is on the ground to make it look more eager.
		 */
		if (Math.sin(sinusDegree) >= 0) {
			rotate = new RotateModification(
					jumpHeight * bendingAngle * 20 - 20, 0, 0, 1.0);
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

	/**
	 * Sets the jump height.
	 * 
	 * @param jumpHeight
	 *            the jump height to set
	 */
	public void setJumpHeight(double jumpHeight) {
		this.jumpHeight = jumpHeight;
	}

	/**
	 * Sets a jump height that is to be set for the next jump.
	 * 
	 * @param jumpHeight
	 *            the jump height for the next jump
	 */
	public void scheduleSetJumpHeight(double jumpHeight) {
		this.scheduledJumpHeight = jumpHeight;
	}

	/**
	 * Sets an animation speed for the next jump.
	 * 
	 * @param speed
	 *            the speed for the next jump
	 */
	public void scheduleSetAnimationSpeed(double speed) {
		this.scheduledAnimationSpeed = speed;
	}

	/**
	 * Returns if the lamp is on ground.
	 * 
	 * @return if the lamp is on ground.
	 */
	public boolean isOnGround() {
		return Math.abs(currentHeight) < 0.1;
	}

}
