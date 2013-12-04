package staticobjects;

import javax.media.opengl.GL2;

import object.MeshObjectPart;
import object.ObjectPart;
import object.SceneObject;
import object.StaticRender;
import object.modification.Modification;
import object.modification.ScaleModification;
import object.modification.TranslateModification;
import object.modification.RotateModification;
import assignment.Mesh;
import assignment.ProceduralMeshFactory;

import com.jogamp.opengl.util.texture.Texture;

public class Room {

	private StaticRender render;

	public Room(Texture[] textures, double height, double width, int m, int n) {
		if (textures.length != 5) {
			throw new IllegalArgumentException(
					"Supply 5 textures for floor and walls.");
		}

		Mesh meshFloor = ProceduralMeshFactory.createPlane(width, width, m, n,
				1, 1);

		ObjectPart floor = new MeshObjectPart(meshFloor, textures[0],
				new Modification[0], new Modification[0]);

		Mesh meshWallOne = ProceduralMeshFactory.createPlane(width, height, m,
				n, 1, 1);

		Modification wallOneTranslate = new TranslateModification(-width / 2,
				height / 2, 0);
		Modification wallOneRotateOne = new RotateModification(90, 0, 1.0, 0);
		Modification wallOneRotateTwo = new RotateModification(90, 1.0, 0, 0);

		ObjectPart wallOne = new MeshObjectPart(meshWallOne, textures[1],
				new Modification[] { wallOneTranslate, wallOneRotateOne,
						wallOneRotateTwo }, new Modification[0]);

		Mesh meshWallTwo = ProceduralMeshFactory.createPlane(width, height, m,
				n, 1, 1);

		Modification wallTwoTranslate = new TranslateModification(width / 2,
				height / 2, 0);
		Modification wallTwoRotateOne = new RotateModification(90, 0, 1.0, 0);
		Modification wallTwoRotateTwo = new RotateModification(-90, 1.0, 0, 0);

		ObjectPart wallTwo = new MeshObjectPart(meshWallTwo, textures[2],
				new Modification[] { wallTwoTranslate, wallTwoRotateOne,
						wallTwoRotateTwo }, new Modification[0]);

		Mesh meshWallThree = ProceduralMeshFactory.createPlane(width, height,
				m, n, 1, 1);

		Modification wallThreeTranslate = new TranslateModification(0,
				height / 2, -width / 2);
		Modification wallThreeRotate = new RotateModification(90, 1.0, 0, 0);

		ObjectPart wallThree = new MeshObjectPart(meshWallThree, textures[3],
				new Modification[] { wallThreeTranslate, wallThreeRotate },
				new Modification[0]);

		Mesh meshWallFour = ProceduralMeshFactory.createPlane(width, height, m,
				n, 1, 1);

		Modification wallFourTranslate = new TranslateModification(0,
				height / 2, width / 2);
		Modification wallFourRotateOne = new RotateModification(180, 0, 0, 1.0);
		Modification wallFourRotateTwo = new RotateModification(-90, 1.0, 0, 0);

		ObjectPart wallFour = new MeshObjectPart(meshWallFour, textures[4],
				new Modification[] { wallFourTranslate, wallFourRotateOne,
						wallFourRotateTwo }, new Modification[0]);

		SceneObject sceneObject = new SceneObject(new ObjectPart[] {floor, wallOne, wallTwo, wallThree, wallFour});
		render = new StaticRender(sceneObject);
	}
	
	public void render(GL2 gl) {
		render.render(gl);
	}

}
