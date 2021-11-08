package com.etr.dao;

import java.util.Map;
import java.util.Map.Entry;

import com.etr.connectors.InterchangeManager;

public class Locations {
	private Map<String, Interchange> locations;

	public Map<String, Interchange> getLocations() {
		return locations;
	}

	public void setLocations(Map<String, Interchange> locations) {
		this.locations = locations;
	}

	/**
	 * Returns the distance between two adjacent interchanges
	 * 
	 * @param interchange1
	 * @param interchange2
	 * @return the distance between two adjacent interchanges
	 */
	public float getDistance(Interchange interchange1, Interchange interchange2) {
		if (interchange1 == null || interchange2 == null) {
			return 0f;
		}

		for (Entry<String, Interchange> entry : locations.entrySet()) {
			Interchange interchange = entry.getValue();
			if (interchange.getName().equals(interchange1.getName())) {
				for (Route route : interchange.getRoutes()) {
					Interchange target = InterchangeManager.getInstance().getInterchangeById(this,
							String.valueOf(route.getToId()));
					if (target != null && target.getName().equals(interchange2.getName())) {
						return route.getDistance();
					}
				}
			}
		}
		return 0f;
	}
}
