package com.mortalpowers.android.bowiefind;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



public class Example implements com.badlogic.gdx.ApplicationListener {
	SpriteBatch spriteBatch;
	Texture texture;
	BitmapFont font;
	Vector2 textPosition = new Vector2(100, 100);
	Vector2 textDirection = new Vector2(1, 1);
	int imageRotation = 0;
	boolean spinning = false;
	
	@Override
	public void create() {
		font = new BitmapFont();
		font.setColor(Color.RED);
		texture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		spriteBatch = new SpriteBatch();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	public void toggleSpin() {
		spinning = !spinning;
	}

	public void acceptInput() {
		if(Gdx.input.justTouched()) {
			System.out.println("Touched!");
			toggleSpin();
		}
	}
	
	@Override
	public void render() {
		acceptInput();
		
		int centerX = Gdx.graphics.getWidth() / 2;
		int centerY = Gdx.graphics.getHeight() / 2;

		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);

		// more fun but confusing :)
		 textPosition.add(textDirection.tmp().mul(Gdx.graphics.getDeltaTime()).mul(60));
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
		
		if(spinning) {
			imageRotation = (int)Gdx.input.getPitch();
		}
		System.out.println("Rotation: " + Gdx.input.getRotation() + " Pitch " + Gdx.input.getPitch() + " azimuth " + Gdx.input.getAzimuth() + " roll" + Gdx.input.getRoll());

		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(texture, centerX - texture.getWidth() / 2, centerY - texture.getHeight() / 2, texture.getWidth()/2, texture.getHeight()/2, texture.getWidth(),
			texture.getHeight(), 1, 1, imageRotation,0,0,texture.getWidth(),texture.getHeight(),false,false );
		font.draw(spriteBatch, "Hello World!", (int)textPosition.x, (int)textPosition.y);
		spriteBatch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		textPosition.set(0, 0);
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
