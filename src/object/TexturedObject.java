package object;

import com.jogamp.opengl.util.texture.Texture;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Interface TexturedObject provides methods for a textured 3D object.
 * 
 * @author zzb13fb
 * 
 */
public interface TexturedObject {

	/**
	 * Sets the textures of this object.
	 * 
	 * @param textures
	 *            the textures to be set
	 */
	public void setTextures(Texture[] textures);

	/**
	 * Indicates whether the textures of this object are to be shown.
	 * 
	 * @param showTextures
	 *            whether the textures of this object are to be shown
	 */
	public void showTextures(boolean showTextures);

}
