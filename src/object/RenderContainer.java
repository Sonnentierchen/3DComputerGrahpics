package object;

import javax.media.opengl.GL2;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Interface RenderContainer provides methods for a class that contains a render.
 * 
 * @author zzb13fb
 *
 */
public interface RenderContainer {
	
	/**
	 * Renders the object or objects.
	 * 
	 * @param gl the opengl context
	 */
	public void render(GL2 gl);
	
	/**
	 * Sets the {@link RenderContainer.RenderingMode} of this render container.
	 * 
	 * @param mode the rendering mode to be set
	 */
	public void setRenderingMode(RenderingMode mode);
	
	/**
	 * Enum RenderingMode is an enum for the different rendering modes.
	 * 
	 * @author zzb13fb
	 *
	 */
	public enum RenderingMode {
		
		/**
		 * The immediate rendering mode.
		 */
		IMMEDIATE,
		
		/**
		 * The display list rendering mode.
		 */
		DISPLAY_LIST;
		
	}

}
