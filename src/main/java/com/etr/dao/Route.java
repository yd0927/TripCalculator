package com.etr.dao;

public class Route {
	private int toId;
	private float distance;

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return toId + " " + distance;
	}
}