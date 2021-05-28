package com.github.annasajkh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Core extends ApplicationAdapter
{
	
	public static AntHome antHome;
	public static Rectangle[][] grid;
	public static float gridSize = 15;
	
	public static ShapeRenderer shapeRenderer;
	public static SpriteBatch spriteBatch;
	public static Sprite antSprite;
	public static OrthographicCamera camera;
	public static float zoomSpeed = 1;
	public static float zoomFactor = 0.1f;
	public static float maxZoom = 100f;
	public static float roomSize = 3000;
	public static int antNum = 100;
	public static Ant AntToFocus;
	public static Vector3 mousePos;
	public static float antVisionRadius = 100;
	
	public static Ant[] ants;
	public static List<Food> foods;
	public static HashMap<String,Rectangle> gridHash;
	
	@Override
	public void create()
	{
		antHome = new AntHome(roomSize / 2, roomSize / 2, 100);
		
		grid = new Rectangle[(int)(roomSize / gridSize)][(int)(roomSize / gridSize)];
		gridHash = new HashMap<>();
		
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[i].length; j++)
			{
				grid[i][j] = new Rectangle(j * gridSize + gridSize / 2, i * gridSize + gridSize / 2, gridSize, gridSize);

				gridHash.put(""+(int)(grid[i][j].x) + ","+(int)(grid[i][j].y),grid[i][j]);
			}
		}
		
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		antSprite = new Sprite(new Texture(Gdx.files.internal("ant.png")));
		ants = new Ant[antNum];
		
		for(int i = 0; i < ants.length; i++)
		{
			ants[i] = new Ant(antHome.x , antHome.y, antSprite);
			ants[i].velocity = new Vector2(1,0).rotateDeg(MathUtils.random(360));
		}
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.position.x = antHome.x;
		camera.position.y = antHome.y;
		camera.zoom = 5;
		camera.update();
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new InputManager());
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		foods = new ArrayList<>();
	}
	
	public void update(float delta)
	{
		mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		mousePos = camera.unproject(mousePos);
		
		for(Rectangle[] rectangles : grid)
		{
			for(Rectangle rectangle : rectangles)
			{
				rectangle.update(delta);
			}
		}
		
		for(Ant ant : ants)
		{
			ant.update(delta);
		}
		
		for(Food food : foods)
		{
			food.update(delta);
		}
		
		if(AntToFocus != null)
		{
			camera.position.x = AntToFocus.x;
			camera.position.y = AntToFocus.y;
			camera.update();
		}
		
		
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			create();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.R))
		{
			AntToFocus = ants[MathUtils.random(ants.length - 1)];
		}
		
		if(Gdx.input.isButtonPressed(1))
		{
			foods.add(new Food(mousePos.x, mousePos.y));
		}
	}
	
	@Override
	public void render()
	{
		update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		for(Rectangle[] rectangles : grid)
		{
			for(Rectangle rectangle : rectangles)
			{
				rectangle.draw();
			}
		}
		shapeRenderer.end();
		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for(Ant ant : ants)
		{			
			ant.draw();
		}
		spriteBatch.end();
		
		shapeRenderer.begin(ShapeType.Filled);
		
		for(Food food : foods)
		{
			food.draw();
		}
		antHome.draw();
		
		shapeRenderer.end();
	}

	@Override
	public void dispose()
	{
		spriteBatch.dispose();
		shapeRenderer.dispose();
	}
}
