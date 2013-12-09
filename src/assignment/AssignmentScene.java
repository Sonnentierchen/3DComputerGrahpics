package assignment;

/**
 Author: Steve Maddock
 Last updated: 12 November 2013

 EDIT:
 This structure code has been used as a base to create the scene.
 The code has been heavily altered by the following author: Florian Blume, fblume1@sheffield.ac.uk.
 */

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.awt.image.*;

import javax.imageio.*;

import object.RenderContainer;
import object.RenderContainer.RenderingMode;
import object.TexturedObject;
import object.staticobjects.Barrel;
import object.staticobjects.Brick;
import object.staticobjects.FuseBox;
import object.staticobjects.Room;

import com.jogamp.opengl.util.awt.*;

import javax.media.opengl.*;

import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;

import javax.media.opengl.glu.GLU;

import object.dynamicobjects.dynamicbarrel.DynamicBarrel;
import object.dynamicobjects.lamp.HeadLightLampAnimator;
import object.dynamicobjects.lamp.JumpLampAnimator;
import object.dynamicobjects.lamp.Lamp;
import object.animation.Animator;

import com.jogamp.opengl.util.gl2.GLUT;

public class AssignmentScene {

	private GLU glu = new GLU();
	private GLUT glut = new GLUT();

	/*
	 * The increment of the position and rotation of the lamp, the animator
	 * itself is independent of this increment
	 */
	private final double LAMP_INC = 0.15;
	/* The animation speed of the running lamp */
	private double lampSpeed = 1.0;
	private double lampRotate = 0.0;
	/* The position of the lamp of the circle */
	private double currentLampPosition = 0.0;
	private double lampJumpHeight = 1.0;

	/* Indicates whether the flickering light is turned on */
	private boolean flickeringLightOn = true;

	private final String TEXTURE_FOLDER = ".\\textures\\";

	private boolean objectsOn = true;

	private int canvaswidth = 0, canvasheight = 0;
	private static final int DEFAULT_SLICES = 30;

	private Light light;
	private Light illuminationLight;
	private Camera camera;
	private Axes axes;
	private Lamp jumpingLamp;
	private JumpLampAnimator lampAnimator;
	private Lamp lampOne;
	private Lamp lampTwo;
	private Lamp lampThree;
	private Lamp lampFour;

	private Brick brickOne;
	private Brick brickTwo;
	private Brick brickThree;
	private Brick brickFour;
	private Brick brickFive;
	private Brick brickSix;

	private Barrel barrelOne;
	private Barrel barrelTwo;
	private DynamicBarrel barrelThree;
	private Barrel barrelFour;
	private Barrel barrelFive;
	private Barrel barrelSix;

	private FuseBox fuseBox;

	private Room room;

	private Texture floorTexture;
	private Texture wallOneTexture;
	private Texture wallTwoTexture;
	private Texture wallThreeTexture;
	private Texture wallFourTexture;
	private Texture ceilingTexture;
	private Texture barrelOneTexture;
	private Texture barrelTwoTexture;
	private Texture barrelThreeTexture;
	private Texture barrelFourTexture;
	private Texture barrelFiveTexture;
	private Texture barrelSixTexture;
	private Texture lampTopTexture;
	private Texture lampTexture;
	private Texture brickTexture;
	private Texture fuseBoxFront;
	private Texture fuseBoxSides;

	/* A list to easily turn off or on the textures on all objects */
	private List<TexturedObject> texturedObjects = new LinkedList<TexturedObject>();
	/* A list to easily switch the rendering mode of all objects */
	private List<RenderContainer> renderContainers = new LinkedList<RenderContainer>();

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
		light = new Light(GL2.GL_LIGHT0,
				new float[] { 3.0f, 3.0f, 3.0f, 1.0f }, new float[] { 0.05f,
						0.2f, 0.05f }, new float[] { 0.5f, 0.5f, 0.5f },
				new float[] { 0.5f, 0.5f, 0.5f }, true);
		light.makeDirectional();

