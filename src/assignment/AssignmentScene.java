package assignment;

/*
 Author: Steve Maddock
 Last updated: 12 November 2013
 */

import java.io.File;
import java.awt.image.*;

import javax.imageio.*;

import staticobjects.Barrel;
import staticobjects.Brick;
import staticobjects.Room;

import com.jogamp.opengl.util.awt.*;

import javax.media.opengl.*;

import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;

import javax.media.opengl.glu.GLU;

import dynamicobjects.lamp.Lamp;
import dynamicobjects.lamp.Lamp2;
import dynamicobjects.lamp.LampAnimator;

import com.jogamp.opengl.util.gl2.GLUT;

public class AssignmentScene {

	private GLU glu = new GLU();
	private GLUT glut = new GLUT();

	private final double INC_ROTATE = 2.0;
	private final double LAMP_INC = 0.13;

	private double rotate = 0.0;
	private double lampPeriod = 0.13;
	private double currentLampRotation = 0.0;
	private boolean objectsOn = true;

	private int canvaswidth = 0, canvasheight = 0;

	private Light light;
	private Light illuminationLight;
	private Camera camera;
	private Axes axes;
	private Lamp2 lamp;

	private Brick brickOne;
	private Brick brickTwo;
	private Brick brickThree;
	private Brick brickFour;
	private Brick brickFive;
	private Brick brickSix;

	private Barrel barrelOne;
	private Barrel barrelTwo;
	private Barrel barrelThree;
	private Barrel barrelFour;
	private Barrel barrelFive;
	private Barrel barrelSix;

	private Room room;

	private Texture floorTexture;
	private Texture wallOneTexture;
	private Texture wallTwoTexture;
	private Texture wallThreeTexture;
	private Texture wallFourTexture;
	private Texture barrelOneTexture;
	private Texture barrelTwoTexture;
	private Texture barrelThreeTexture;
	private Texture barrelFourTexture;
	private Texture barrelFiveTexture;
	private Texture barrelSixTexture;
	private Texture lampTopTexture;
	private Texture lampTexture;
	private Texture brickTexture;

	/**
	 * Constructor.
	 * 
	 * @param gl
	 *            OpenGL context
	 * @param camera
	 *            Instance of the camera class, which uses the idea of moving
	 *            around a virtual sphere, centred on the origin, under mouse
	 *            control.
	 */
	public AssignmentScene(GL2 gl, Camera camera) {
		light = new Light(GL2.GL_LIGHT0, new float[] { 1.0f, 1.0f, 1.0f, 1.0f }); // Create
																					// a
																					// default
																					// light
		light.makeDirectional();
		illuminationLight = new Light(GL2.GL_LIGHT1, new float[] { 2.0f, 1.5f,
				2.5f, 1.0f }, new float[] { 0.05f, 0.2f, 0.05f }, new float[] {
				0.7f, 0.7f, 0.7f }, new float[] { 0.5f, 0.5f, 0.5f }, true);
		this.camera = camera;
		axes = new Axes(2.2, 1.8, 1.6);
		createRenderObjects(gl); // Create/load objects
	}

	private void createRenderObjects(GL2 gl) {

		// Some of the objects will have textures applied, so load the relevant
		// textures
		floorTexture = loadTexture(gl, "floor.jpg");
		wallOneTexture = loadTexture(gl, "wall1.jpg");
		wallTwoTexture = loadTexture(gl, "wall2.jpg");
		wallThreeTexture = loadTexture(gl, "wall3.jpg");
		wallFourTexture = loadTexture(gl, "wall4.jpg");
		barrelOneTexture = loadTexture(gl, "barrel_shell.jpg");
		barrelTwoTexture = loadTexture(gl, "barrel_rusty_red.jpg");
		barrelThreeTexture = loadTexture(gl, "barrel_explosive.jpg");
		barrelFourTexture = loadTexture(gl, "barrel_grey.jpg");
		barrelFiveTexture = loadTexture(gl, "barrel_explosive.jpg");
		barrelSixTexture = loadTexture(gl, "barrel_radioactive.jpg");
		lampTopTexture = loadTexture(gl, "lamp_top.jpg");
		lampTexture = loadTexture(gl, "lamp_body.jpg");
		brickTexture = loadTexture(gl, "brick.jpg");

		room = new Room(new Texture[] { floorTexture, wallOneTexture,
				wallTwoTexture, wallThreeTexture, wallFourTexture }, 8, 10,
				100, 100);

		brickOne = new Brick(brickTexture);
		brickTwo = new Brick(brickTexture);
		brickThree = new Brick(brickTexture);
		brickFour = new Brick(brickTexture);
		brickFive = new Brick(brickTexture);
		brickSix = new Brick(brickTexture);

		barrelOne = new Barrel(barrelOneTexture, 30, 30);
		barrelTwo = new Barrel(barrelTwoTexture, 30, 30);
		barrelThree = new Barrel(barrelThreeTexture, 30, 30);
		barrelFour = new Barrel(barrelFourTexture, 30, 30);
		barrelFive = new Barrel(barrelFiveTexture, 30, 30);
		barrelSix = new Barrel(barrelSixTexture, 30, 30);

		lamp = new Lamp2(GL2.GL_LIGHT2,
				new Texture[] { lampTexture, lampTexture, lampTexture,
				lampTexture, lampTexture, lampTopTexture }, 30, 30);
		/* end of own code */
	}

