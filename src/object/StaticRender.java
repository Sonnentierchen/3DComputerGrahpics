package object;

import java.util.Iterator;

import javax.media.opengl.GL2;

import assignment.Render;
import object.RenderContainer.RenderingMode;
import object.SceneObjectRender;
import object.modification.Modification;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class StaticRender is a {@link SceneObjectRender} that has no animation.
 * @author zzb13fb
 *
 */
public class StaticRender extends SceneObjectRender {

	/**
	 * Constructor of class StaticRender.
	 * 
	 * @param sceneObject the scene object to render
	 */
	public StaticRender(SceneObject sceneObject) {
		super(sceneObject);
	}
	
	@Override
	public void render(GL2 gl) {
		if (needToInitializeDisplayList) {
			initializeDisplayList(gl);
			needToInitializeDisplayList = false;
		}
		Iterator<Render> renderIterator = renders.iterator();
		for (ObjectPart rootPart : this.sceneObject.getRootObjectParts()) {
			renderWithSubParts(gl, rootPart, renderIterator);
		}
	}
	
	/**
	 * Renders all the parts of the scene object by recursively calling the
	 * method.
	 * 
	 * @param gl
	 *            the rendering context
	 * @param currentPart
	 *            the current part so that modifications of this part can be
	 *            retrieved
	 * @param renderIterator
	 */
	private void renderWithSubParts(GL2 gl, ObjectPart currentPart, Iterator<Render> renderIterator) {
		gl.glPushMatrix();
			/*
			 * Do all modifications that are applicable not only for the current
			 * part but also for all sub-parts
			 */
			for (Modification globalModification : currentPart
					.getGlobalModifications()) {
				globalModification.doModification(gl);
			}
			gl.glPushMatrix();
				/* 
				 * Do all private modifications that are only applicable to the current 
				 * part, therefore push and pop the matrix before rendering the sub-parts
				 */
				for (Modification modification : currentPart.getModifications()) {
					modification.doModification(gl);
				}
				/*
				 * Modify the current used material to the material used by the current 
				 * part
				 */
				this.doMaterial(gl, currentPart.getMaterial());
				if (currentPart instanceof MeshObjectPart) {
					/*
					 * If the current part contains a mesh, render it.
					 */
					Render currentRender = renderIterator.next();
					/*
					 * Draw the current part depending on the set rendering mode.
					 */
					switch (mode) {
					case IMMEDIATE: 
						currentRender.renderImmediateMode(gl, showTextures);
						break;
					case DISPLAY_LIST:
						currentRender.renderDisplayList(gl);
						break;
					}
				} else if (currentPart instanceof LightObjectPart) {
					/* 
					 * If the current part is a light, show the light. 
					 * */
					LightObjectPart light = (LightObjectPart) currentPart;
					light.getLight().use(gl, glut, false);
				} else if (currentPart instanceof GlutObjectPart){
					/*
					 * If the current part is a glut object, render it.
					 */
					((GlutObjectPart) currentPart).doGlutObject(glut);
				}
			gl.glPopMatrix();
		
			/*
			 * Repeat these steps for all sub-parts of the current part.
			 * This way a tree-structure is established.
			 */
			for (ObjectPart subPart : currentPart.getSubParts()) {
				renderWithSubParts(gl, subPart, renderIterator);
			}
		gl.glPopMatrix();
	}

	@Override
	public void setRenderingMode(RenderingMode mode) {
		if (this.mode != mode && mode == RenderingMode.DISPLAY_LIST) {
			needToInitializeDisplayList = true;
		}
		this.mode = mode;
	}

}
