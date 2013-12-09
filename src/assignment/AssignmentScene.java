package assignment;

/*
 Author: Steve Maddock
 Last updated: 12 November 2013
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

import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.*;

import javax.media.opengl.glu.GLU;

import object.dynamicobjects.dynamicbarrel.DynamicBarrel;
import object.dynamicobjects.lamp.BasicJumpLampAnimator;
import object.dynamicobjects.lamp.HeadLightLampAnimator;
import object.dynamicobjects.lamp.JumpLampAnimator;
import object.dynamicobjects.lamp.Lamp;
import object.dynamicobjects.lamp.SwingJumpLampAnimator;
import object.animation.Animator;

import com.jogamp.opengl.util.gl2.GLUT;

public class AssignmentScene {

	private GLU glu = new GLU();
	private GLUT glut = new GLUT();

	private final double LAMP_INC = 0.15;
	private double lampSpeed = 1.0;
	private double lampRotate = 0.0;
	private double currentLampRotate = 0.0;
	private double lampJumpHeight = 1.0;
	
	private boolean flickeringLightOn = true;
	
	private final String TEXTURE_FOLDER = "textures\\";

	private boolean objectsOn = true;

	private int canvaswidth = 0, canvasheight = 0;

	private Light light;
	private Light illuminationLight;
	private Camera camera;
	private Axes axes;
	private Lamp jumpingLamp;
	private Animator lampAnimator;
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
	
	private List<TexturedObject> texturedObjects = new LinkedList<TexturedObject>();
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
		floorTexture = loadTexture(gl, TEXTURE_FOLDER + "floor.jpg");
		wallOneTexture = loadTexture(gl, TEXTURE_FOLDER + "wall1.jpg");
		wallTwoTexture = loadTexture(gl, TEXTURE_FOLDER + "wall2.jpg");
		wallThreeTexture = loadTexture(gl, TEXTURE_FOLDER + "wall3.jpg");
		wallFourTexture = loadTexture(gl, TEXTURE_FOLDER + "wall4.jpg");
		barrelOneTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_shell.jpg");
		barrelTwoTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_rusty_red.jpg");
		barrelThreeTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_explosive.jpg");
		barrelFourTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_grey.jpg");
		barrelFiveTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_explosive.jpg");
		barrelSixTexture = loadTexture(gl, TEXTURE_FOLDER + "barrel_radioactive.jpg");
		lampTopTexture = loadTexture(gl, TEXTURE_FOLDER + "lamp_top.jpg");
		lampTexture = loadTexture(gl, TEXTURE_FOLDER + "lamp_body.jpg");
		brickTexture = loadTexture(gl, TEXTURE_FOLDER + "brick.jpg");
		fuseBoxFront = loadTexture(gl, TEXTURE_FOLDER + "fuse_box_front.jpg");
		fuseBoxSides = loadTexture(gl, TEXTURE_FOLDER + "fuse_box_side.jpg");
		
		fuseBox = new FuseBox(new Texture[] { fuseBoxFront, fuseBoxSides,
				fuseBoxSides, fuseBoxSides, fuseBoxSides },
				20, 20);

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
		barrelThree = new DynamicBarrel(barrelThreeTexture, 30, 30);
		barrelThree.setAnimationSpeed(2.0);
		barrelFour = new Barrel(barrelFourTexture, 30, 30);
		barrelFive = new Barrel(barrelFiveTexture, 30, 30);
		barrelSix = new Barrel(barrelSixTexture, 30, 30);

		jumpingLamp = new Lamp(GL2.GL_LIGHT2,
				new Texture[] { lampTexture, lampTexture, lampTexture,
				lampTexture, lampTexture, lampTopTexture }, 30, 30);
		lampAnimator = new JumpLampAnimator();
		jumpingLamp.setAnimator(lampAnimator);
		lampOne = new Lamp(GL2.GL_LIGHT3, new Texture[] { lampTexture, lampTexture, lampTexture,
				lampTexture, lampTexture, lampTopTexture }, 30, 30);
		HeadLightLampAnimator headLightAnimator = new HeadLightLampAnimator();
		lampOne.setAnimator(headLightAnimator);
		lampOne.startAnimation();
		lampTwo = new Lamp(GL2.GL_LIGHT4, new Texture[] { lampTexture, lampTexture, lampTexture,
				lampTexture, lampTexture, lampTopTexture }, 30, 30);
		lampThree = new Lamp(GL2.GL_LIGHT5, new Texture[] { lampTexture, lampTexture, lampTexture,
				lampTexture, lampTexture, lampTopTexture }, 30, 30);
		HeadLightLampAnimator headLightAnimatorBended = new HeadLightLampAnimator();
		headLightAnimatorBended.setBendingAngle(-30);
		headLightAnimatorBended.setLookupAngle(70);
		lampThree.setAnimator(headLightAnimatorBended);
		lampThree.startAnimation();
		lampFour = new Lamp(GL2.GL_LIGHT6, new Texture[] { lampTexture, lampTexture, lampTexture,
				lampTexture, lampTexture, lampTopTexture }, 30, 30);
		HeadLightLampAnimator headLightAnimator2 = new HeadLightLampAnimator();
		lampFour.setAnimator(headLightAnimator2);
		lampFour.startAnimation();
		
		texturedObjects = addObjectsToList(TexturedObject.class);
		
		renderContainers = addObjectsToList(RenderContainer.class);
		/* end of own code */
	}
	
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
			//File f = new File("textures\\placeholder.jpg");
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
		currentLampRotate = 0.0;
		lampSpeed = 1.0;
		lampJumpHeight = 1.0;
		//((BasicJumpLampAnimator) lampAnimator).scheduleSetJumpHeight(lampJumpHeight);
		
		showTextures(true);
		jumpingLamp.showLight(true);
	}

	/**
	 * Updates the animation control variable. Could be rewritten to update more
	 * things for each frame of animation.
	 */
	public void update() {
		//System.out.println(currentLampRotate + "  " + lampRotate);
		if (currentLampRotate % 360 > 120 && currentLampRotate % 360 < 220) {
			//lampJumpHeight = 4.0;
			//lampSpeed = 0.5;
		} else {
			lampJumpHeight = 1.0;
			lampSpeed = 1.0;
		}
		//((BasicJumpLampAnimator) lampAnimator).scheduleSetJumpHeight(lampJumpHeight / 2);
		//((BasicJumpLampAnimator) lampAnimator).scheduleSetSpeed(lampSpeed);
		
		if (Math.sin(lampRotate) <= 0) {
			//if(((BasicJumpLampAnimator) lampAnimator).isOnGround() && currentLampRotate > 340) {
				
			//}
			currentLampRotate += -Math.sin(lampRotate % (2 * Math.PI)) * 4 * lampJumpHeight;
		}
		lampRotate += LAMP_INC * lampSpeed;
		if (flickeringLightOn) {
			lampTwo.showLight(Math.random() > 0.1);
		}
	}
	
	public void doAnimation(boolean doAnimation) {
		if (doAnimation) {
			jumpingLamp.startAnimation();
			barrelThree.startAnimation();
		} else {
			jumpingLamp.stopAnimation();
			barrelThree.stopAnimation();
		}
	}
	
	public void pauseAnimation() {
		jumpingLamp.pauseAnimation();
		barrelThree.pauseAnimation();
	}
	
	public void showJumpingLampLight (boolean showLight) {
		this.jumpingLamp.showLight(showLight);
	}
	
	public void showLyingLampLight (boolean showLight) {
		this.lampTwo.showLight(showLight);
		this.flickeringLightOn = showLight;
	}
	
	public void showStandingLampLightOne (boolean showLight) {
		this.lampOne.showLight(showLight);
	}
	
	public void showStandingLampLightTwo (boolean showLight) {
		this.lampThree.showLight(showLight);
	}
	
	public void showStandingLampLightThree (boolean showLight) {
		this.lampFour.showLight(showLight);
	}
	
	public void showTextures(boolean showTextures) {
		for (TexturedObject texturedObject : texturedObjects) {
			texturedObject.showTextures(showTextures);
		}
	}
	
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
			gl.glTranslated(1.5, 0, -3.3);
			gl.glRotated(20, 0, 1.0, 0);
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
			gl.glScaled(0.5, 0.5, 0.5);

			// Draw lamps
			gl.glPushMatrix();
			gl.glRotated(-currentLampRotate, 0, 1.0, 0);
			gl.glTranslated(0, 0, 4);
			jumpingLamp.render(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslated(6, 0, 4.5);
			gl.glRotated(80, 0, 1.0, 0);
			lampOne.render(gl);
			gl.glPopMatrix();
			
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
