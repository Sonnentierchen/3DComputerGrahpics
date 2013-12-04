package object.animation;

import object.modification.Modification;

public class AnimationStep {
	
	private Modification[] modifications;
	
	public AnimationStep(Modification[] modifications) {
		this.modifications = modifications;
	}
	
	public Modification[] getModifications() {
		return modifications;
	}

}
