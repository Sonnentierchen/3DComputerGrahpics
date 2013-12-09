package object;

import object.modification.Modification;
import assignment.Light;
import assignment.Material;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class LightObjectPart represents an {@link ObjectPart} that is a light.
 * 
 * @author zzb13fb
 *
 */
public class LightObjectPart extends ObjectPart {
	
	/**
	 * Indicates whether the light is switched on or off.
	 */
	private boolean switchedOn = true;
	
	/**
	 * The light of this light object part.
	 */
	private Light light;
	
	/**
	 * Constructor of class LightObjectPart.
	 * 
	 * @param light the light of this object part
	 * @param modifications the modifications of this object part
	 * @param globalModifications the global modifications of this object part
	 */
	public LightObjectPart(Light light, Modification[] modifications,
			Modification[] globalModifications) {
		super(modifications, globalModifications);
		this.light = light;
	}

	/**
	 * Constructor of class LightObjectPart.
	 * 
	 * @param light the light of this object part
	 * @param material the material of this light
	 * @param modifications the modifications of this object part
	 * @param globalModifications the global modifications of this object part
	 */
	public LightObjectPart(Light light, Material material, Modification[] modifications,
			Modification[] globalModifications) {
		super(material, modifications, globalModifications);
		this.light = light;
	}
	
	/**
	 * Returns the light of this light object part.
	 * @return the light of this object part
	 */
	public Light getLight() {
		return this.light;
	}
	
	/**
	 * Returns whether this light is switched on
	 * @return whether the light is switched on
	 */
	public boolean switchedOn() {
		return this.switchedOn;
	}
	
	/**
	 * Sets if the light is to be shown.
	 * @param showLight show or hide the light
	 */
	public void showLight(boolean showLight) {
		this.light.setSwitchedOn(showLight);
		this.switchedOn = showLight;
	}
	
	@Override
	public void setMaterial(Material material) {
		this.light.setMaterial(material);
	}

}
