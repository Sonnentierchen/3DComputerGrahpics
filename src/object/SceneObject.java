package object;

import java.util.LinkedList;
import java.util.List;

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
	
	public void showTextures() {
		for (ObjectPart objectPart : rootObjectParts) {
			if (objectPart instanceof MeshObjectPart) {
				((MeshObjectPart) objectPart).showTexture();
			}
		}
		this.showTextures = true;
	}
	
	public void hideTextures() {
		for (ObjectPart objectPart : rootObjectParts) {
			((MeshObjectPart) objectPart).hideTexture();
		}
		this.showTextures = false;
	}
	
	public boolean getShowTextures() {
		return this.showTextures;
	}
	
}
