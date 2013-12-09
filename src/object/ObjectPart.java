package object;

import java.util.LinkedList;
import java.util.List;

import object.modification.Modification;
import assignment.Material;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class ObjectPart is a part of an object and can contain subparts to create a tree structure.
 * Therefore sub-parts (also ObjectParts) can be added to the list of sub-parts of this ObjectPart.
 * @author zzb13fb
 *
 */
public abstract class ObjectPart {
	
	/**
	 * The material of this object part.
	 */
	private Material material;
	
	private static final Material DEFAULT_MATERIAL = new Material();
	
	/**
	 * The private modifications of this object part.
	 */
	private Modification[] modifications;
	
	/**
	 * The global modifications of this object part which also apply for its sub-parts.
	 */
	private Modification[] globalModifications;
	
	/**
	 * The sub-parts of this object part.
	 */
	private List<ObjectPart> subParts;
	
	/**
	 * Constructor of class ObjectPart
	 * @param modifications the private modifications of this object part
	 * @param globalModifications the global modifications of this object part which also apply for its sub-parts
	 */
	public ObjectPart(Modification[] modifications, Modification[] globalModifications) {
		this.init((Material) DEFAULT_MATERIAL.clone(), modifications, globalModifications);
	}
	
	/**
	 * Constructor of class ObjectPart
	 * @param material the material of this object part
	 * @param modifications the private modifications of this object part
	 * @param globalModifications the global modifications of this object part which also apply for its sub-parts
	 */
	public ObjectPart(Material material, Modification[] modifications, Modification[] globalModifications) {
		this.init(material, modifications, globalModifications);
	}
	
	/**
	 * Initialises this ObjectPart.
	 */
	private void init(Material material, Modification[] modifications, Modification[] globalModifications) {
		if (material == null || modifications == null || globalModifications == null) {
			throw new IllegalArgumentException("Arguments must not be null.");
		}
		this.material = material;
		this.modifications = modifications;
		this.globalModifications = globalModifications;
		this.subParts = new LinkedList<ObjectPart>();
	}
	
	/**
	 * Adds the given sub-part as a direct sub-part.
	 * Objectparts can be stacked together to create a tree structure.
	 * @param subPart the sub-part to add
	 */
	public void addSubPart(ObjectPart subPart) {
		this.subParts.add(subPart);
	}
	
	/**
	 * Removes the direct sub-part specified by the index.
	 * @param i the index of the direct sub-part
	 */
	public void removeSubPart(int i) {
		this.subParts.remove(i);
	}
	
	/**
	 * Returns the count of all sub-parts, retrieved recursively.
	 * @return the count of all sub-parts
	 */
	public int getSubPartsCount() {
		int subPartsChildrenCount = 0;
		for (ObjectPart subPart : subParts) {
			subPartsChildrenCount += subPart.getSubPartsCount();
		}
		return subPartsChildrenCount + 1;
	}
	
	/**
	 * Returns all direct sub-parts.
	 * @return all direct sub-parts
	 */
	public Iterable<ObjectPart> getSubParts() {
		return this.subParts;
	}

	/**
	 * @return the materials
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * Sets the material of this object part.
	 * @param material the material to set
	 */
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
