package com.puzdrowski.lightwarrior.utils;

import com.badlogic.gdx.math.Vector2;

public class Utils {

	public static boolean isPointInRangeOfPoint(Vector2 point1, Vector2 point2, float margin) {
		float x = point1.x - point2.x;
		float y =  point1.y - point2.y;
		return ((x * x) + (y * y)) < (margin * margin);
	}
	
	public static boolean isPointInRangeOfPointVertically(Vector2 point1, Vector2 point2, float margin) {
		float y =  point1.y - point2.y;
		System.out.println(y);
		return Math.abs(y) < margin;
	}
	
}
