package object;

import java.util.Iterator;

import javax.media.opengl.GL2;

import object.RenderContainer.RenderingMode;
import object.animation.AnimationStep;
import object.animation.Animator;
import object.modification.Modification;
import assignment.Render;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class DynamicRender is a {@link SceneObjectRender} that contains a
 * {@link SceneObject} and modifies it using an dynamic animator.
 * 
 * @author zzb13fb
 * 
 */
public class DynamicRender extends SceneObjectRender {

	/**
	 * Indicates whether the animation is running.
	 */
	private boolean running = false;

	/**
	 * The animator to manipulate the stores sceneobject.
	 */
	private Animator animator;

	/**
	 * Constructor of class DynamicRender.
	 * 
	 * @param sceneObject
	 *            the sceneobject to render
	 * @param animator
	 *            the animator to manipulate the sceneobject
	 */
	public DynamicRender(SceneObject sceneObject, Animator animator) {
		super(sceneObject);
		this.animator = animator;
	}

	@Override
	public void render(GL2 gl) {
		/* Re-Initialise the display list in case that something has changed */
		if (needToInitializeDisplayList) {
			initializeDisplayList(gl);
			needToInitializeDisplayList = false;
		}
		/* Retrieve all renders to iterate over them */
		Iterator<Render> renderIterator = renders.iterator();
		/*
		 * Retrieve all next animation steps to manipulate the parts of the
		 * scene object
		 */
		Iterator<AnimationStep> animationStepIterator = animator
				.getAnimationStepIterator();
		/*
		 * Iterate over all root objects to render them, and recursively render
		 * their subparts
		 */
		for (ObjectPart rootPart : this.sceneObject.getRootObjectParts()) {
			renderWithSubParts(gl, rootPart, renderIterator,
					animationStepIterator);
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
	 * @param animationStepIterator
	 */
	private void renderWithSubParts(GL2 gl, ObjectPart currentPart,
			Iterator<Render> renderIterator,
			Iterator<AnimationStep> animationStepIterator) {
		gl.glPushMatrix();
			/*
			 * Do all modifications that are applicable not only for the current
			 * part but also for all sub-parts
			 */
			for (Modification globalModification : currentPart
					.getGlobalModifications()) {
				globalModification.doModification(gl);
			}
			/*
			 * In case that the animation is running retrieve the manipulation steps
			 * from the animation step iterator that was formerly retrieved from the
			 * animator
			 */
			if (running) {
				if (!animationStepIterator.hasNext()) {
					throw new IllegalArgumentException(
							"Animator should have provided enough animation steps.");
				}
				AnimationStep animationStep = animationStepIterator.next();
				/* Execute all stored modifications of the animation step */
				for (Modification modification : animationStep.getModifications()) {
					modification.doModification(gl);
				}
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
				} else if (currentPart instanceof GlutObjectPart) {
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
				renderWithSubParts(gl, subPart, renderIterator,
						animationStepIterator);
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

	/**
	 * Starts the animation.
	 */
	public void startAnimation() {
		this.running = true;
		this.animator.start();
	}

	/**
	 * Stops the animation.
	 */
	public void stopAnimation() {
		this.running = false;
		this.animator.reset();
	}

	/**
	 * Pauses the animation.
	 */
	public void pauseAnimation() {
		this.animator.pause();
	}

	/**
	 * Returns whether the animation is running.
	 * 
	 * @return whether the animation is running
	 */
	public boolean isRunning() {
		return this.running;
	}

	/**
	 * Sets the animator of this dynamic render.
	 * 
	 * @param animator
	 *            the animator to set
	 */
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}

	/**
	 * Returns the animator of this dynamic render.
	 * 
	 * @return the animator of this render
	 */
	public Animator getAnimator() {
		return this.animator;
	}

	/**
	 * Sets the animation speed of this render.
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setAnimationSpeed(double speed) {
		this.animator.setAnimationSpeed(speed);
	}

}