	private Texture loadTexture(GL2 gl, String filename) {
		Texture tex = null;
		// since file loading is involved, must use try...catch
		try {
			File f = new File(filename);

			// The following line results in a texture that is flipped
			// vertically (i.e. is upside down)
			// due to OpenGL and Java (0,0) position being different:
			// tex = TextureIO.newTexture(new File(filename), false);

			// So, instead, use the following three lines which flip the image
			// vertically:
			BufferedImage img = ImageIO.read(f); // read file into BufferedImage
			ImageUtil.flipImageVertically(img);

			// No mip-mapping.
			tex = AWTTextureIO.newTexture(GLProfile.getDefault(), img, false);

			// Different filter settings can be used to give different effects
			// when the texture
			// is applied to a set of polygons.
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);

		} catch (Exception e) {
			System.out.println("Error loading texture " + filename);
		}
		return tex;
	} // end of loadTexture()

	// called from SG1.reshape() if user resizes the window
	public void setCanvasSize(int w, int h) {
		canvaswidth = w;
		canvasheight = h;
	}

	/**
	 * Method used from the GUI to control whether or not all the objects are
	 * displayed
	 * 
	 * @param b
	 *            true if the objects should be displayed
	 */
	public void setObjectsDisplay(boolean b) {
		objectsOn = b;
	}

	/**
	 * Retrieves the first Light instance so that its attributes can be set from
	 * the GUI. Currently only returns the first light instance, which is set to
	 * be the general light for the entire scene. If two lights are created in
	 * this class, then the method could be rewritten so that a parameter could
	 * be used to return the specific light. Alternatively a separate method
	 * could be used.
	 * 
	 * @return returns the first Light instance used in this class
	 */
	public Light getLight() {
		return light;
	}

	/**
	 * Retrieves the Axes instance so that its attributes can be set from the
	 * GUI, e.g. turned on and off.
	 * 
	 * @return returns the Axes instance used in this class
	 */
	public Axes getAxes() {
		return axes;
	}

	/**
	 * Sets the animation control attribute to its initial value and sets the
	 * objects to display
	 */
	public void reset() {
		rotate = 0.0;
		setObjectsDisplay(true);
	}

	/**
	 * Increments the animation control attribute
	 */
	public void incRotate() {
		rotate = (rotate + INC_ROTATE) % 360;
		if (Math.sin(lampPeriod + Math.PI) - 0.7 > 0) {
			currentLampRotation += Math.exp(-(Math.sin(LAMP_INC) + 1)) * 5;
		}
		lampPeriod += LAMP_INC;
	}

	/**
	 * Updates the animation control variable. Could be rewritten to update more
	 * things for each frame of animation.
	 */
	public void update() {
		incRotate();
	}

	public void setLampAnimator(LampAnimator animator) {
		//this.lamp.setAnimator(animator);
	}

	public LampAnimator getLampAnimator() {
		//return this.lamp.getAnimator();
		return null;
	}

	private void doLight(GL2 gl) {
		gl.glPushMatrix();
		gl.glRotated(rotate, 0, 1, 0);
		if (light.getSwitchedOn()) {
			light.use(gl, glut, false);
		} else
			light.disable(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		illuminationLight.use(gl, glut, false);
		gl.glPopMatrix();
	}

	/**
	 * Renders the scene.
	 * 
	 * @param gl
	 *            OpenGL context
	 */
	public void render(GL2 gl) {
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		camera.view(glu); // Orientate the camera
		doLight(gl); // Place the light

		if (axes.getSwitchedOn())
			axes.display(gl, glut);

		if (objectsOn) { // Render the objects

			room.render(gl);

			// Draw barrels
			gl.glPushMatrix();
			gl.glTranslated(4, 0, 3);
			gl.glRotated(-90, 1.0, 0, 0);
			barrelOne.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(2.5, 0, 3);
			gl.glRotated(40, 0, 1.0, 0);
			barrelTwo.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(-2.7, 0, 2.3);
			gl.glRotated(55, 0, 1.0, 0);
			barrelThree.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(1.6, 1.5, -4.6);
			gl.glRotated(90, 1.0, 0, 0);
			barrelFour.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(3.0, 0, -3.9);
			gl.glRotated(180, 1.0, 0, 0);
			gl.glRotated(90, 1.0, 0, 0);
			barrelFive.render(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslated(4.2, 0, -2.6);
			gl.glRotated(-180, 1.0, 0, 0);
			gl.glRotated(90, 1.0, 0, 0);
			barrelSix.render(gl);
			gl.glPopMatrix();

			// draw bricks

			gl.glPushMatrix();
			gl.glTranslated(3, 0, -2);
			gl.glRotated(15, 0, 1.0, 0);
			brickOne.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(3.25, 0, -2.5);
			gl.glRotated(-15, 0, 1.0, 0);
			brickTwo.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(3.1, 0.2, -2.35);
			gl.glRotated(120, 0, 1.0, 0);
			brickThree.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(2.2, 0, -2.9);
			gl.glRotated(70, 0, 1.0, 0);
			brickFour.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(3.7, 0, -3.7);
			gl.glRotated(120, 0, 1.0, 0);
			brickFive.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(3.8, 0, -2.2);
			gl.glRotated(-50, 0, 1.0, 0);
			brickSix.render(gl);
			gl.glPopMatrix();

			// Draw lamp
			gl.glPushMatrix();
			gl.glRotated(-currentLampRotation, 0, 1.0, 0);
			gl.glTranslated(0, 0, 2);
			lamp.render(gl);
			gl.glPopMatrix();
		}
	}

}
