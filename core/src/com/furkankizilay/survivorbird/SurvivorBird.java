package com.furkankizilay.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background ;
	Texture bird ;
	Texture bee1 ;
	Texture bee2 ;
	Texture bee3 ;
	float birdX = 0 ;
	float birdY = 0 ;
	int gameState = 0 ;
	float velocity = 0 ;
	float gravity = 0.7f ;
	int numberOfEnemies = 4 ;
	float [] enemyX = new float[numberOfEnemies] ;
	float distance = 0 ;
	float enemyVelocity = 10 ;
	float [] enemyOffSet1 = new float[numberOfEnemies] ;
	float [] enemyOffSet2 = new float[numberOfEnemies] ;
	float [] enemyOffSet3 = new float[numberOfEnemies] ;
	Random random ;
	Circle birdCircle ;
	Circle[] enemyCircle1 ;
	Circle[] enemyCircle2 ;
	Circle[] enemyCircle3 ;
	ShapeRenderer shapeRenderer ;
	int score = 0 ;
	int scoredEnemy = 0 ;
	BitmapFont font ;
	BitmapFont font2 ;


	@Override
	public void create () {
		// onCreate
		batch = new SpriteBatch() ;

		background = new Texture("background.png") ;
		bird = new Texture("bird.png") ;
		bee1 = new Texture("bee.png") ;
		bee2 = new Texture("bee.png") ;
		bee3 = new Texture("bee.png") ;

		birdX = Gdx.graphics.getWidth() / 4 ;
		birdY = Gdx.graphics.getHeight() / 2 ;

		distance = Gdx.graphics.getWidth() / 2 ; // 2 arı arasında ekranın yarısı kadar bir fark olsun
		random = new Random() ;

		birdCircle = new Circle() ;
		enemyCircle1 = new Circle[numberOfEnemies] ;
		enemyCircle2 = new Circle[numberOfEnemies] ;
		enemyCircle3 = new Circle[numberOfEnemies] ;

		shapeRenderer = new ShapeRenderer() ;

		font = new BitmapFont() ;
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont() ;
		font2.setColor(Color.RED);
		font2.getData().setScale(6);

		for (int i = 0 ; i < numberOfEnemies ; i++) {
			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance ; // her seferinde seriler arasında distance kadar fark olsun

			enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;

			enemyCircle1[i] = new Circle() ;
			enemyCircle2[i] = new Circle() ;
			enemyCircle3[i] = new Circle() ;
		}

	}

	@Override
	public void render () {
		// oyun devam ettiğinde devamlı çağırılan metot

		batch.begin();
		// begin ve end arasına ne çiziceğimizi yazıcaz

		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 4 ) {
				score++ ;
				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++ ;
				}else {
					scoredEnemy = 0 ;
				}
			}

			if (Gdx.input.justTouched()) {
				velocity = -15 ;
			}

			for (int i = 0 ; i < numberOfEnemies ; i++) {

				if (enemyX[i] < 0) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance ;
					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;
				}else {
					enemyX[i] = enemyX[i] - enemyVelocity ;
				}

				batch.draw(bee1,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet1[i],Gdx.graphics.getWidth() / 15 , Gdx.graphics.getHeight() / 10);
				batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 15 , Gdx.graphics.getHeight() / 10);
				batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 15 , Gdx.graphics.getHeight() / 10);

				enemyCircle1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30) ;
				enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30) ;
				enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30) ;

			}


			if (birdY > 0) {
				velocity = velocity + gravity ;
				birdY = birdY - velocity ;
			}else {
				gameState = 2 ;
			}


		}else if (gameState == 0){
			if (Gdx.input.justTouched()) {
				gameState = 1 ;
			}
		}else if (gameState == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again!",250,Gdx.graphics.getHeight() / 2) ;

			if (Gdx.input.justTouched()) {
				gameState = 1 ;
				birdY = Gdx.graphics.getHeight() / 2 ;
				for (int i = 0 ; i < numberOfEnemies ; i++) {
					enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance ; // her seferinde seriler arasında distance kadar fark olsun

					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200) ;

					enemyCircle1[i] = new Circle() ;
					enemyCircle2[i] = new Circle() ;
					enemyCircle3[i] = new Circle() ;
				}

				velocity = 0 ;
				scoredEnemy = 0 ;
				score = 0 ;

			}
		}



		batch.draw(bird,birdX , birdY, Gdx.graphics.getWidth() / 15 , Gdx.graphics.getHeight() / 10 );

		font.draw(batch,String.valueOf(score),100,200) ;

		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30,birdY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);*/

		for (int i = 0 ; i < numberOfEnemies ; i++) {
			/*
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30 ,Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			*/

			if (Intersector.overlaps(birdCircle,enemyCircle1[i]) || Intersector.overlaps(birdCircle,enemyCircle2[i]) || Intersector.overlaps(birdCircle,enemyCircle3[i])) {
				gameState = 2 ;
			}

		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
