package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public final class Tile {
	static Texture tileSetTexture;
	static int tileWidth = 64;
	static int tileHeight = 64;
	static int tileStepX = 64;
	static int tileStepY = 16;
	static int oddRowXOffset = 32;
	static int heighTileOffset = 32;
    static int baseOffsetX = -32;
	static int baseOffsetY = -64;

	static float heightRowDepthMod = 0.0000001f;

    public static int getBaseOffsetX() {
        return baseOffsetX;
    }

    public static int getBaseOffsetY() {
        return baseOffsetY;
    }

    public static int getTileStepX() {
        return tileStepX;
    }

    public static int getTileStepY() {
        return tileStepY;
    }

    public static int getOddRowXOffset() {
        return oddRowXOffset;
    }

	public static int getTileWidth() {
		return tileWidth;
	}

	public static void setTileWidth(int tileWidth) {
		Tile.tileWidth = tileWidth;
	}

	public static int getTileHeight() {
		return tileHeight;
	}

	public static void setTileHeight(int tileHeight) {
		Tile.tileHeight = tileHeight;
	}

	public static int getHeighTileOffset() {
		return heighTileOffset;
	}

	public static Rectangle getSourceRectangle(int tileIndex) {
		int tileY = tileIndex / (tileSetTexture.getWidth() / tileWidth);
		int tileX = tileIndex % (tileSetTexture.getWidth() / tileWidth);

		return new Rectangle(tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight);

		/** Vieille itération */
		//return new Rectangle(tileIndex * TILESIZE, 0, TILESIZE, TILESIZE);

	}

	public static Texture getTileSetTexture() {
		return tileSetTexture;
	}

	public static void setTileSetTexture(Texture tileSetTexture) {
		Tile.tileSetTexture = tileSetTexture;
	}

    public static float getHeightRowDepthMod() {
        return heightRowDepthMod;
    }
}
