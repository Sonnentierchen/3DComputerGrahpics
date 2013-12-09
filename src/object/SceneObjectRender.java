package object;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import object.RenderContainer.RenderingMode;

import com.jogamp.opengl.util.gl2.GLUT;

import assignment.Material;
import assignment.Render;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class SceneObjectRender contains a {@link SceneObject} and all to the scene object corresponding renders.
 * @author zzb13fb
 *
 */
public abstract class SceneObjectRender {

	/**
	 * Indicates whether the textures are to be shown.
	 */
	protected boolean showTextures = true;
	
	/**
	 * Indicates whether there has been a change to the object and the display lists need to be re-initialised.
	 */
	protected boolean needToInitializeDisplayList = false;
	
	/**
	 * The {@link RenderingMode} of this render.
	 */
	protected RenderingMode mode = RenderingMode.IMMEDIATE;

	/**
	 * The {@link SceneObject} that is to be rendered.
	 */
	protected SceneObject sceneObject;

	/**
	 * The List of renders of the {@link ObjectPart}s of the {@link SceneObject}.
	 */
	protected List<Render> renders;

	/**
	 * The GLUT context.
	 */
	protected GLUT glut = new GLUT();

	/**
	 * Constructor of class SceneObjectRender.
	 * @param sceneObject the scene object to render
	 */
	public SceneObjectRender(SceneObject sceneObject) {
		if (sceneObject == null) {
			throw new IllegalArgumentException("sceneObject must not be null.");
		}
		this.sceneObject = sceneObject;
		renders = new LinkedList<Render>();
		initializeRenders();
	}

	/**
	 * Recursively retrieves all object-parts and initialises the respective renders.
	 */
	private void initializeRenders() {
		for (ObjectPart objectPart : sceneObject.getRootObjectParts()) {
			initializeWithSubPartsRenders(objectPart);
		}
	}

	/**
	 * Initialises the render for the given part and recursively does the same for all its sub-parts.
	 */
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
	
	/**
	 * Initialises the display lists of all renders of all object parts.
	 */
	protected void initializeDisplayList(GL2 gl) {
		for (Render render : renders) {
			render.initialiseDisplayList(gl, showTextures);
		}
	}

	/**
	 * Renders the stored {@link SceneObject} in respect to the stored {@link RenderingMode}.
	 * 
	 * @param gl the rendering context
	 */
	public abstract void render(GL2 gl);
	
	/**
	 * Sets the {@link RenderingMode} of this render.
	 * @param mode the mode to set
	 */
	public abstract void setRenderingMode(RenderingMode mode);

	/**
	 * Tells the rendering context to use the given material.
	 */
	protected void doMaterial(GL2 gl, Material material) {
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,
				material.getAmbient(), 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE,
				material.getDiffuse(), 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, material.getSpecular(),
				0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS,
				new float[] { material.getShininess() }, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, material.getEmission(),
				0);
	}

	/**
	 * Sets whether the textures are to be shown.
	 * @param showTextures whether the textures are to be shown
	 */
	public void showTextures(boolean showTextures) {
		this.sceneObject.showTextures(showTextures);
		this.showTextures = showTextures;
		this.needToInitializeDisplayList = true;
	}

	/**
	 * Returns whether textures are currently shown.
	 * @return whether textures are currently shown
	 */
	public boolean getShowTextures() {
		return this.showTextures;
	}
	
	/**
	 * Returns the {@link SceneObject} of this render.
	 * @return the scene object of this render
	 */
	public SceneObject getSceneObject() {
		return this.sceneObject;
	}

}
