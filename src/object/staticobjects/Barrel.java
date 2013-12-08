package object.staticobjects;

import javax.media.opengl.GL2;

import assignment.Mesh;
import assignment.ProceduralMeshFactory;

import com.jogamp.opengl.util.texture.Texture;

import object.MeshObjectPart;
import object.ObjectPart;
import object.RenderContainer;
import object.SceneObject;
import object.StaticRender;
import object.TexturedObject;
import object.modification.Modification;
import object.modification.ScaleModification;
import object.modification.TranslateModification;

public class Barrel implements TexturedObject, RenderContainer {

	private StaticRender render;

	public Barrel(Texture texture, int slices, int stacks) {
		Mesh mesh = ProceduralMeshFactory.createCylinder(slices, stacks, true);
		Modification scale = new ScaleModification(1, 1, 1.5);
		Modification translate = new TranslateModification(0, 0.5, 0);
		ObjectPart part = new MeshObjectPart(mesh, texture, new Modification[] {
				translate, scale }, new Modification[0]);
		SceneObject sceneObject = new SceneObject(new ObjectPart[] {part});
		render = new StaticRender(sceneObject);
	}
	
	public void setTextures(Texture[] textures) {
		this.render.getSceneObject().setTextures(textures);
	}
	
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

}
