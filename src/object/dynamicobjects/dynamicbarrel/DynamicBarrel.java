package object.dynamicobjects.dynamicbarrel;

import javax.media.opengl.GL2;

import assignment.Mesh;
import assignment.ProceduralMeshFactory;
import assignment.Vertex;

import com.jogamp.opengl.util.texture.Texture;

import object.DynamicRender;
import object.MeshObjectPart;
import object.ObjectPart;
import object.SceneObject;
import object.TexturedObject;
import object.animation.Animator;
import object.dynamicobjects.AnimatedObject;
import object.modification.Modification;
import object.modification.ScaleModification;
import object.modification.TranslateModification;

public class DynamicBarrel implements TexturedObject, AnimatedObject {
	
	private DynamicRender render;

	public DynamicBarrel(Texture texture, int slices, int stacks) {
		Mesh mesh = ProceduralMeshFactory.createCylinder(slices, stacks, true);
		Modification scale = new ScaleModification(1, 1, 1.5);
		Modification translate = new TranslateModification(0, 0.5, 0);
		ObjectPart part = new MeshObjectPart(mesh, texture, new Modification[] {
				translate, scale }, new Modification[0]);
		SceneObject sceneObject = new SceneObject(new ObjectPart[] {part});
		Animator barrelAnimator = new RollingBarrelAnimator();
		render = new DynamicRender(sceneObject, barrelAnimator);
	}
	
	public void setTextures(Texture[] textures) {
		this.render.getSceneObject().setTextures(textures);
	}
	
	public void showTextures(boolean showTextures) {
		this.render.showTextures(showTextures);
	}
	
	public void render(GL2 gl) {
		this.render.render(gl);
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
