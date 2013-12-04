package object;

import java.util.Iterator;

import javax.media.opengl.GL2;

import assignment.Render;
import object.SceneObjectRender;
import object.modification.Modification;

public class StaticRender extends SceneObjectRender {

	public StaticRender(SceneObject sceneObject) {
		super(sceneObject);
	}
	
	@Override
	public void render(GL2 gl) {
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
					currentRender.renderImmediateMode(gl, showTextures);
				} else {
					LightObjectPart light = (LightObjectPart) currentPart;
					if (light.switchedOn()) {
						light.getLight().use(gl, glut, false);
					}
				}
			gl.glPopMatrix();
		
			for (ObjectPart subPart : currentPart.getSubParts()) {
				renderWithSubParts(gl, subPart, renderIterator);
			}
		gl.glPopMatrix();
	}

}
