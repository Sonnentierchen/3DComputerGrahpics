package object.dynamicobjects.dynamicbarrel;

import java.util.Iterator;
import java.util.LinkedList;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import object.modification.RotateModification;
import object.modification.TranslateModification;

public class RollingBarrelAnimator implements Animator {

	private boolean started = false;

	private double rollingDegree = 0.0;

	private double rollingDegreeIncrease = 0.05;

	private static final double rollingDegreeIncreaseDec = 0.000025;
	
	private double speed = 1.0;

	@Override
	public Iterator<AnimationStep> getAnimationStepIterator() {
		LinkedList<AnimationStep> steps = new LinkedList<AnimationStep>();
		Modification moveBarrel = new TranslateModification(
				-0.2 * rollingDegree, 0, 0);
		Modification translateForSelfRotate = new TranslateModification(0, 0.5,
				0);
		Modification rotateSelf = new RotateModification(rollingDegree * 28,
				0, 0, 1.0);
		Modification translateBackFromSelfRotate = new TranslateModification(0,
				-0.5, 0);
		steps.add(new AnimationStep(
				new Modification[] { moveBarrel, translateForSelfRotate,
						rotateSelf, translateBackFromSelfRotate }));
		if (started && rollingDegree < 10) {
			rollingDegree += rollingDegreeIncrease * speed;
			rollingDegreeIncrease -= rollingDegree * rollingDegreeIncreaseDec * speed;
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
