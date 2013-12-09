package object.dynamicobjects.dynamicbarrel;

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
 * Class RollingBarrelAnimator is an {@link Animator} that rolls a barrel for a
 * short time and slows it down evenutally.
 * 
 * @author zzb13fb
 * 
 */
public class RollingBarrelAnimator implements Animator {

	/**
	 * Indicates whether the rolling has already started to know when to roll
	 * the barrel.
	 */
	private boolean started = false;

	/**
	 * The current degree of the barrel.
	 */
	private double rollingDegree = 0.0;

	/**
	 * The amount by which the rolling degree has to be increased.
	 */
	private double rollingDegreeIncrease = 0.05;

	/**
	 * The amount by which the rolling is slowed down over time.
	 */
	private static final double rollingDegreeIncreaseDec = 0.000025;

	/**
	 * The animation speed.
	 */
	private double speed = 1.0;

	@Override
	public Iterator<AnimationStep> getAnimationStepIterator() {
		LinkedList<AnimationStep> steps = new LinkedList<AnimationStep>();
		Modification moveBarrel = new TranslateModification(-0.2
				* rollingDegree, 0, 0);
		Modification translateForSelfRotate = new TranslateModification(0, 0.5,
				0);
		Modification rotateSelf = new RotateModification(rollingDegree * 18, 0,
				0, 1.0);
		Modification translateBackFromSelfRotate = new TranslateModification(0,
				-0.5, 0);
		steps.add(new AnimationStep(
				new Modification[] { moveBarrel, translateForSelfRotate,
						rotateSelf, translateBackFromSelfRotate }));
		if (started && rollingDegree < 10) {
			rollingDegree += rollingDegreeIncrease * speed;
			rollingDegreeIncrease -= rollingDegree * rollingDegreeIncreaseDec
					* speed;
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
		this.rollingDegree = 0.0;
		rollingDegreeIncrease = 0.05;
	}

	@Override
	public void setAnimationSpeed(double speed) {
		if (speed > 0) {
			this.speed = speed;
		}
	}

}
