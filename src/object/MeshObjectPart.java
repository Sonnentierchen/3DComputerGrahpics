package object;

import object.modification.Modification;
import assignment.Material;
import assignment.Mesh;

import com.jogamp.opengl.util.texture.Texture;

public class MeshObjectPart extends ObjectPart {
	
	private boolean showTexture = true;

	private Mesh mesh;

	private Texture texture;

	public MeshObjectPart(Mesh mesh, Texture texture, Modification[] modifications,
			Modification[] globalModifications) {
		super(modifications, globalModifications);
		this.mesh = mesh;
		this.texture = texture;
	}

	public MeshObjectPart(Mesh mesh, Texture texture, Material material,
			Modification[] modifications, Modification[] globalModifications) {
		super(material, modifications, globalModifications);
		this.mesh = mesh;
		this.texture = texture;
	}

	/**
	 * @return the mesh
	 */
	public Mesh getMesh() {
		return mesh;
	}

	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}
	
	public void showTexture() {
		this.showTexture = true;
	}
	
	public void hideTexture() {
		this.showTexture = true;
	}
	
	public boolean getShowTexture() {
		return this.showTexture;
	}

}
