package object.animation;

import java.util.Iterator;

public interface Animator {
	
	public Iterator<AnimationStep> getAnimationStepIterator();
	
	public void start();
	
	public void pause();
	
	public void reset();
	
}
