package object.staticobjects;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

import assignment.Mesh;
import assignment.ProceduralMeshFactory;

import com.jogamp.opengl.util.texture.Texture;

import object.MeshObjectPart;
import object.ObjectPart;
import object.SceneObject;
import object.StaticRender;
import object.modification.Modification;
import object.modification.TranslateModification;

public class Brick {

	private StaticRender render;

	public Brick(Texture texture) {
		Mesh mesh = ProceduralMeshFactory.createHardCube(0.3, 0.2, 0.5);
		Modification translate = new TranslateModification(0, 0.1, 0);
		ObjectPart part = new MeshObjectPart(mesh, texture,
				new Modification[] { translate }, new Modification[0]);
		SceneObject sceneObject = new SceneObject(new ObjectPart[] {part});
		render = new StaticRender(sceneObject);
	}
	
	public void setTextures(Texture[] textures) {
		this.render.getSceneObject().setTextures(textures);
	}
	
	public void displayTextures(boolean displayTextures) {
		if (displayTextures) {
			this.render.getSceneObject().showTextures();
		} else {
			this.render.getSceneObject().hideTextures();
		}
	}
	
	public void render(GL2 gl) {
		this.render.render(gl);
	}

}
