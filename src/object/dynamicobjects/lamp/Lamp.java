package object.dynamicobjects.lamp;

import javax.media.opengl.GL2;

import assignment.Light;
import assignment.Material;
import assignment.Mesh;
import assignment.ProceduralMeshFactory;
import assignment.Vertex;

import com.jogamp.opengl.util.texture.Texture;

import object.animation.AnimatedObject;
import object.DynamicRender;
import object.GlutObjectPart;
import object.LightObjectPart;
import object.MeshObjectPart;
import object.ObjectPart;
import object.RenderContainer;
import object.SceneObject;
import object.TexturedObject;
import object.RenderContainer.RenderingMode;
import object.animation.Animator;
import object.modification.Modification;
import object.modification.RotateModification;
import object.modification.ScaleModification;
import object.modification.TranslateModification;

public class Lamp implements AnimatedObject, TexturedObject, RenderContainer {

	/**
	 * The material for the light when switched on.
	 */
	private static Material switchedOnMaterial = new Material();

	/**
	 * The material for the bulb when switched on.
	 */
	private static Material switchedOnBulbMaterial = new Material();

	/**
	 * The material for the light and bulb when switched off.
	 */
	private static final Material switchedOffMaterial = new Material();

	/**
	 * The render of this lamp.
	 */
	private DynamicRender render;

	/**
	 * Constructor of class bulb.
	 * 
	 * @param index the index of the light
	 * @param textures the textures of the lamp
	 * @param slices the number of slices of the arms, joints and the cone
	 * @param stacks the number of stacks of the arms, joints and the cone
	 */
	public Lamp(int index, Texture[] textures, int slices, int stacks) {
		if (textures.length != 6) {
			throw new IllegalArgumentException("Textures count not equal 6");
		}

		float[] matAmbient = { 0.1f, 0.1f, 0.1f, 1.0f };
		float[] matDiffuse = { 1.0f, 1.0f, 0.5f, 1.0f };
		float[] matSpecular = { 0.0f, 0.0f, 0.0f, 0.0f };
		float[] matShininess = { 0.2f };
		float[] matEmission = { 1.0f, 1.0f, 0.6f, 1.0f };
		switchedOnMaterial.setAmbient(matAmbient);
		switchedOnMaterial.setDiffuse(matDiffuse);
		switchedOnMaterial.setSpecular(matSpecular);
		switchedOnMaterial.setShininess(matShininess[0]);
		switchedOnMaterial.setEmission(matEmission);

		switchedOnBulbMaterial = (Material) switchedOnMaterial.clone();
		switchedOnBulbMaterial
				.setDiffuse(new float[] { 1.0f, 1.0f, 1.3f, 1.0f });

		// base
		Mesh baseMesh = ProceduralMeshFactory.createCylinder(slices, stacks,
				true);
		Modification baseRotate = new RotateModification(-90, 1.0, 0, 0);
		Modification baseScale = new ScaleModification(0.7, 0.7, 0.15);
		ObjectPart base = new MeshObjectPart(baseMesh, textures[0],
				new Modification[] { baseRotate, baseScale },
				new Modification[0]);

		// lower joint
		Mesh lowerJointMesh = ProceduralMeshFactory.createCylinder(slices,
				stacks, true);
		Modification lowerJointTranslate = new TranslateModification(0, 0,
				-0.15);
		Modification lowerJointScale = new ScaleModification(0.26, 0.26, 0.3);
		Modification lowerJointGlobalRotate = new RotateModification(-10, 0, 0,
				1.0);
		Modification lowerJointGlobalTranslate = new TranslateModification(0,
				0.15, 0);
		ObjectPart lowerJoint = new MeshObjectPart(lowerJointMesh, textures[1],
				new Modification[] { lowerJointTranslate, lowerJointScale },
				new Modification[] { lowerJointGlobalRotate,
						lowerJointGlobalTranslate });

		// lower arm
		Mesh lowerArmMesh = ProceduralMeshFactory.createCylinder(slices,
				stacks, true);
		Modification lowerArmRotate = new RotateModification(-90, 1.0, 0, 0);
		Modification lowerArmScale = new ScaleModification(0.1, 0.1, 1.3);
		ObjectPart lowerArm = new MeshObjectPart(lowerArmMesh, textures[2],
				new Modification[] { lowerArmRotate, lowerArmScale },
				new Modification[0]);

		// upper joint
		Mesh upperJointMesh = ProceduralMeshFactory.createCylinder(slices,
				stacks, true);
		Modification upperJointTranslate = new TranslateModification(0, 0,
				-0.075);
		Modification upperJointScale = new ScaleModification(0.18, 0.18, 0.15);
		Modification upperJointGlobalTranslate = new TranslateModification(0,
				1.3, 0);
		Modification upperJointGlobalRotate = new RotateModification(-10, 0, 0,
				1.0);
		ObjectPart upperJoint = new MeshObjectPart(upperJointMesh, textures[3],
				new Modification[] { upperJointTranslate, upperJointScale },
				new Modification[] { upperJointGlobalTranslate,
						upperJointGlobalRotate });

		// upper arm
		Mesh upperArmMesh = ProceduralMeshFactory.createCylinder(slices,
				stacks, true);
		Modification upperArmRotate = new RotateModification(-90, 1.0, 0, 0);
		Modification upperArmScale = new ScaleModification(0.1, 0.1, 1.3);
		Modification upperArmGlobalRotate = new RotateModification(40, 0, 0,
				1.0);
		ObjectPart upperArm = new MeshObjectPart(upperArmMesh, textures[4],
				new Modification[] { upperArmRotate, upperArmScale },
				new Modification[] { upperArmGlobalRotate });

		// head
		Mesh headMesh = ProceduralMeshFactory.createCone(slices, true);
		Modification headRotate = new RotateModification(90, 1.0, 0, 0);
		Modification headScale = new ScaleModification(0.5, 0.5, 0.5);
		Modification headGlobalTranslate = new TranslateModification(0.1, 1.28,
				0);
		Modification headGlobalRotate = new RotateModification(-85, 0, 0, 1.0);
		ObjectPart head = new MeshObjectPart(headMesh, textures[5],
				new Modification[] { headRotate, headScale },
				new Modification[] { headGlobalTranslate, headGlobalRotate });

		// head light
		Light light = new Light(index, new float[] { 0.25f, 0f, -0.15f, 1f },
				switchedOnMaterial.getAmbient(),
				switchedOnMaterial.getDiffuse(),
				switchedOnMaterial.getSpecular(), true);
		light.makeSpotlight(new float[] { 2.5f, 0.0f, -0.5f, 1f }, 40f);
		Modification lightTranslateOne = new TranslateModification(0, -0.5, 0);
		Modification lightRotateOne = new RotateModification(-90, 0, 0, 1.0);
		Modification lightRotateTwo = new RotateModification(-90, 1.0, 0, 0);
		Modification lightTranslateTwo = new TranslateModification(-0.1, 0, 0);
		Modification lightPrivateRotateThree = new RotateModification(-25, 0,
				1.0, 0);
		ObjectPart lightPart = new LightObjectPart(light,
				new Modification[] { lightPrivateRotateThree },
				new Modification[] { lightTranslateOne, lightRotateOne,
						lightRotateTwo, lightTranslateTwo });

		// bulb
		Modification bulbScaleOne = new ScaleModification(1.1, 1.1, 1.1);
		Modification bulbScaleTwo = new ScaleModification(0.5, 1.0, 1.0);
		Modification bulbScaleThree = new ScaleModification(0.18, 0.18, 0.18);
		GlutObjectPart bulb = new GlutObjectPart(switchedOnMaterial,
				new Modification[] { bulbScaleOne, bulbScaleTwo, bulbScaleThree },
				new Modification[0]);

		// stack together and create tree structured object
		lightPart.addSubPart(bulb);
		head.addSubPart(lightPart);
		upperArm.addSubPart(head);
		upperJoint.addSubPart(upperArm);
		lowerArm.addSubPart(upperJoint);
		lowerJoint.addSubPart(lowerArm);
		base.addSubPart(lowerJoint);
		SceneObject sceneObject = new SceneObject(new ObjectPart[] { base });
		Animator jumpAnimtor = new JumpLampAnimator();
		render = new DynamicRender(sceneObject, jumpAnimtor);
	}

