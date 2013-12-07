package object.dynamicobjects.lamp;

import java.util.Iterator;
import java.util.LinkedList;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import object.modification.RotateModification;
import object.modification.TranslateModification;

public class HeadLightLampAnimator implements Animator {
	
	private double lookUpAngle = 50.0;
	
	private double bendingAngle = 0.0;

	@Override
	public Iterator<AnimationStep> getAnimationStepIterator() {
		LinkedList<AnimationStep> steps = new LinkedList<AnimationStep>();
		steps.add(new AnimationStep(new Modification[]{}));
		Modification rotateLowerJoint = new RotateModification(bendingAngle, 0, 0, 1.0);
		steps.add(new AnimationStep(new Modification[] {rotateLowerJoint}));
		steps.add(new AnimationStep(new Modification[]{}));
		Modification rotateUpperJoint = new RotateModification(-bendingAngle * 2, 0, 0, 1.0);
		steps.add(new AnimationStep(new Modification[] {rotateUpperJoint}));
		steps.add(new AnimationStep(new Modification[]{}));
		Modification rotateHead = new RotateModification(- lookUpAngle, 0.0, 0.0, 1.0);
		steps.add(new AnimationStep(new Modification[] {rotateHead}));
		for (int i = 0; i < 2; i++) {
			steps.add(new AnimationStep(new Modification[]{}));
		}
		return steps.iterator();
	}
	
	public void setLookupAngle(double angle) {
		this.lookUpAngle = angle;
	}
	
	public void setBendingAngle(double angle) {
		this.bendingAngle = angle;
	}

	@Override
	public void setAnimationSpeed(double speed) {
	}

	@Override
	public void start() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void reset() {
	}

}
