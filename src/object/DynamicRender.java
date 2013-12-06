package object;

import java.util.Iterator;

import javax.media.opengl.GL2;

import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import assignment.Render;

public class DynamicRender extends SceneObjectRender {

	private boolean running = false;

	private Animator animator;
	
	public DynamicRender(SceneObject sceneObject, Animator animator) {
		super(sceneObject);
		this.animator = animator;
	}

	@Override
	public void render(GL2 gl) {
		Iterator<Render> renderIterator = renders.iterator();
		Iterator<AnimationStep> animationStepIterator = animator.getAnimationStepIterator();
		for (ObjectPart rootPart : this.sceneObject.getRootObjectParts()) {
			renderWithSubParts(gl, rootPart, renderIterator, animationStepIterator);
		}
	}

	private void renderWithSubParts(GL2 gl, ObjectPart currentPart,
			Iterator<Render> renderIterator,
			Iterator<AnimationStep> animationStepIterator) {
		gl.glPushMatrix();
			for (Modification globalModification : currentPart
					.getGlobalModifications()) {
				globalModification.doModification(gl);
			}
			if (running) {
				if (!animationStepIterator.hasNext()) {
					throw new IllegalArgumentException("Animator should have provided enough animation steps.");
				}
				AnimationStep animationStep = animationStepIterator.next();
				for (Modification modification : animationStep.getModifications()) {
					modification.doModification(gl);
				}
			}
			gl.glPushMatrix();
				for (Modification modification : currentPart.getModifications()) {
					modification.doModification(gl);
				}
				this.doMaterial(gl, currentPart.getMaterial());
				if (currentPart instanceof MeshObjectPart) {
					Render currentRender = renderIterator.next();
					currentRender.renderImmediateMode(gl, showTextures);
				} else if (currentPart instanceof LightObjectPart){
					LightObjectPart light = (LightObjectPart) currentPart;
					light.getLight().use(gl, glut, false);
				} else {
					glut.glutSolidSphere(0.20, 20, 20);
				}
			gl.glPopMatrix();

			for (ObjectPart subPart : currentPart.getSubParts()) {
				renderWithSubParts(gl, subPart, renderIterator, animationStepIterator);
			}
		gl.glPopMatrix();
	}

	public void startAnimation() {
		this.running = true;
		this.animator.start();
	}

	public void stopAnimation() {
		this.running = false;
		this.animator.reset();
	}

	public void pauseAnimation() {
		this.animator.pause();
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
	
	public Animator getAnimator() {
		return this.animator;
	}
	
	public void setAnimationSpeed(double speed) {
		this.animator.setAnimationSpeed(speed);
	}

}
