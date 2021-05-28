package com.github.annasajkh;

import com.badlogic.gdx.graphics.Color;


enum Trail
{
	EXPLORE,
	BACK,
	NONE;
}

public class Rectangle extends GameObject
{
	float width, height;
	Color color;
	
	Trail trail;
	

	public Rectangle(float x, float y, float width, float height)
	{
		super(x, y);
		
		this.width = width;
		this.height = height;
		
		trail = Trail.NONE;
		color = Color.BLACK;

	}

	@Override
	public void update(float delta)
	{
		switch(trail)
		{
			case EXPLORE:
				color = Color.GREEN;
				break;
			case BACK:
				color = Color.BLUE;
				break;
	
			case NONE:
				color = Color.BLACK;
				break;
		}
	}

	@Override
	public void draw()
	{
		Core.shapeRenderer.setColor(color);
		Core.shapeRenderer.rect(x - width / 2, y - height / 2, width, height);
	}
	
}
