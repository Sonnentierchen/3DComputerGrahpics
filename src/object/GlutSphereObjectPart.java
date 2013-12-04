package object;

import object.modification.Modification;
import assignment.Material;

public class GlutSphereObjectPart extends ObjectPart {

	public GlutSphereObjectPart(Modification[] modifications,
			Modification[] globalModifications) {
		super(modifications, globalModifications);
	}

	public GlutSphereObjectPart(Material material,
			Modification[] modifications, Modification[] globalModifications) {
		super(material, modifications, globalModifications);
	}

}
