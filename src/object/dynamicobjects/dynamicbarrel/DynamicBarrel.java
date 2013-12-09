package object.dynamicobjects.dynamicbarrel;

import javax.media.opengl.GL2;

import assignment.Mesh;
import assignment.ProceduralMeshFactory;
import assignment.Vertex;

import com.jogamp.opengl.util.texture.Texture;

import object.DynamicRender;
import object.MeshObjectPart;
import object.ObjectPart;
import object.RenderContainer;
import object.SceneObject;
import object.TexturedObject;
import object.animation.Animator;
import object.animation.AnimatedObject;
import object.modification.Modification;
import object.modification.ScaleModification;
import object.modification.TranslateModification;


/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class DynamicBarrel is a barrel contains a {@link RollingBarrelAnimator} and therefore rolls around.
 * 
 * @author zzb13fb
 *
 */
public class DynamicBarrel implements TexturedObject, AnimatedObject, RenderContainer {
	
	/* The render of this barrel */
	private DynamicRender render;

	/**
	 * Constructor of class DynamicBarrel.
	 * 
	 * @param texture the texture of this barrel
	 * @param slices the number of slices along the cylinder of this barrel
	 * @param stacks the number of stacks along the cylinder of this barrel
	 */
	public DynamicBarrel(Texture texture, int slices, int stacks) {
		Mesh mesh = ProceduralMeshFactory.createCylinder(slices, stacks, true);
		
		/* The modifications of this barrel to make it look like a barrel. */
		Modification scale = new ScaleModification(1, 1, 1.5);
		Modification translate = new TranslateModification(0, 0.5, 0);
		
		/* Only one object part -> the cylinder of the barrel */
		ObjectPart part = new MeshObjectPart(mesh, texture, new Modification[] {
				translate, scale }, new Modification[0]);
		SceneObject sceneObject = new SceneObject(new ObjectPart[] {part});
		Animator barrelAnimator = new RollingBarrelAnimator();
		render = new DynamicRender(sceneObject, barrelAnimator);
	}
	
	@Override
	public void setTextures(Texture[] textures) {
		this.render.getSceneObject().setTextures(textures);
	}
	
	@Override
	public void showTextures(boolean showTextures) {
		this.render.showTextures(showTextures);
	}
	
	@Override
	public void render(GL2 gl) {
		this.render.render(gl);
	}

	@Override
	public void setRenderingMode(RenderingMode mode) {
		this.render.setRenderingMode(mode);
	}

	@Override
	public void startAnimation() {
		this.render.startAnimation();
	}

	@Override
	public void pauseAnimation() {
		this.render.pauseAnimation();
	}

	@Override
	public void stopAnimation() {
		this.render.stopAnimation();
	}

	@Override
	public void setAnimationSpeed(double speed) {
		this.render.setAnimationSpeed(speed);
	}

	@Override
	public void setAnimator(Animator animator) {
		this.render.setAnimator(animator);
	}

}
