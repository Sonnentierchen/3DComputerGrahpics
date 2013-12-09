package object.animation;

import java.util.List;

import object.modification.Modification;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * A step in an animation. Therefore it contains multiple {@link Modification
 * Modifications}.
 * 
 * @author Florian Blume, zzb13fb
 * 
 */
public class AnimationStep {

	/* The modifications of the animation step. */
	private Modification[] modifications;

	/**
	 * Constructor of class AnimationStep.
	 * 
	 * @param modifications
	 *            the modifications of this animation step
	 */
	public AnimationStep(List<Modification> modifications) {
		if (modifications == null) {
			throw new IllegalArgumentException();
		}
		this.modifications = new Modification[modifications.size()];
		for (int i = 0; i < modifications.size(); i++) {
			this.modifications[i] = modifications.get(i);
		}
	}

	/**
	 * Constructor of class AnimationStep.
	 * 
	 * @param modifications
	 *            the modifications of this animation step
	 */
	public AnimationStep(Modification[] modifications) {
		if (modifications == null) {
			throw new IllegalArgumentException();
		}
		this.modifications = modifications;
	}

	/**
	 * Returns the modifications of this animation step.
	 * 
	 * @return the modifications of this animation step
	 */
	public Modification[] getModifications() {
		return modifications;
	}

}
