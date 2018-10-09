package com.nicolasbourre.tileenginea18;

import models.Camera;
import models.Tile;
import models.TileComplex;
import models.TileMap;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class YATE extends ApplicationAdapter {
	SpriteBatch batch;
	TileMap map = new TileMap();

	int carreLargeur; // Largeur en tuile dans l'ecran
	int carreHauteur; // Hauteur en tuile dans l'ecran


	@Override
	public void create () {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		Tile.setTileSetTexture (new Texture("part4_tileset.png"));

		carreLargeur = Gdx.graphics.getWidth() / Tile.getTileStepX() + 2; // + 2 pour enlever le bug
		carreHauteur = Gdx.graphics.getHeight() / Tile.getTileStepY() + 2;

	}

	@Override
	public void render () {
		update();
		draw();
		debugging();

	}
    ArrayList<TileComplex> tileToRender;
	boolean camIsDirty = true;

	private void update() {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			camIsDirty = true;
			Camera.setLocation(MathUtils.clamp(
					Camera.getLocation().x - 2, 0, (map.getMapWidth() - carreLargeur) * Tile.getTileStepX()),
					Camera.getLocation().y);
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			camIsDirty = true;
			Camera.setLocation(MathUtils.clamp(
					Camera.getLocation().x + 2, 0, (map.getMapWidth() - carreLargeur) * Tile.getTileStepX()),
					Camera.getLocation().y);
		}

		if (Gdx.input.isKeyPressed(Keys.UP)) {
			camIsDirty = true;
			Camera.setLocation(Camera.getLocation().x,
					MathUtils.clamp(
							Camera.getLocation().y + 2, 0, (map.getMapHeight() - carreHauteur) * Tile.getTileStepY()));
		}

		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			camIsDirty = true;
			Camera.setLocation(Camera.getLocation().x,
					MathUtils.clamp(
							Camera.getLocation().y - 2, 0, (map.getMapHeight() - carreHauteur) * Tile.getTileStepY()));
		}

		if (camIsDirty) {
		    tileToRender = sortTiles();
        }
	}

    /**
     * Sort tiles by height
     */
	private ArrayList<TileComplex> sortTiles() {
        /**
         * Calculs pour le premier carr en haut  gauche
         * a afficher
         */
        ArrayList<TileComplex> result = new ArrayList<TileComplex>();

        Vector2 firstSquare = new Vector2(
                Camera.getLocation().x / (float)Tile.getTileStepX(),
                Camera.getLocation().y / (float)Tile.getTileStepY());
        int firstX = (int) firstSquare.x;
        int firstY = (int) firstSquare.y;

        float maxDepth = ((map.getMapWidth() + 1) + ((map.getMapHeight()+ 1) * Tile.getTileWidth())) * 10;
        float depthOffset;

         for (int y = 0; y < carreHauteur; y++) {
            for (int x = 0; x < carreLargeur; x++) {

                int mapX = (firstX + x);
                int mapY = (firstY + y);

                for (TileComplex tile : map.getRow(y + firstY).getCell(x + firstX).getBaseTiles()) {
                    tile.setTileHeight(1f);
                 }



                int heightRow = 0;
                depthOffset = 0.7f - ((mapX + (mapY * Tile.getTileWidth())) / maxDepth);

                for (TileComplex tile : map.getRow(mapY).getCell(mapX).getHeightTiles()) {
                    float zDepth = depthOffset - ((float)heightRow * Tile.getHeightRowDepthMod());
                    heightRow++;

                    tile.setTileHeight(zDepth);
                    result.add(tile);
                }

            }
        }
        Collections.sort(result);

        return result;
    }

	private void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/**
		 * Calculs pour le premier carr en haut  gauche
		 * a afficher
		 */
		Vector2 firstSquare = new Vector2(
				Camera.getLocation().x / (float)Tile.getTileStepX(),
				Camera.getLocation().y / (float)Tile.getTileStepY());
		int firstX = (int) firstSquare.x;
		int firstY = (int) firstSquare.y;

		/**
		 * Calcul pour le decalage pour la camera pour les cotes
		 */
		Vector2 squareOffset = new Vector2(
				Camera.getLocation().x % Tile.getTileStepX(),
				Camera.getLocation().y % Tile.getTileStepY());
		int offsetX = (int)squareOffset.x;
		int offsetY = (int)squareOffset.y;

		float maxDepth = ((map.getMapWidth() + 1) + ((map.getMapHeight()+ 1) * Tile.getTileWidth())) * 10;
		float depthOffset;

		batch.begin();

		for (int y = 0; y < carreHauteur; y++) {
			int positionY = (y * Tile.getTileHeight()) - offsetY;

			int rowOffset = 0;
			if ((firstY + y) % 2 == 1) {
				rowOffset = Tile.getOddRowXOffset();
			}

			for (int x = 0; x < carreLargeur; x++) {

				int mapX = (firstX + x);
				int mapY = (firstY + y);

                for (TileComplex tile : map.getRow(y + firstY).getCell(x + firstX).getBaseTiles()) {
                    // Va chercher le rectangle de la tuile à afficher
                    Rectangle srcRect = Tile.getSourceRectangle(tile.getTileId());
					batch.draw(Tile.getTileSetTexture(),
							(x * Tile.getTileStepX()) - offsetX + rowOffset + Tile.getBaseOffsetX(),
							(y * Tile.getTileStepY()) - offsetY + Tile.getBaseOffsetY(),
							(int)srcRect.x, (int)srcRect.y,
							(int)srcRect.width, (int)srcRect.height);

				}

				for (TileComplex tile : tileToRender) {

					// Va chercher le rectangle de la tuile à afficher
					Rectangle srcRect = Tile.getSourceRectangle(tile.getTileId());

					batch.draw(Tile.getTileSetTexture(),
							(x * Tile.getTileStepX()) - offsetX + rowOffset + Tile.getBaseOffsetX(),
							(y * Tile.getTileStepY()) - offsetY + Tile.getBaseOffsetY(),
							(int)srcRect.x, (int)srcRect.y,
							(int)srcRect.width, (int)srcRect.height);
				}

			}
		}

		batch.end();
	}

	void debugging() {
		if (camIsDirty) {
			Gdx.app.debug("CAMERA", Camera.getLocation().x + ", " + Camera.getLocation().y);
			camIsDirty = false;
		}

	}
}