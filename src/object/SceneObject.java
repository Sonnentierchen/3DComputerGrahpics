package object;

import java.util.LinkedList;
import java.util.List;

import assignment.Material;

import com.jogamp.opengl.util.texture.Texture;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class SceneObject is a collection of {@link ObjectPart ObjectParts}, but
 * because of their tree structure only stores the root parts.
 * 
 * @author zzb13fb
 * 
 */
public class SceneObject {

	/**
	 * Indicates whether textures are to be shown.
	 */
	private boolean showTextures = true;

	/**
	 * All root parts of this scene object.
	 */
	private List<ObjectPart> rootObjectParts;

	/**
	 * Constructor of class SceneObject.
	 * 
	 * @param objectParts the object parts to be stored by this scene object
	 */
	public SceneObject(ObjectPart[] objectParts) {
		if (objectParts == null) {
			throw new IllegalArgumentException("objectParts must not be null.");
		}
		rootObjectParts = new LinkedList<ObjectPart>();
		for (ObjectPart objectPart : objectParts) {
			this.rootObjectParts.add(objectPart);
		}
	}

	/**
	 * Constructor of class SceneObject.
	 * 
	 * @param objectParts the object parts to be stored by this scene object
	 */
	public SceneObject(List<ObjectPart> objectParts) {
		if (objectParts == null) {
			throw new IllegalArgumentException("objectParts must not be null.");
		}
		rootObjectParts = new LinkedList<ObjectPart>();
		for (ObjectPart objectPart : objectParts) {
			this.rootObjectParts.add(objectPart);
		}
	}

	/**
	 * Returns all root {@link ObjectPart ObjectParts} stored by this scene object.
	 * @return the root parts of this scene object
	 */
	public Iterable<ObjectPart> getRootObjectParts() {
		return this.rootObjectParts;
	}

	/**
	 * Returns the count of all object parts of this scene object.
	 * @return the count of all object parts
	 */
	public int getTotalObjectPartsCount() {
		int totalObjectPartsCount = 0;
		for (ObjectPart objectPart : rootObjectParts) {
			totalObjectPartsCount += objectPart.getSubPartsCount();
		}
		return totalObjectPartsCount;
	}

	/**
	 * Retrieves recursively all {@link ObjectPart ObjectParts} stored in this scene object.
	 * @return the list of object parts stored in this scene object
	 */
	public List<ObjectPart> getAllObjectParts() {
		List<ObjectPart> parts = new LinkedList<ObjectPart>();
		for (ObjectPart rootPart : rootObjectParts) {
			parts.addAll(retrieveObjectPartWithSubParts(rootPart));
		}
		return parts;
	}

	/**
	 * Retrieves recursively all {@link ObjectPart ObjectParts} stored in this scene object.
	 * 
	 * @param filter the filter class to retrieve only certain {@link ObjectPart ObjectParts}
	 * @return the list of object parts stored in this scene object of the given class
	 */
	public List<ObjectPart> getAllObjectParts(Class<? extends ObjectPart> filter) {
		List<ObjectPart> parts = new LinkedList<ObjectPart>();
		for (ObjectPart rootPart : rootObjectParts) {
			for (ObjectPart part : retrieveObjectPartWithSubParts(rootPart)) {
				if (part.getClass().equals(filter)) {
					parts.add(part);
				}
			}
		}
		return parts;
	}

	/**
	 * Retrieves recursively all sub-parts of the given object part as well as itself.
	 * @param currentPart the current part which's sub-parts are to be retrieved
	 * @return the list of the current part and its sub-parts
	 */
	private List<ObjectPart> retrieveObjectPartWithSubParts(
			ObjectPart currentPart) {
		List<ObjectPart> parts = new LinkedList<ObjectPart>();
		parts.add(currentPart);
		for (ObjectPart subPart : currentPart.getSubParts()) {
			parts.addAll(retrieveObjectPartWithSubParts(subPart));
		}
		return parts;
	}

	/**
	 * Sets if textures are to be shown
	 * @param showTextures if textures are to be shown
	 */
	public void showTextures(boolean showTextures) {
		for (ObjectPart objectPart : rootObjectParts) {
			if (objectPart instanceof MeshObjectPart) {
				((MeshObjectPart) objectPart).showTexture(showTextures);
			}
		}
		this.showTextures = showTextures;
	}

	/**
	 * Returns if textures are shown.
	 * @return if textures are shown
	 */
	public boolean getShowTextures() {
		return this.showTextures;
	}

	/**
	 * Sets the textures for all {@link ObjectPart ObjectParts} stored in this scene object.
	 * @param textures the textures to set
	 */
	public void setTextures(Texture[] textures) {
		if (textures == null) {
			throw new IllegalArgumentException();
		}
		List<ObjectPart> meshParts = getAllObjectParts(MeshObjectPart.class);
		if (textures.length != meshParts.size()) {
			throw new IllegalArgumentException(
					"Given textures contain not enough or too many textures for this object.");
		}
		for (int i = 0; i < meshParts.size(); i++) {
			((MeshObjectPart) meshParts.get(i)).setTexture(textures[i]);
		}
	}

	/**
	 * Sets the materials for all {@link ObjectPart ObjectParts} stored in this scene object.
	 * @param materials the materials to set
	 */
	public void setMaterials(Material[] materials) {
		if (materials == null) {
			throw new IllegalArgumentException();
		}
		List<ObjectPart> objectParts = getAllObjectParts();
		if (materials.length != objectParts.size()) {
			throw new IllegalArgumentException(
					"Given materials contain not enough or too many materials for this object.");
		}
		for (int i = 0; i < objectParts.size(); i++) {
			objectParts.get(i).setMaterial(materials[i]);
		}
	}

	/**
	 * Shows or hides all lights stored in this scene object.
	 * @param showLight show or hide all lights
	 */
	public void showLight(boolean showLight) {
		for (ObjectPart light : getAllObjectParts(LightObjectPart.class)) {
			((LightObjectPart) light).showLight(showLight);
		}
	}

}
