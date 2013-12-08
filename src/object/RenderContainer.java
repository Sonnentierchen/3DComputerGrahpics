package object;

import javax.media.opengl.GL2;

public interface RenderContainer {
	
	public void render(GL2 gl);
	
	public void setRenderingMode(RenderingMode mode);
	
	public enum RenderingMode {
		
		IMMEDIATE,
		
		DISPLAY_LIST;
		
	}

}
