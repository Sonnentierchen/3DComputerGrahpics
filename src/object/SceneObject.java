package object;

import java.util.LinkedList;
import java.util.List;

import assignment.Material;

import com.jogamp.opengl.util.texture.Texture;

public class SceneObject {

	private boolean showTextures = true;

	private List<ObjectPart> rootObjectParts;

	public SceneObject(ObjectPart[] objectParts) {
		if (objectParts == null) {
			throw new IllegalArgumentException("objectParts must not be null.");
		}
		rootObjectParts = new LinkedList<ObjectPart>();
		for (ObjectPart objectPart : objectParts) {
			this.rootObjectParts.add(objectPart);
		}
	}

	public SceneObject(List<ObjectPart> objectParts) {
		if (objectParts == null) {
			throw new IllegalArgumentException("objectParts must not be null.");
		}
		rootObjectParts = new LinkedList<ObjectPart>();
		for (ObjectPart objectPart : objectParts) {
			this.rootObjectParts.add(objectPart);
		}
	}

	public Iterable<ObjectPart> getRootObjectParts() {
		return this.rootObjectParts;
	}

	public int getTotalObjectPartsCount() {
		int totalObjectPartsCount = 0;
		for (ObjectPart objectPart : rootObjectParts) {
			totalObjectPartsCount += objectPart.getSubPartsCount();
		}
		return totalObjectPartsCount;
	}
	
	public List<ObjectPart> getAllObjectParts() {
		List<ObjectPart> parts = new LinkedList<ObjectPart>();
		for (ObjectPart rootPart : rootObjectParts) {
			parts.addAll(retrieveObjectPartWithSubParts(rootPart));
		}
		return parts;
	}

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

	private List<ObjectPart> retrieveObjectPartWithSubParts(
			ObjectPart currentPart) {
		List<ObjectPart> parts = new LinkedList<ObjectPart>();
		parts.add(currentPart);
		for (ObjectPart subPart : currentPart.getSubParts()) {
			parts.addAll(retrieveObjectPartWithSubParts(subPart));
		}
		return parts;
	}

	public void showTextures(boolean showTextures) {
		for (ObjectPart objectPart : rootObjectParts) {
			if (objectPart instanceof MeshObjectPart) {
				((MeshObjectPart) objectPart).showTexture(showTextures);
			}
		}
		this.showTextures = showTextures;
	}

	public boolean getShowTextures() {
		return this.showTextures;
	}

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
	
	public void showLight(boolean showLight) {
		for (ObjectPart light : getAllObjectParts(LightObjectPart.class)) {
			((LightObjectPart) light).showLight(showLight);
		}
	}

}
