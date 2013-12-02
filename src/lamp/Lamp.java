/* I declare that this code is my own work */
/* Author Florian Blume, fblume1@sheffield.ac.uk */

package lamp;

import javax.media.opengl.GL2;

import assignment.Light;
import assignment.Mesh;
import assignment.ProceduralMeshFactory;
import assignment.Render;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;

public class Lamp {

	/* The following code is partly copied from Dr. Steve Maddock */
	public static final float[] DEFAULT_DIFFUSE = { 1.0f, 1.0f, 1.0f };
	public static final float[] DEFAULT_AMBIENT = { 0.1f, 0.1f, 0.1f };

	public static final int DEFAULT_SLICES = 35;

	private float[] ambient;
	private float[] diffuse;
	private float[] specular;
	private float[] shininess = { 16.0f };
	private float[] emission = { 0.0f, 0.0f, 0.0f, 1.0f };
	/* End of copy */

	private boolean switchedOn = true;
	private boolean showTextures = false;
	private Texture[] textures = new Texture[6];
	private Mesh[] meshes = new Mesh[6];
	private Render[] renders;
	private Light spotLight;
	private LampAnimator animator;
	private GLUT glut = new GLUT();

	public Lamp(int index) {
		this(index, DEFAULT_AMBIENT, DEFAULT_DIFFUSE, DEFAULT_DIFFUSE);
	}

	public Lamp(int index, float[] ambient, float[] diffuse, float[] specular) {
		this.ambient = ambient.clone();
		this.diffuse = diffuse.clone();
		this.specular = specular.clone();

		/* Base */
		meshes[0] = ProceduralMeshFactory.createCylinder(DEFAULT_SLICES,
				DEFAULT_SLICES, true);
		/* Base-joint */
		meshes[1] = ProceduralMeshFactory.createCylinder(DEFAULT_SLICES,
				DEFAULT_SLICES, true);
		/* Lower arm */
		meshes[2] = ProceduralMeshFactory.createCylinder(DEFAULT_SLICES,
				DEFAULT_SLICES, true);
		/* Middle-Joint */
		meshes[3] = ProceduralMeshFactory.createCylinder(DEFAULT_SLICES,
				DEFAULT_SLICES, true);
		/* Upper arm */
		meshes[4] = ProceduralMeshFactory.createCylinder(DEFAULT_SLICES,
				DEFAULT_SLICES, true);
		/* Head */
		meshes[5] = ProceduralMeshFactory.createCone(DEFAULT_SLICES, true);

		spotLight = new Light(index, new float[] { 0.25f, 0f, -0.15f, 1f });
		spotLight.makeSpotlight(new float[] { 2.5f, 0.0f, -0.5f, 1f }, 50f);
		switchedOn = true;
		animator = new JumpLampAnimator();
		animator.start();
	}

	public Lamp(int index, Texture[] textures) {
		this(index);
		if (textures.length != 6) {
			throw new IllegalArgumentException("Textures count not equal 6");
		}
		this.textures = textures;
		this.showTextures = true;
	}

