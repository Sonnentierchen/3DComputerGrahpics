package object.animation;

import java.util.List;

import object.modification.Modification;

public class AnimationStep {
	
	private Modification[] modifications;
	
	public AnimationStep(List<Modification> modifications) {
		if (modifications == null) {
			throw new IllegalArgumentException();
		}
		this.modifications = new Modification[modifications.size()];
		for (int i = 0; i < modifications.size(); i++) {
			this.modifications[i] = modifications.get(i);
		}
	}
	
	public AnimationStep(Modification[] modifications) {
		if (modifications == null) {
			throw new IllegalArgumentException();
		}
		this.modifications = modifications;
	}
	
	public Modification[] getModifications() {
		return modifications;
	}

}
