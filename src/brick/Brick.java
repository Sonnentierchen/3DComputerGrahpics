package brick;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import assignment.Mesh;
import assignment.ProceduralMeshFactory;
import assignment.Render;

public class Brick {
	
	private Mesh mesh;
	
	private Texture texture;
	
	private Render render;
	
	public Brick(Texture texture) {
		this.mesh = ProceduralMeshFactory.createHardCube(0.3, 0.2, 0.5);
		this.texture = texture;
		this.render = new Render(mesh, texture);
	}
	
	public void render(GL2 gl) {
		gl.glPushMatrix();
			gl.glTranslated(0.0, 0.1, 0.0);
			render.renderImmediateMode(gl, true);
		gl.glPopMatrix();
	}

}