	public void render(GL2 gl) {
		if (renders == null) {
			renders = new Render[6];
			for (int i = 0; i < 6; i++) {
				/* If the renders are not initialised do it now */
				renders[i] = (showTextures ? new Render(meshes[i], textures[i])
						: new Render(meshes[i]));
			}
		}

		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diffuse, 0);
	    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
	    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, shininess, 0);
	    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, emission, 0);

	    gl.glPushMatrix();
	    	gl.glScaled(0.5,0.5,0.5);
	    	drawBase(gl);	
	    gl.glPopMatrix();
	}
	
	private void drawBase(GL2 gl) {
    	gl.glPushMatrix();
    	
    		animator.animateBase(gl);
    	
    		/* Draw base */ 
    		gl.glPushMatrix();
    			gl.glRotated(-90, 1.0, 0.0, 0.0);
    			gl.glScaled(0.7,0.7,0.15);
    			renders[0].renderImmediateMode(gl, showTextures);
    		gl.glPopMatrix();
    		
    		drawLowerJoint(gl);	
	    gl.glPopMatrix();
	}
	
	private void drawLowerJoint(GL2 gl) {
    	gl.glPushMatrix();
    		gl.glRotated(-20, 0.0, 0.0, 1.0);
			gl.glTranslated(0, 0.15, 0);
			
    		animator.animateLowerJoint(gl);
    	
			/* Draw lower joint */
    		gl.glPushMatrix();
    			gl.glTranslated(0, 0, -0.15);
				gl.glScaled(0.26,0.26,0.3);
    			renders[1].renderImmediateMode(gl, showTextures);
    		gl.glPopMatrix();
    		
			drawLowerArm(gl);	
		gl.glPopMatrix();
	}
	
	private void drawLowerArm(GL2 gl) {
    	gl.glPushMatrix();
			
    		animator.animateLowerArm(gl);
    	
			/* Draw lower arm */
    		gl.glPushMatrix();
    			gl.glRotated(-90, 1.0, 0.0, 0.0);
    			gl.glScaled(0.1,0.1,1.3);
    			renders[2].renderImmediateMode(gl, showTextures);
    		gl.glPopMatrix();
    		
    		drawMiddleJoint(gl);	
		gl.glPopMatrix();
	}
	
	private void drawMiddleJoint(GL2 gl) {
		gl.glPushMatrix();
			gl.glTranslated(0, 1.3, 0);
			
			animator.animateMiddleJoint(gl);
		
			/* Draw middle joint */
			gl.glPushMatrix();
				gl.glTranslated(0, 0, -0.075);
				gl.glScaled(0.18,0.18,0.15);
				renders[3].renderImmediateMode(gl, showTextures);
			gl.glPopMatrix();
			
			drawUpperArm(gl);
		gl.glPopMatrix();
	}
	
	private void drawUpperArm(GL2 gl) {
		gl.glPushMatrix();
			gl.glRotated(40, 0.0, 0.0, 1.0);
			
			animator.animateUpperArm(gl);
			
			/* Draw upper arm */
			gl.glPushMatrix();
				gl.glRotated(-90, 1.0, 0.0, 0.0);
				gl.glScaled(0.1,0.1,1.3);
				renders[4].renderImmediateMode(gl, showTextures);
			gl.glPopMatrix();
			
			drawHead(gl);
		gl.glPopMatrix();
	}
	
	private void drawHead(GL2 gl) {
		gl.glPushMatrix();
			gl.glTranslated(0.1, 1.28, 0);
			gl.glRotated(-85, 0.0, 0.0, 1.0);
			
			animator.animateHead(gl);
			
			gl.glPushMatrix();
				gl.glRotated(90, 1.0, 0.0, 0.0);
				gl.glScaled(0.5,0.5,0.5);
				renders[5].renderImmediateMode(gl, showTextures);
			gl.glPopMatrix();
			
			drawBulb(gl);
		gl.glPopMatrix();
	}
	
	private void drawBulb(GL2 gl) {
		gl.glPushMatrix();
			gl.glTranslated(0, -0.5, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, new float[]{1.0f,1.0f,1.0f}, 0);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, new float[]{1.0f,1.0f,1.0f}, 0);
			gl.glRotated(-90, 0.0, 0.0, 1.0);
			gl.glRotated(-90, 1.0, 0.0, 0.0);
			gl.glTranslated(-0.1, 0, 0);
			gl.glScaled(1.1, 1.1, 1.1);
			gl.glScaled(0.5, 1.0, 1.0);
			glut.glutSolidSphere(0.20, DEFAULT_SLICES, DEFAULT_SLICES);
			
			if (switchedOn) {
				gl.glPushMatrix();
					gl.glRotated(-25, 0.0, 1.0, 0.0);
					spotLight.use(gl, glut, false);
				gl.glPopMatrix();	
			} else {
				spotLight.disable(gl);
			}
		gl.glPopMatrix();	
	}

	public void switchOn() {
		switchedOn = true;
	}

	public void switchOff() {
		switchedOn = false;
	}

}

/* End of declaration */