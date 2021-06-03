package com.github.annasajkh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class InputManager implements InputProcessor
{
	
	float touchX;
	float touchY;

	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if(Gdx.input.isButtonPressed(0))
		{			
			Vector3 pos = Core.camera.unproject(new Vector3(screenX, screenY, 0));
			
			touchX = pos.x;
			touchY = pos.y;
			
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		if(Gdx.input.isButtonPressed(0))
		{	
			Core.AntToFocus = null;
			
			Vector3 pos = Core.camera.unproject(new Vector3(screenX, screenY, 0));
			
			Core.camera.position.add(touchX - pos.x,touchY - pos.y,0);
			
			Core.camera.update();
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		
		if(amountY < 0)
		{
			Core.camera.zoom -= Core.zoomSpeed * Core.camera.zoom * Core.zoomFactor;
		}
		else
		{
			Core.camera.zoom += Core.zoomSpeed * Core.camera.zoom * Core.zoomFactor;
		}
		
		Core.camera.zoom = MathUtils.clamp(Core.camera.zoom,0.0001f,Core.maxZoom);
		Core.camera.update();
		
		return true;
	}

}
