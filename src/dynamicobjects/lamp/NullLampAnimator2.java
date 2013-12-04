package dynamicobjects.lamp;

import java.util.Iterator;
import java.util.LinkedList;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;

public class NullLampAnimator2 implements Animator {
	
	private LinkedList<AnimationStep> animationSteps;
	
	public NullLampAnimator2() {
		animationSteps = new LinkedList<AnimationStep>();
		for (int i = 0; i < 6; i++) {
			animationSteps.add(new AnimationStep(new Modification[0]));
		}
	}

	@Override
	public Iterator<AnimationStep> getAnimationStepIterator() {
		return animationSteps.iterator();
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
