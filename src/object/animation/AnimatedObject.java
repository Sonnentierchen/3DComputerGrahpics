package object.animation;

import object.animation.Animator;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Interface AnimatedObject provides methods for an animated 3D object.
 * 
 * @author zzb13fb
 *
 */
public interface AnimatedObject {
	
	/**
	 * Starts the animation.
	 */
	public void startAnimation();
	
	/**
	 * Pauses the animation.
	 */
	public void pauseAnimation();
	
	/**
	 * Stops the animation.
	 */
	public void stopAnimation();
	
	/**
	 * Sets the {@link Animator} of this animated object.
	 * 
	 * @param animator the animator to be set
	 */
	public void setAnimator(Animator animator);
	
	/**
	 * Sets the animation speed of this animated object.
	 * 
	 * @param speed the speed to be set
	 */
	public void setAnimationSpeed(double speed);

}
