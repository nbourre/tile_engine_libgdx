package models;

import java.util.ArrayList;

public class MapCell {

	private int tileID;

	ArrayList<TileComplex> baseTiles = new ArrayList<TileComplex>();
	ArrayList<TileComplex> heightTiles = new ArrayList<TileComplex>();


	public int getTileID() {
		return tileID;
	}

	public void setTileID(int tileID) {
		baseTiles.add(new TileComplex(tileID));
	}

	public void addBaseTile(int tileID) {
		baseTiles.add(new TileComplex(tileID));
	}

	public MapCell(int tileID) {
		super();
		addBaseTile(tileID);
	}

	public void addHeightTile (int tileID) {
	    heightTiles.add(new TileComplex(tileID));
    }

	public ArrayList<TileComplex> getBaseTiles() {
		return baseTiles;
	}

	public ArrayList<TileComplex> getHeightTiles() {
		return heightTiles;
	}
}
