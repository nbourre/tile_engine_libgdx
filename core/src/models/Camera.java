package models;

import com.badlogic.gdx.math.Vector2;

public final class Camera {
	static Vector2 location = Vector2.Zero;

	// Position en "pixel"
	public static Vector2 getLocation() {
		return location;
	}
	
	public static void setLocation(float x, float y) {
		location.x = x;
		location.y = y;
	}
}
