package object;

import java.util.Iterator;

import javax.media.opengl.GL2;

import assignment.Render;
import object.RenderContainer.RenderingMode;
import object.SceneObjectRender;
import object.modification.Modification;

public class StaticRender extends SceneObjectRender {

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
	
	private void renderWithSubParts(GL2 gl, ObjectPart currentPart, Iterator<Render> renderIterator) {
		gl.glPushMatrix();
			for (Modification globalModification : currentPart
					.getGlobalModifications()) {
				globalModification.doModification(gl);
			}
			gl.glPushMatrix();
				for (Modification modification : currentPart.getModifications()) {
					modification.doModification(gl);
				}
				this.doMaterial(gl, currentPart.getMaterial());
				if (currentPart instanceof MeshObjectPart) {
					Render currentRender = renderIterator.next();
					switch (mode) {
					case IMMEDIATE: 
						currentRender.renderImmediateMode(gl, showTextures);
						break;
					case DISPLAY_LIST:
						currentRender.renderDisplayList(gl);
						break;
					}
				} else if (currentPart instanceof LightObjectPart) {
					LightObjectPart light = (LightObjectPart) currentPart;
					light.getLight().use(gl, glut, false);
				} else if (currentPart instanceof GlutObjectPart){
					((GlutObjectPart) currentPart).doGlutObject(glut);
				}
			gl.glPopMatrix();
		
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
