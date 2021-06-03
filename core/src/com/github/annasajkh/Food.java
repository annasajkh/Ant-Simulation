package com.github.annasajkh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Food extends GameObject
{
	
	float radius = 3;
	Ant carrier = null;

	public Food(float x, float y)
	{
		super(x, y);
	}
	
	public void antSaw(Ant ant)
	{
		final float dx = ant.x - x;
		final float dy = ant.y - y;
		
		boolean inRadius =	(radius + Core.antSprite.getHeight() / 2 + Core.antVisionRadius) * 
					radius + Core.antSprite.getHeight() / 2 + Core.antVisionRadius) > 
					(dx * dx) + (dy * dy);
		if(inRadius)
		{
			float len;
			
			float antDirX;
			float antDirY;
			
			float dst;
			
			float dirX;
			float dirY;
			
			float dotProduct;
			
			if(ant.target == null)
			{
				len = ant.velocity.len();
				
				antDirX = ant.velocity.x / len;
				antDirY = ant.velocity.y / len;
				
				dst = Vector2.dst(x,y,ant.x,ant.y);
				
				dirX = (x - ant.x) / dst;
				dirY = (y - ant.y) / dst;
				
				dotProduct = Vector2.dot(antDirX, antDirY, dirX, dirY);
			}
			else
			{
				len = ant.velocity.len();
				
				antDirX = ant.velocity.x / len;
				antDirY = ant.velocity.y / len;
				
				dst = Vector2.dst(ant.target.x,ant.target.y,ant.x,ant.y);
				
				dirX = (ant.target.x - ant.x) / dst;
				dirY = (ant.target.y - ant.y) / dst;
				
				dotProduct = Vector2.dot(antDirX, antDirY, dirX, dirY);
				
			}
			
			
			
			if(dotProduct > 0.6f && ant.foodToCarry == null)
			{
				ant.velocity.setAngleRad(new Vector2(dirX, dirY).angleRad());
				ant.target = this;
			}
		}
	}
	
	public boolean intersects(Ant ant)
	{
		final float dx = ant.x - x;
		final float dy = ant.y - y;
		
		return 	(radius + Core.antSprite.getHeight() / 2) *  
			(radius + Core.antSprite.getHeight() / 2) > 
			(dx * dx) + (dy * dy);
	}

	@Override
	public void update(float delta)
	{
		for(Ant ant : Core.ants)
		{
			if(carrier == null)
			{				
				if(intersects(ant) && ant.foodToCarry == null)
				{
					carrier = ant;
					ant.foodToCarry = this;
					ant.target = null;
				}
				
				antSaw(ant);
			}
		}
		
		if(carrier != null)
		{
			float len = carrier.velocity.len();
			x = carrier.x + carrier.velocity.x / len * Core.antSprite.getHeight() / 2;
			y = carrier.y + carrier.velocity.y / len * Core.antSprite.getHeight() / 2;
		}
	}

	@Override
	public void draw()
	{
		Core.shapeRenderer.setColor(Color.GOLD);
		Core.shapeRenderer.circle(x, y, radius);
	}

}
