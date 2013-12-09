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
 * Class HeadLightLampAnimator is an animator that points the head of the lamp
 * up or down by the specified angle.
 * 
 * @author zzb13fb
 * 
 */
public class HeadLightLampAnimator implements Animator {

	/**
	 * The angle by which the lamp looks up or down.
	 */
	private double lookUpAngle = 50.0;

	/**
	 * The bending angle of the joints.
	 */
	private double bendingAngle = 0.0;

	@Override
	public Iterator<AnimationStep> getAnimationStepIterator() {
		LinkedList<AnimationStep> steps = new LinkedList<AnimationStep>();
		steps.add(new AnimationStep(new Modification[] {}));
		/*
		 * Bend the arms to create different looks.
		 */
		Modification rotateLowerJoint = new RotateModification(bendingAngle, 0,
				0, 1.0);
		steps.add(new AnimationStep(new Modification[] { rotateLowerJoint }));
		steps.add(new AnimationStep(new Modification[] {}));
		Modification rotateUpperJoint = new RotateModification(
				-bendingAngle * 2, 0, 0, 1.0);
		steps.add(new AnimationStep(new Modification[] { rotateUpperJoint }));
		steps.add(new AnimationStep(new Modification[] {}));
		Modification rotateHead = new RotateModification(-lookUpAngle, 0.0,
				0.0, 1.0);
		steps.add(new AnimationStep(new Modification[] { rotateHead }));
		for (int i = 0; i < 2; i++) {
			steps.add(new AnimationStep(new Modification[] {}));
		}
		return steps.iterator();
	}

	/**
	 * Sets the angle by which the lamp looks up or down.
	 * @param angle the angle by which the lamp looks up or down
	 */
	public void setLookupAngle(double angle) {
		this.lookUpAngle = angle;
	}

	/**
	 * Sets the angle by which the lamp bends its arms to create different looks.
	 * @param angle the angle by which the lamp bends its arms
	 */
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
