package object;

import java.util.LinkedList;
import java.util.List;

import object.modification.Modification;
import assignment.Material;

public abstract class ObjectPart {
	
	private Material material;
	
	private static final Material DEFAULT_MATERIAL = new Material();
	
	private Modification[] modifications;
	
	private Modification[] globalModifications;
	
	private List<ObjectPart> subParts;
	
	public ObjectPart(Modification[] modifications, Modification[] globalModifications) {
		this.init((Material) DEFAULT_MATERIAL.clone(), modifications, globalModifications);
	}
	
	public ObjectPart(Material material, Modification[] modifications, Modification[] globalModifications) {
		this.init(material, modifications, globalModifications);
	}
	
	private void init(Material material, Modification[] modifications, Modification[] globalModifications) {
		if (material == null || modifications == null || globalModifications == null) {
			throw new IllegalArgumentException("Arguments must not be null.");
		}
		this.material = material;
		this.modifications = modifications;
		this.globalModifications = globalModifications;
		this.subParts = new LinkedList<ObjectPart>();
	}
	
	public void addSubPart(ObjectPart subPart) {
		this.subParts.add(subPart);
	}
	
	public void removeSubPart(int i) {
		this.subParts.remove(i);
	}
	
	public int getSubPartsCount() {
		int subPartsChildrenCount = 0;
		for (ObjectPart subPart : subParts) {
			subPartsChildrenCount += subPart.getSubPartsCount();
		}
		return subPartsChildrenCount + 1;
	}
	
	public Iterable<ObjectPart> getSubParts() {
		return this.subParts;
	}

	/**
	 * @return the materials
	 */
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		if (material == null) {
			throw new IllegalArgumentException();
		}
		this.material = material;
	}

	/**
	 * @return the modifications
	 */
	public Modification[] getModifications() {
		return modifications;
	}

	/**
	 * @return the globalModifications
	 */
	public Modification[] getGlobalModifications() {
		return globalModifications;
	}

}
