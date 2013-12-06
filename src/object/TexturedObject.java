package object;

import com.jogamp.opengl.util.texture.Texture;

public interface TexturedObject {

	public void setTextures(Texture[] textures);
	
	public void showTextures(boolean showTextures);

}
