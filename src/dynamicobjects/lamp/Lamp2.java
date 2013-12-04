package dynamicobjects.lamp;

import javax.media.opengl.GL2;

import assignment.Light;
import assignment.Material;
import assignment.Mesh;
import assignment.ProceduralMeshFactory;

import com.jogamp.opengl.util.texture.Texture;

import object.DynamicRender;
import object.GlutSphereObjectPart;
import object.LightObjectPart;
import object.MeshObjectPart;
import object.ObjectPart;
import object.SceneObject;
import object.modification.Modification;
import object.modification.RotateModification;
import object.modification.ScaleModification;
import object.modification.TranslateModification;

public class Lamp2 {

	private DynamicRender render;

	public Lamp2(int index, Texture[] textures, int slices, int stacks) {
		if (textures.length != 6) {
			throw new IllegalArgumentException("Textures count not equal 6");
		}

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
		Modification lowerJointGlobalRotate = new RotateModification(-20, 0, 0,
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
		ObjectPart upperJoint = new MeshObjectPart(upperJointMesh, textures[3],
				new Modification[] { upperJointTranslate, upperJointScale },
				new Modification[] { upperJointGlobalTranslate });

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
		ObjectPart head = new MeshObjectPart(headMesh, textures[4],
				new Modification[] { headRotate, headScale },
				new Modification[] { headGlobalTranslate, headGlobalRotate });

		// head light
		Light light = new Light(index, new float[] { 0.25f, 0f, -0.15f, 1f });
		light.makeSpotlight(new float[] { 2.5f, 0.0f, -0.5f, 1f }, 50f);
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
		Material material = new Material();
		float[] matAmbientDiffuse = { 0.1f, 0.1f, 0.1f, 1.0f };
		float[] matSpecular = { 0.0f, 0.0f, 0.0f, 0.0f };
		float[] matShininess = { 1.0f };
		float[] matEmission = { 0.9f, 0.9f, 0.9f, 1.0f };
		material.setAmbient(matAmbientDiffuse);
		material.setSpecular(matSpecular);
		material.setShininess(matShininess[0]);
		material.setEmission(matEmission);
		GlutSphereObjectPart bulb = new GlutSphereObjectPart(material,
				new Modification[] { bulbScaleOne, bulbScaleTwo },
				new Modification[0]);

		// stack together
		lightPart.addSubPart(bulb);
		head.addSubPart(lightPart);
		upperArm.addSubPart(head);
		upperJoint.addSubPart(upperArm);
		lowerArm.addSubPart(upperJoint);
		lowerJoint.addSubPart(lowerArm);
		base.addSubPart(lowerJoint);
		SceneObject sceneObject = new SceneObject(new ObjectPart[] { base });
		render = new DynamicRender(sceneObject, new NullLampAnimator2());
	}

	public void render(GL2 gl) {
		render.render(gl);
	}

}
