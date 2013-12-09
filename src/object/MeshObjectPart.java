package object;

import object.modification.Modification;
import assignment.Material;
import assignment.Mesh;

import com.jogamp.opengl.util.texture.Texture;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class MeshObjectPart is an {@link ObjectPart} that contains a mesh.
 * 
 * @author zzb13fb
 *
 */
public class MeshObjectPart extends ObjectPart {
	
	/**
	 * Indicates whether the texture is to be shown.
	 */
	private boolean showTexture = true;

	/**
	 * The mesh of this object.
	 */
	private Mesh mesh;

	/**
	 * The texture of this object.
	 */
	private Texture texture;

	/**
	 * Constructor of class MeshObjectPart. 
	 * 
	 * @param mesh the mesh of this object part
	 * @param texture the texture of this object part
	 * @param modifications the modifications of this object part
	 * @param globalModifications the global modifications of this object part
	 */
	public MeshObjectPart(Mesh mesh, Texture texture, Modification[] modifications,
			Modification[] globalModifications) {
		super(modifications, globalModifications);
		this.mesh = mesh;
		this.texture = texture;
	}

	/**
	 * Constructor of class MeshObjectPart. 
	 * 
	 * @param mesh the mesh of this object part
	 * @param texture the texture of this object part
	 * @param material the material of this object part
	 * @param modifications the modifications of this object part
	 * @param globalModifications the global modifications of this object part
	 */
	public MeshObjectPart(Mesh mesh, Texture texture, Material material,
			Modification[] modifications, Modification[] globalModifications) {
		super(material, modifications, globalModifications);
		this.mesh = mesh;
		this.texture = texture;
	}

	/**
	 * Returns the mesh of this object
	 * @return the mesh
	 */
	public Mesh getMesh() {
		return mesh;
	}

	/**
	 * Returns the texture of this object.
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}
	
	/**
	 * Sets whether the texture is to be shown.
	 * @param showTexture show or hide the texture
	 */
	public void showTexture(boolean showTexture) {
		this.showTexture = showTexture;
	}
	
	/**
	 * Returns if the textures are to be shown.
	 * @return if textures are shown or hidden
	 */
	public boolean getShowTexture() {
		return this.showTexture;
	}
	
	/**
	 * Sets the texture of this object part.
	 * 
	 * @param texture the texture to set
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
