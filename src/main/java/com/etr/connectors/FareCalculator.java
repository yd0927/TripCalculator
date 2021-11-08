package com.etr.connectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.etr.dao.Interchange;
import com.etr.dao.Locations;
import com.etr.dao.Route;
import com.etr.exceptions.InvalidInterchangeNameException;

public class FareCalculator {
	private static final float RATE = 0.25f;

	private Locations locations;
	private Set<Interchange> settled;
	private Set<Interchange> unsettled;
	private Map<Interchange, Interchange> predecessors;
	private Map<Interchange, Float> distances;

	public FareCalculator(Locations locations) {
		this.locations = locations;
	}

	private void init(Interchange departure) {
		settled = new HashSet<>();
		unsettled = new HashSet<>();
		predecessors = new HashMap<>();
		distances = new HashMap<>();

		distances.put(departure, 0f);
		unsettled.add(departure);
		while (!unsettled.isEmpty()) {
			Interchange interchange = getMinimum(unsettled);
			settled.add(interchange);
			unsettled.remove(interchange);
			findMinDistance(interchange);
		}
	}

	private void findMinDistance(Interchange interchange) {
		List<Interchange> adjacents = getAdjacentInterchanges(interchange);

		// Finds the minimum distance from the interchange to the adjacent
		// interchanges
		for (Interchange target : adjacents) {
			float distance = getShortestDistance(interchange) + locations.getDistance(interchange, target);
			if (getShortestDistance(target) > distance) {
				distances.put(target, distance);
				predecessors.put(target, interchange);
				unsettled.add(target);
			}
		}
	}

	private List<Interchange> getAdjacentInterchanges(Interchange interchange) {
		List<Route> adjacents = interchange.getRoutes();

		// Gets the adjacent interchanges and returns a list of the adjacent
		// interchanges
		List<Interchange> adjacentInterchanges = new ArrayList<>();
		for (Route route : adjacents) {
			Interchange adjacent = InterchangeManager.getInstance().getInterchangeById(locations,
					String.valueOf(route.getToId()));
			if (adjacent != null) {
				adjacentInterchanges.add(adjacent);
			}
		}
		return adjacentInterchanges;
	}

	private Interchange getMinimum(Set<Interchange> vertexes) {
		Interchange min = null;
		
		for (Interchange vertex : vertexes) {
			min = min == null || getShortestDistance(vertex) < getShortestDistance(min) ? vertex : min;
		}
		return min;
	}

	private float getShortestDistance(Interchange destination) {
		Float d = distances.get(destination);
		return d == null ? Float.MAX_VALUE : d;
	}

	/**
	 * Returns the distance between two interchanges
	 * 
	 * @param srcName
	 * @param trgName
	 * @return the distance between two interchanges
	 * @throws InvalidInterchangeNameException
	 */
	public float getDistance(String srcName, String trgName) throws InvalidInterchangeNameException {
		float distance = 0f;

		// Gets the interchanges for the given departure name and the
		// destination name
		LinkedList<Interchange> path = new LinkedList<>();
		Interchange source = InterchangeManager.getInstance().getInterchangeByName(locations, srcName);
		Interchange target = InterchangeManager.getInstance().getInterchangeByName(locations, trgName);

		if (source == null) {
			throw new InvalidInterchangeNameException(srcName);
		}

		if (target == null) {
			throw new InvalidInterchangeNameException(trgName);
		}

		// Initializes the variables
		init(source);
		Interchange step = target;

		if (predecessors.get(step) == null) {
			return distance;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}

		Interchange cur = path.get(0);
		for (int i = 1; i < path.size(); i++) {
			Interchange next = path.get(i);
			distance += locations.getDistance(cur, next);
			cur = next;
		}
		return distance;
	}

	/**
	 * Returns the fare to pay based on the given distance
	 * 
	 * @param distance
	 * @return the amount of fare to pay
	 */
	public float getFare(float distance) {
		return distance * RATE;
	}
}
