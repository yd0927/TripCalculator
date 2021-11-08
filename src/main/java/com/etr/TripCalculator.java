package com.etr;

import java.util.Scanner;

import com.etr.connectors.FareCalculator;
import com.etr.connectors.FileManager;
import com.etr.dao.Locations;
import com.etr.exceptions.InvalidInterchangeNameException;

public class TripCalculator {
	public static final String FILENAME = "interchanges.json";

	public static void main(String[] args) {
		Locations locations = FileManager.getFileManager().getInterchangeData(FILENAME);

		if (locations != null) {
			FareCalculator calc = new FareCalculator(locations);

			Scanner scanner = new Scanner(System.in);
			String departure = "";
			String arrival = "";
			do {
				System.out.println("Enter a departure : ");
				departure = scanner.nextLine();

				System.out.println("Enter an arrival : ");
				arrival = scanner.nextLine();

				try {
					float distance = calc.getDistance(departure, arrival);
					System.out.println(String.format("Distance: %f km", distance));
					System.out.println(String.format("Cost: %.2f $", calc.getFare(distance)));
				} catch (InvalidInterchangeNameException e) {
					System.err.println(
							String.format("Could not find the distance between %s and %s%n", departure, arrival));
				}
			} while (!departure.equals("exit") && !arrival.equals("exit"));
			scanner.close();
			System.out.println("407 Trip Calculator is terminated.");
		}
	}
}
