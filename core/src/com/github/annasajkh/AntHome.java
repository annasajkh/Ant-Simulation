package com.github.annasajkh;

import com.badlogic.gdx.graphics.Color;

public class AntHome extends GameObject
{
	
	float radius;
	
	public AntHome(float x, float y, float radius)
	{
		super(x,y);
		this.radius = radius;
	}
	

	@Override
	public void update(float delta)
	{
		
		
	}

	@Override
	public void draw()
	{
		Core.shapeRenderer.setColor(Color.WHITE);
		Core.shapeRenderer.circle(x, y, radius);
	}

}