		/*
		 * A light to keep some global illumniation on when all lights are
		 * switched off
		 */
		illuminationLight = new Light(GL2.GL_LIGHT1, new float[] { 2.0f, 1.5f,
				2.5f, 1.0f }, new float[] { 0.05f, 0.2f, 0.05f }, new float[] {
				0.7f, 0.7f, 0.7f }, new float[] { 0.5f, 0.5f, 0.5f }, true);
		this.camera = camera;
		axes = new Axes(2.2, 1.8, 1.6);
		createRenderObjects(gl); // Create/load objects
	}

	private void createRenderObjects(GL2 gl) {
		loadAllTextures(gl);
		fuseBox = new FuseBox(new Texture[] { fuseBoxFront, fuseBoxSides,
				fuseBoxSides, fuseBoxSides, fuseBoxSides }, 10, 10);

		room = new Room(new Texture[] { floorTexture, wallOneTexture,
				wallTwoTexture, wallThreeTexture, wallFourTexture, ceilingTexture }, 8, 10,
				100, 100);

		createBricks(gl);
		createBarrels(gl);
		createLamps(gl);

		texturedObjects = addObjectsToList(TexturedObject.class);

		renderContainers = addObjectsToList(RenderContainer.class);
		/* end of own code */
	}

	private void loadAllTextures(GL2 gl) {
		floorTexture = loadTexture(gl, TEXTURE_FOLDER + "floor.jpg");
		wallOneTexture = loadTexture(gl, TEXTURE_FOLDER + "wall1.jpg");
		wallTwoTexture = loadTexture(gl, TEXTURE_FOLDER + "wall2.jpg");
		wallThreeTexture = loadTexture(gl, TEXTURE_FOLDER + "wall3.jpg");
		wallFourTexture = loadTexture(gl, TEXTURE_FOLDER + "wall4.jpg");
		ceilingTexture = loadTexture(gl, TEXTURE_FOLDER + "ceiling.jpg");
		barrelOneTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_shell.jpg");
		barrelTwoTexture = loadTexture(gl, TEXTURE_FOLDER
				+ "barrel_rusty_red.jpg");
		barrelThreeTexture = loadTexture(gl, TEXTURE_FOLDER
				+ "barrel_explosive.jpg");
		barrelFourTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_grey.jpg");
		barrelFiveTexture = loadTexture(gl, TEXTURE_FOLDER
				+ "barrel_explosive.jpg");
		barrelSixTexture = loadTexture(gl, TEXTURE_FOLDER
				+ "barrel_radioactive.jpg");
		lampTopTexture = loadTexture(gl, TEXTURE_FOLDER + "lamp_top.jpg");
		lampTexture = loadTexture(gl, TEXTURE_FOLDER + "lamp_body.jpg");
		brickTexture = loadTexture(gl, TEXTURE_FOLDER + "brick.jpg");
		fuseBoxFront = loadTexture(gl, TEXTURE_FOLDER + "fuse_box_front.jpg");
		fuseBoxSides = loadTexture(gl, TEXTURE_FOLDER + "fuse_box_side.jpg");
	}

	private void createBricks(GL2 gl) {
		brickOne = new Brick(brickTexture);
		brickTwo = new Brick(brickTexture);
		brickThree = new Brick(brickTexture);
		brickFour = new Brick(brickTexture);
		brickFive = new Brick(brickTexture);
		brickSix = new Brick(brickTexture);
	}

	private void createBarrels(GL2 gl) {
		barrelOne = new Barrel(barrelOneTexture, DEFAULT_SLICES, DEFAULT_SLICES);
		barrelTwo = new Barrel(barrelTwoTexture, DEFAULT_SLICES, DEFAULT_SLICES);
		barrelThree = new DynamicBarrel(barrelThreeTexture, DEFAULT_SLICES,
				DEFAULT_SLICES);
		barrelThree.setAnimationSpeed(3.0);
		barrelFour = new Barrel(barrelFourTexture, DEFAULT_SLICES,
				DEFAULT_SLICES);
		barrelFive = new Barrel(barrelFiveTexture, DEFAULT_SLICES,
				DEFAULT_SLICES);
		barrelSix = new Barrel(barrelSixTexture, DEFAULT_SLICES, DEFAULT_SLICES);
	}

	private void createLamps(GL2 gl) {
		jumpingLamp = new Lamp(GL2.GL_LIGHT2, new Texture[] { lampTexture,
				lampTexture, lampTexture, lampTexture, lampTexture,
				lampTopTexture }, DEFAULT_SLICES, DEFAULT_SLICES);
		/* Creates a new animator for the lamp, that makes it jump. */
		lampAnimator = new JumpLampAnimator();
		jumpingLamp.setAnimator(lampAnimator);

		lampOne = new Lamp(GL2.GL_LIGHT3, new Texture[] { lampTexture,
				lampTexture, lampTexture, lampTexture, lampTexture,
				lampTopTexture }, DEFAULT_SLICES, DEFAULT_SLICES);
		HeadLightLampAnimator headLightAnimator = new HeadLightLampAnimator();
		/* Creates a new animator for the lamp that makes it bend it's head. */
		lampOne.setAnimator(headLightAnimator);
		lampOne.startAnimation();

		lampTwo = new Lamp(GL2.GL_LIGHT4, new Texture[] { lampTexture,
				lampTexture, lampTexture, lampTexture, lampTexture,
				lampTopTexture }, DEFAULT_SLICES, DEFAULT_SLICES);

		lampThree = new Lamp(GL2.GL_LIGHT5, new Texture[] { lampTexture,
				lampTexture, lampTexture, lampTexture, lampTexture,
				lampTopTexture }, DEFAULT_SLICES, DEFAULT_SLICES);
		HeadLightLampAnimator headLightAnimatorBended = new HeadLightLampAnimator();
		headLightAnimatorBended.setBendingAngle(-30);
		headLightAnimatorBended.setLookupAngle(70);
		lampThree.setAnimator(headLightAnimatorBended);
		lampThree.startAnimation();

		lampFour = new Lamp(GL2.GL_LIGHT6, new Texture[] { lampTexture,
				lampTexture, lampTexture, lampTexture, lampTexture,
				lampTopTexture }, DEFAULT_SLICES, DEFAULT_SLICES);
		HeadLightLampAnimator headLightAnimator2 = new HeadLightLampAnimator();
		lampFour.setAnimator(headLightAnimator2);
		lampFour.startAnimation();
	}

	/**
	 * Adds all the objects of this scene to the given list with the given interface or class.
	 * 
	 * @param clazz the class or interface type that the objects share and should be added as
	 * 
	 * @return the list of the objects
	 */
	private <T> LinkedList<T> addObjectsToList(Class<T> clazz) {
		LinkedList<T> list = new LinkedList<T>();
		list.add((T) room);
		list.add((T) fuseBox);
		list.add((T) brickOne);
		list.add((T) brickTwo);
		list.add((T) brickThree);
		list.add((T) brickFour);
		list.add((T) brickFive);
		list.add((T) brickSix);

		list.add((T) barrelOne);
		list.add((T) barrelTwo);
		list.add((T) barrelThree);
		list.add((T) barrelFour);
		list.add((T) barrelFive);
		list.add((T) barrelSix);

		list.add((T) jumpingLamp);
		list.add((T) lampOne);
		list.add((T) lampTwo);
		list.add((T) lampThree);
		list.add((T) lampFour);
		return list;
	}

	private Texture loadTexture(GL2 gl, String filename) {
		Texture tex = null;
		// since file loading is involved, must use try...catch
		try {
			// File f = new File("textures\\placeholder.jpg");
			File f = new File(filename);

			BufferedImage img = ImageIO.read(f); // read file into BufferedImage
			ImageUtil.flipImageVertically(img);

			tex = AWTTextureIO.newTexture(GLProfile.getDefault(), img, false);

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
		lampRotate = 0.0;
		currentLampPosition = 0.0;
		lampSpeed = 1.0;
		lampJumpHeight = 1.0;
		// ((BasicJumpLampAnimator)
		// lampAnimator).scheduleSetJumpHeight(lampJumpHeight);

		showTextures(true);
		jumpingLamp.showLight(true);
	}

	/**
	 * Updates the animation control variable. Could be rewritten to update more
	 * things for each frame of animation.
	 */
	public void update() {
		/*
		 * When the lamp reaches the area between 105 and 160 degrees it has to
		 * perform the higher jump to get over the barrel.
		 */
		if (currentLampPosition % 360 > 105 && currentLampPosition % 360 < 160) {
			lampJumpHeight = 2.0;
			/*
			 * Reduce the animation speed temporarily because the animator of
			 * the lamp uses the jump to alter the speed of the animation and
			 * would be too fast otherwise
			 */
			lampSpeed = 0.7;
		} else {
			lampJumpHeight = 1.0;
			lampSpeed = 1.0;
		}
		/*
		 * To prevent a lagging animation, only when the lamp reaches the ground
		 * again, set the jump height and speed.
		 */
		lampAnimator.scheduleSetJumpHeight(lampJumpHeight);
		lampAnimator.scheduleSetAnimationSpeed(lampSpeed);

		/*
		 * Only move the lamp when it's in air and therefore the sine of
		 * lampRotate is less than 0
		 */
		if (Math.sin(lampRotate) <= 0) {
			/*
			 * Alter the jump height for the distance that the lamp is to be
			 * moved, to make it look more realistic
			 */
			double lampJumpHeight_;
			if (lampJumpHeight < 2) {
				lampJumpHeight_ = lampJumpHeight / 2;
			} else {
				lampJumpHeight_ = lampJumpHeight;
			}
			currentLampPosition += -Math.sin(lampRotate % (2 * Math.PI)) * 4
					* lampJumpHeight_ * lampSpeed;
		} else {
			// currentLampRotate += Math.sin(lampRotate % (2 * Math.PI)) * 4
			// * lampJumpHeight;
		}
		lampRotate += LAMP_INC * lampSpeed;

		/* If the flickering light is turned on, turn it on and off randomly */
		if (flickeringLightOn) {
			lampTwo.showLight(Math.random() > 0.1);
		}
	}

	/**
	 * Turns the animation of the barrel and lamp on or off. 
	 * 
	 * @param doAnimation sets whether the animation should be started or stopped
	 */
	public void doAnimation(boolean doAnimation) {
		if (doAnimation) {
			jumpingLamp.startAnimation();
			barrelThree.startAnimation();
		} else {
			jumpingLamp.stopAnimation();
			barrelThree.stopAnimation();
		}
	}

	/**
	 * Pauses the animation of the barrel and lamp. 
	 */
	public void pauseAnimation() {
		jumpingLamp.pauseAnimation();
		barrelThree.pauseAnimation();
	}

	/**
	 * Indicates whether the light of the jumping lamp should be shown.
	 * 
	 * @param showLight show or hide the light of the jumping lamp
	 */
	public void showJumpingLampLight(boolean showLight) {
		this.jumpingLamp.showLight(showLight);
	}

	/**
	 * Indicates whether the light of the lying lamp should be shown.
	 * 
	 * @param showLight show or hide the light of the lying lamp
	 */
	public void showLyingLampLight(boolean showLight) {
		this.lampTwo.showLight(showLight);
		this.flickeringLightOn = showLight;
	}

	/**
	 * Indicates whether the light of one of the standing lamps should be shown.
	 * 
	 * @param showLight show or hide the light of the standing lamp
	 */
	public void showStandingLampLightOne(boolean showLight) {
		this.lampOne.showLight(showLight);
	}

	/**
	 * Indicates whether the light of one of the standing lamps should be shown.
	 * 
	 * @param showLight show or hide the light of the standing lamp
	 */
	public void showStandingLampLightTwo(boolean showLight) {
		this.lampThree.showLight(showLight);
	}

	/**
	 * Indicates whether the light of one of the standing lamps should be shown.
	 * 
	 * @param showLight show or hide the light of the standing lamp
	 */
	public void showStandingLampLightThree(boolean showLight) {
		this.lampFour.showLight(showLight);
	}

	/**
	 * Show or hide all textures of all objects. 
	 * 
	 * @param showTextures whether to show or hide all textures of all objects 
	 */
	public void showTextures(boolean showTextures) {
		for (TexturedObject texturedObject : texturedObjects) {
			texturedObject.showTextures(showTextures);
		}
	}

	/**
	 * Sets the {@link RenderContainer.RenderingMode} mode of all objects of this scene.
	 * 
	 * @param mode the rendering mode of the objects in this scene
	 */
	public void setRenderingMode(RenderingMode mode) {
		for (RenderContainer renderContainer : renderContainers) {
			renderContainer.setRenderingMode(mode);
		}
	}

	private void doLight(GL2 gl) {
		gl.glPushMatrix();
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

			gl.glPushMatrix();
				gl.glScaled(2.0, 2.0, 2.0);
				gl.glTranslated(-2.5, 1.4, 1.3);
				gl.glRotated(90, 0, 1.00, 0);
				gl.glRotated(90, 1.0, 0, 0);
				fuseBox.render(gl);
			gl.glPopMatrix();

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
				gl.glTranslated(-0.2, 0, -3.4);
				gl.glRotated(35, 0, 1.0, 0);
				barrelThree.render(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
				gl.glTranslated(1.6, 1.5, -4.6);
				gl.glRotated(90, 1.0, 0, 0);
				gl.glTranslated(0, 0, 0.3);
				gl.glScaled(1, 1, 0.8);
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
				gl.glTranslated(2.6, 0, -2.9);
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

			gl.glPushMatrix();
				/* Scale all the lamps to make the size fit the barrel's size */
				gl.glScaled(0.5, 0.5, 0.5);

				// Draw lamps
				gl.glPushMatrix();
					gl.glRotated(-currentLampPosition, 0, 1.0, 0);
					gl.glTranslated(0, 0, 4);
					jumpingLamp.render(gl);
				gl.glPopMatrix();

				gl.glPushMatrix();
					gl.glTranslated(6, 0, 4.5);
					gl.glRotated(80, 0, 1.0, 0);
					lampOne.render(gl);
				gl.glPopMatrix();

				/* The lying lamp */
				gl.glPushMatrix();
					gl.glTranslated(3.7, 0.35, -3.18);
					gl.glRotated(-2, 0, 0, 1.0);
					gl.glRotated(-39.5, 1.0, 0, 0);
					gl.glRotated(180, 0, 0, 1.0);
					gl.glRotated(90, 0, 0, 1.0);
					lampTwo.render(gl);
				gl.glPopMatrix();

				gl.glPushMatrix();
					gl.glTranslated(8, 0, -3);
					gl.glRotated(40, 0, 1.0, 0);
					lampThree.render(gl);
				gl.glPopMatrix();

				gl.glPushMatrix();
					gl.glTranslated(-6, 0, -6);
					gl.glRotated(170, 0, 1.0, 0);
					lampFour.render(gl);
				gl.glPopMatrix();

			gl.glPopMatrix();
		}
	}

}
