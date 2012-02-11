package com.mortalpowers.android.bowiefind;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Example implements com.badlogic.gdx.ApplicationListener {
	SpriteBatch spriteBatch;
	Texture texture;
	BitmapFont font;
	Vector2 textPosition = new Vector2(100, 100);
	Vector2 textDirection = new Vector2(1, 1);
	double imageRotation = 0;
	boolean spinning = false;
	private OrthographicCamera camera;

	Mesh triangle;

	Sound touchSound;

	@Override
	public void create() {
		font = new BitmapFont();
		font.setColor(Color.RED);
		texture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		spriteBatch = new SpriteBatch();

		touchSound = Gdx.audio.newSound(Gdx.files
				.internal("data/touchsound.ogg"));

		if (triangle == null) {
			triangle = new Mesh(true, 3, 3, new VertexAttribute(Usage.Position,
					3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
					 new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
			triangle.setVertices(new float[] { -.5f, -.5f, 0, Color.toFloatBits(255, 0, 0, 255), 0,1,
												.5f, -.5f, 0, Color.toFloatBits(0, 255, 0, 255),1,1,
												0,.5f, 0 ,Color.toFloatBits(0, 0, 255, 255) ,.5f,0});

			triangle.setIndices(new short[] { 0, 1, 2 });
		}

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	public void toggleSpin() {
		spinning = !spinning;
		touchSound.play();
	}

	public void acceptInput() {
		if (Gdx.input.justTouched()) {
			System.out.println("Touched!");
			toggleSpin();
		}
	}

	@Override
	public void render() {
		acceptInput();

		int centerX = Gdx.graphics.getWidth() / 2;
		int centerY = Gdx.graphics.getHeight() / 2;

		
		camera.update();
        camera.apply(Gdx.gl10);
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);

		
		Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
	    texture.bind();
		triangle.render(GL10.GL_TRIANGLES, 0, 3);
		
		// more fun but confusing :)
		textPosition.add(textDirection.tmp().mul(Gdx.graphics.getDeltaTime())
				.mul(60));
		textPosition.x += textDirection.x * Gdx.graphics.getDeltaTime() * 60;
		textPosition.y += textDirection.y * Gdx.graphics.getDeltaTime() * 60;

		if (textPosition.x < 0) {
			textDirection.x = -textDirection.x;
			textPosition.x = 0;
		}
		if (textPosition.x > Gdx.graphics.getWidth()) {
			textDirection.x = -textDirection.x;
			textPosition.x = Gdx.graphics.getWidth();
		}
		if (textPosition.y < 0) {
			textDirection.y = -textDirection.y;
			textPosition.y = 0;
		}
		if (textPosition.y > Gdx.graphics.getHeight()) {
			textDirection.y = -textDirection.y;
			textPosition.y = Gdx.graphics.getHeight();
		}

		imageRotation = 180.0
				/ Math.PI
				* Math.atan(Gdx.input.getAccelerometerY()
						/ Gdx.input.getAccelerometerX());
		if (Gdx.input.getAccelerometerX() < 0) {
			imageRotation += 180;
		}

		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(texture, centerX - texture.getWidth() / 4, centerY
				- texture.getHeight() / 4, texture.getWidth() / 4,
				texture.getHeight() / 4, texture.getWidth() /2,
				texture.getHeight() /2, 1, 1, (float) imageRotation, 0, 0,
				texture.getWidth(), texture.getHeight(), false, false);
		
		font.draw(spriteBatch, "Hello World!", (int) textPosition.x,
				(int) textPosition.y);
		spriteBatch.end();
		
		
        

	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
		camera = new OrthographicCamera(2f * aspectRatio, 2f);
		//spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		textPosition.set(0, 0);

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		touchSound.dispose();

	}

}
