package object.staticobjects;

import javax.media.opengl.GL2;

import object.MeshObjectPart;
import object.ObjectPart;
import object.RenderContainer;
import object.SceneObject;
import object.StaticRender;
import object.TexturedObject;
import object.RenderContainer.RenderingMode;
import object.modification.Modification;
import object.modification.TranslateModification;
import object.modification.RotateModification;
import assignment.Mesh;
import assignment.ProceduralMeshFactory;

import com.jogamp.opengl.util.texture.Texture;

/** 
 * 
 * I declare that this code is my own work 
 * Author Florian Blume, fblume1@sheffield.ac.uk 
 * 
 */

/**
 * Class Room is a 3D room with walls a floor and a ceiling.
 * @author zzb13fb
 *
 */
public class Room implements TexturedObject, RenderContainer {

	/**
	 * The render of this room.
	 */
	private StaticRender render;

	/**
	 * Constructor of class room.
	 * 
	 * @param textures the textures of this room
	 * @param height the height of the walls
	 * @param width the width of the walls
	 * @param m the number tilings along x axis
	 * @param n the number of tiling along y axis
	 */
	public Room(Texture[] textures, double height, double width, int m, int n) {
		if (textures.length != 6) {
			throw new IllegalArgumentException(
					"Supply 6 textures for floor and walls and ceiling.");
		}

		/*
		 * The floor
		 */
		Mesh meshFloor = ProceduralMeshFactory.createPlane(width, width, m, n,
				1, 1);

		ObjectPart floor = new MeshObjectPart(meshFloor, textures[0],
				new Modification[0], new Modification[0]);
		
		/*
		 * Wall one
		 */
		Mesh meshWallOne = ProceduralMeshFactory.createPlane(width, height, m,
				n, 1, 1);

		Modification wallOneTranslate = new TranslateModification(-width / 2,
				height / 2, 0);
		Modification wallOneRotateOne = new RotateModification(90, 0, 1.0, 0);
		Modification wallOneRotateTwo = new RotateModification(90, 1.0, 0, 0);

		ObjectPart wallOne = new MeshObjectPart(meshWallOne, textures[1],
				new Modification[] { wallOneTranslate, wallOneRotateOne,
						wallOneRotateTwo }, new Modification[0]);

		/*
		 * Wall two
		 */
		Mesh meshWallTwo = ProceduralMeshFactory.createPlane(width, height, m,
				n, 1, 1);

		Modification wallTwoTranslate = new TranslateModification(width / 2,
				height / 2, 0);
		Modification wallTwoRotateOne = new RotateModification(90, 0, 1.0, 0);
		Modification wallTwoRotateTwo = new RotateModification(-90, 1.0, 0, 0);

		ObjectPart wallTwo = new MeshObjectPart(meshWallTwo, textures[2],
				new Modification[] { wallTwoTranslate, wallTwoRotateOne,
						wallTwoRotateTwo }, new Modification[0]);

		/*
		 * Wall three
		 */
		Mesh meshWallThree = ProceduralMeshFactory.createPlane(width, height,
				m, n, 1, 1);

		Modification wallThreeTranslate = new TranslateModification(0,
				height / 2, -width / 2);
		Modification wallThreeRotate = new RotateModification(90, 1.0, 0, 0);

		ObjectPart wallThree = new MeshObjectPart(meshWallThree, textures[3],
				new Modification[] { wallThreeTranslate, wallThreeRotate },
				new Modification[0]);

		/*
		 * Wall four
		 */
		Mesh meshWallFour = ProceduralMeshFactory.createPlane(width, height, m,
				n, 1, 1);

		Modification wallFourTranslate = new TranslateModification(0,
				height / 2, width / 2);
		Modification wallFourRotateOne = new RotateModification(180, 0, 0, 1.0);
		Modification wallFourRotateTwo = new RotateModification(-90, 1.0, 0, 0);

		ObjectPart wallFour = new MeshObjectPart(meshWallFour, textures[4],
				new Modification[] { wallFourTranslate, wallFourRotateOne,
						wallFourRotateTwo }, new Modification[0]);
		
		/*
		 * Ceiling
		 */
		Mesh meshCeiling = ProceduralMeshFactory.createPlane(width, width, m, n,
				1, 1);
		
		Modification ceilingRotation = new RotateModification(180, 1.0, 0, 0);
		Modification ceilingTranslation = new TranslateModification(0, height, 0);
		ObjectPart ceiling = new MeshObjectPart(meshFloor, textures[5],
				new Modification[] { ceilingTranslation, ceilingRotation }, new Modification[0]);
		

		SceneObject sceneObject = new SceneObject(new ObjectPart[] {floor, wallOne, wallTwo, wallThree, wallFour, ceiling});
		render = new StaticRender(sceneObject);
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
	public void render(GL2 gl) {
		render.render(gl);
	}

	@Override
	public void setRenderingMode(RenderingMode mode) {
		this.render.setRenderingMode(mode);
	}

}
