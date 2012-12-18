package com.flammantis.PlatformRPG.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.flammantis.PlatformRPG.controllers.Assets;

public class MainMenuScreen implements Screen
{

	Game theGame;

	OrthographicCamera guiCam;
	SpriteBatch batch;
	Rectangle playBounds;

	Vector3 touchPoint;

	public MainMenuScreen(Game g)
	{

		this.theGame = g;

		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		batch = new SpriteBatch();
		playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
		touchPoint = new Vector3();

	}

	@Override
	public void render(float delta)
	{

		update(delta);
		draw(delta);

	}

	public void draw(float deltaTime)
	{
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);

		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		batch.end();

		batch.enableBlending();
		batch.begin();
		batch.draw(Assets.title, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
		batch.draw(Assets.play, 10, 200 - 110 / 2, 300, 110);
		batch.end();
	}

	private void update(float delta)
	{

		if (Gdx.input.justTouched())
		{

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (pointInRectangle(playBounds, touchPoint.x, touchPoint.y))
			{

				theGame.setScreen(new GameScreen(theGame));
				return;
			}

		}

	}

	public static boolean pointInRectangle(Rectangle r, float x, float y)
	{
		return r.x <= x && r.x + r.width >= x && r.y <= y
				&& r.y + r.height >= y;
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

}
