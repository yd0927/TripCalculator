package com.etr.connectors;

import java.io.File;
import java.io.IOException;

import com.etr.dao.Locations;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileManager {
	private static FileManager fileManager = new FileManager();

	private FileManager() {
	}

	public static FileManager getFileManager() {
		return fileManager;
	}

	public static Locations getInterchangeData(String fileName) {
		Locations locations = null;
		try {
			// Read the json file
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String file = FileManager.class.getClassLoader().getResource(fileName).getFile();
			locations = mapper.readValue(new File(file), Locations.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return locations;
	}
}
