package object.animation;

import java.util.Iterator;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Interface Animator provides methods for an animator for an object that is rendered by an
 * {@link DynamicRender} and therefore contains an animation step for each part
 * of the object.
 * 
 * @author zzb13fb
 * 
 */
public interface Animator {

	/**
	 * Returns the animation steps of this animator. 
	 * 
	 * @return the animation steps of this animator
	 */
	public Iterator<AnimationStep> getAnimationStepIterator();

	/***
	 * Sets the animation speed of this animator.
	 * 
	 * @param speed the speed of this animator
	 */
	public void setAnimationSpeed(double speed);

	/**
	 * Starts the animation.
	 */
	public void start();

	/**
	 * Pauses the animation.
	 */
	public void pause();

	/**
	 * Resets the animation.
	 */
	public void reset();

}
