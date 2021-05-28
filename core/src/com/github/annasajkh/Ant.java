package com.github.annasajkh;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ant extends GameObject
{
	Sprite sprite;
	Vector2 velocity;
	Vector2 acceleration;
	float rotation;
	Food foodToCarry;
	static float maxVelocity = 250;
	static Vector2 rotater;
	Food target;
	List<Rectangle> path;
	
	public void follow()
	{
		if(path.isEmpty())
		{
			Core.foods.remove(foodToCarry);
			foodToCarry = null;
			return;
		}
		
		Rectangle target = path.get(path.size() - 1);
		
		float dst = Vector2.dst(x,y,target.x,target.y);
		
		float dirX = (target.x - x) / dst;
		float dirY = (target.y - y) / dst;
		
		velocity.setAngleRad(new Vector2(dirX, dirY).angleRad());
		
		final float dx = target.x - x;
		final float dy = target.y - y;
		
		
		boolean intersect;
		
		if(path.size() > 1)
		{
			intersect = 200 * 200 > (dx * dx) + (dy * dy);			
		}
		else
		{
			intersect = 3 * 3 > (dx * dx) + (dy * dy);	
		}
		
		if(intersect)
		{
			path.remove(path.size() - 1);
		}
		
	}
	
	public Ant(float x, float y, Sprite sprite)
	{
		super(x, y);
		
		this.sprite = sprite;
		acceleration = new Vector2();
		rotater = new Vector2();
		path = new ArrayList<>();
	}

	@Override
	public void update(float delta)
	{		
		rotater.set(MathUtils.random(5, 10),0);
		acceleration = rotater.rotateDeg(MathUtils.random(360));

		rotation = MathUtils.atan2(velocity.y, velocity.x ) * MathUtils.radiansToDegrees;
		
		velocity.add(acceleration);
		velocity.clamp(-maxVelocity,maxVelocity);
		
		x += velocity.x * delta;
		y += velocity.y * delta;
		
		if(x > Core.roomSize)
		{
			x = Core.roomSize;
			velocity.x *= -1;
		}
		else if(x < 0)
		{
			x = 0;
			velocity.x *= -1;
		}
		else if(y > Core.roomSize)
		{
			y = Core.roomSize;
			velocity.y *= -1;
		}
		else if(y < 0)
		{
			y = 0;
			velocity.y *= -1;
		}
		
		Rectangle rect = Core.gridHash.get(""+	(int)(MathUtils.ceil(x / Core.gridSize) * Core.gridSize - Core.gridSize / 2) 
												+ ","+
												(int)(MathUtils.ceil(y / Core.gridSize) * Core.gridSize - Core.gridSize / 2));
		
		if(rect != null)
		{
			if(foodToCarry != null)
			{
				follow();
				rect.trail = Trail.BACK;
			}
			else if(foodToCarry == null && rect.trail == Trail.NONE)
			{
				rect.trail = Trail.EXPLORE;
			}
			
			if(foodToCarry == null)
			{				
				path.add(rect);
			}
		}
	}

	@Override
	public void draw()
	{
		Core.spriteBatch.draw(	sprite,
								x - sprite.getWidth() / 2,
								y - sprite.getHeight() / 2,
								sprite.getWidth() / 2,
								sprite.getHeight() / 2,
								sprite.getWidth(),
								sprite.getHeight(),
								sprite.getScaleX(),
								sprite.getScaleY(),
								rotation - 90);
	}

	
	
}
