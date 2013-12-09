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
import object.RenderContainer.RenderingMode;
import object.modification.Modification;
import object.modification.TranslateModification;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class Brick is a 3D brick.
 * @author zzb13fb
 *
 */
public class Brick implements TexturedObject, RenderContainer {

	/**
	 * The render of the brick.
	 */
	private StaticRender render;

	/**
	 * Constructor of class Brick.
	 * 
	 * @param texture the texture of the brick.
	 */
	public Brick(Texture texture) {
		Mesh mesh = ProceduralMeshFactory.createHardCube(0.3, 0.2, 0.5);
		Modification translate = new TranslateModification(0, 0.1, 0);
		ObjectPart part = new MeshObjectPart(mesh, texture,
				new Modification[] { translate }, new Modification[0]);
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
