package object;

import object.modification.Modification;
import assignment.Light;
import assignment.Material;

public class LightObjectPart extends ObjectPart {
	
	private boolean switchedOn = true;
	
	private Light light;
	
	public LightObjectPart(Light light, Modification[] modifications,
			Modification[] globalModifications) {
		super(modifications, globalModifications);
		this.light = light;
	}

	public LightObjectPart(Light light, Material material, Modification[] modifications,
			Modification[] globalModifications) {
		super(material, modifications, globalModifications);
		this.light = light;
	}
	
	public Light getLight() {
		return this.light;
	}
	
	public boolean switchedOn() {
		return this.switchedOn;
	}
	
	public void switchOn() {
		this.switchedOn = true;
	}
	
	public void switchOff() {
		this.switchedOn = false;
	}

}
