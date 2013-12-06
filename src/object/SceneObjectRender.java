package object;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import assignment.Material;
import assignment.Render;

public abstract class SceneObjectRender {

	protected boolean showTextures = true;

	protected SceneObject sceneObject;

	protected List<Render> renders;

	protected GLUT glut = new GLUT();

	public SceneObjectRender(SceneObject sceneObject) {
		if (sceneObject == null) {
			throw new IllegalArgumentException("sceneObject must not be null.");
		}
		this.sceneObject = sceneObject;
		renders = new LinkedList<Render>();
		initializeRenders();
	}

	private void initializeRenders() {
		for (ObjectPart objectPart : sceneObject.getRootObjectParts()) {
			initializeWithSubPartsRenders(objectPart);
		}
	}

	private void initializeWithSubPartsRenders(ObjectPart currentPart) {
		if (currentPart instanceof MeshObjectPart) {
			Render currentRender = new Render(
					((MeshObjectPart) currentPart).getMesh(),
					((MeshObjectPart) currentPart).getTexture());
			renders.add(currentRender);
		}
		for (ObjectPart subPart : currentPart.getSubParts()) {
			initializeWithSubPartsRenders(subPart);
		}
	}

	public abstract void render(GL2 gl);

	protected void doMaterial(GL2 gl, Material material) {
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE,
				material.getDiffuse(), 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, material.getSpecular(),
				0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS,
				new float[] { material.getShininess() }, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, material.getEmission(),
				0);
	}

	public void showTextures() {
		this.sceneObject.showTextures();
		this.showTextures = true;
	}

	public void hideTextures() {
		this.sceneObject.hideTextures();
		this.showTextures = false;
	}

	public boolean getShowTextures() {
		return this.showTextures;
	}
	
	public SceneObject getSceneObject() {
		return this.sceneObject;
	}

}
