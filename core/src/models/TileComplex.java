package models;

public class TileComplex implements Comparable<TileComplex> {

    int tileId;
    float tileHeight;

    public int getTileId() {
        return tileId;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public TileComplex (int tileId) {
        this.tileId = tileId;
        tileHeight = 0.7f; // Arbitrary value
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public void setTileHeight(float tileHeight) {
        this.tileHeight = tileHeight;
    }

    @Override
    public int compareTo(TileComplex otherTile) {
        return (int)(10000 * (this.tileHeight - otherTile.getTileHeight()));
    }
}
