package com.github.annasajkh;

public abstract class GameObject
{
	float x, y;
	
	public GameObject(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public abstract void update(float delta);
	
	public abstract void draw();
	

}
