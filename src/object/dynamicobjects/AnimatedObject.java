package object.dynamicobjects;

import object.animation.Animator;

public interface AnimatedObject {
	
	public void startAnimation();
	
	public void pauseAnimation();
	
	public void stopAnimation();
	
	public void setAnimator(Animator animator);
	
	public void setAnimationSpeed(double speed);

}