	/**
	 * Shows or hides the light of this lamp.
	 * 
	 * @param showLight shows or hides the light of this lamp
	 */
	public void showLight(boolean showLight) {
		SceneObject sceneObject = render.getSceneObject();
		sceneObject.showLight(showLight);
		Material[] materials = new Material[8];
		for (int i = 0; i < 8; i++) {
			materials[i] = new Material();
		}
		/*
		 * Sets the material of the bulb and light accordingly to the state of the light
		 */
		materials[6] = (showLight ? (Material) switchedOnMaterial.clone()
				: (Material) switchedOffMaterial.clone());
		materials[7] = (showLight ? (Material) switchedOnBulbMaterial.clone()
				: (Material) switchedOffMaterial.clone());
		sceneObject.setMaterials(materials);
	}

	@Override
	public void render(GL2 gl) {
		render.render(gl);
	}

	@Override
	public void setRenderingMode(RenderingMode mode) {
		this.render.setRenderingMode(mode);
	}

	@Override
	public void startAnimation() {
		this.render.startAnimation();
	}

	@Override
	public void pauseAnimation() {
		this.render.pauseAnimation();
	}

	@Override
	public void stopAnimation() {
		this.render.stopAnimation();
	}

	@Override
	public void setTextures(Texture[] textures) {
		this.render.getSceneObject().setTextures(textures);
	}

	@Override
	public void showTextures(boolean showTextures) {
		this.render.showTextures(showTextures);
	}

	@Override
	public void setAnimationSpeed(double speed) {
		this.render.setAnimationSpeed(speed);
	}

	@Override
	public void setAnimator(Animator animator) {
		this.render.setAnimator(animator);
	}

}
