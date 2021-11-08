package com.etr.connectors;

import java.util.Map;

import com.etr.dao.Interchange;
import com.etr.dao.Locations;

public class InterchangeManager {
	private static InterchangeManager interchangeManager = new InterchangeManager();

	private InterchangeManager() {

	}

	public static InterchangeManager getInstance() {
		return interchangeManager;
	}

	public static Interchange getInterchangeByName(Locations locations, String name) {
		for (Map.Entry<String, Interchange> entry : locations.getLocations().entrySet()) {
			if (entry.getValue().getName().equals(name)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public static Interchange getInterchangeById(Locations locations, String id) {
		return locations.getLocations().get(id);
	}
}
