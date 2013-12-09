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

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class Barrel is a 3D barrel made of a cylinder.
 * @author zzb13fb
 *
 */
public class Barrel implements TexturedObject, RenderContainer {

	/**
	 * The render of this barrel.
	 */
	private StaticRender render;

	/**
	 * Constructor of class Barrel.
	 * @param texture the texture of the barrel
	 * @param slices the number of slices of the cylinder of this barrel
	 * @param stacks the number of stacks of the cylinder of this barrel
	 */
	public Barrel(Texture texture, int slices, int stacks) {
		Mesh mesh = ProceduralMeshFactory.createCylinder(slices, stacks, true);
		Modification scale = new ScaleModification(1, 1, 1.5);
		Modification translate = new TranslateModification(0, 0.5, 0);
		ObjectPart part = new MeshObjectPart(mesh, texture, new Modification[] {
				translate, scale }, new Modification[0]);
		SceneObject sceneObject = new SceneObject(new ObjectPart[] {part});
		render = new StaticRender(sceneObject);
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

}
