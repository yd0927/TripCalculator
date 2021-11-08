import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.etr.connectors.FareCalculator;
import com.etr.connectors.FileManager;
import com.etr.dao.Locations;
import com.etr.exceptions.InvalidInterchangeNameException;

@DisplayName("TripCalculator Test Cases")
public class TripCalculatorTest {
	public static final String FILENAME = "interchanges.json";

	static Locations locations;
	static FareCalculator calc;

	@BeforeAll
	public static void setup() {
		locations = FileManager.getFileManager().getInterchangeData(FILENAME);
		if (locations != null) {
			calc = new FareCalculator(locations);
		}
	}

	@Test
	public void firstAdjacent() {
		try {
			float distance = calc.getDistance("QEW", "Dundas Street");
			assertEquals(6.062f, distance, 0.001);
			assertEquals(6.062f * 0.25, calc.getFare(distance), 0.001);
		} catch (InvalidInterchangeNameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void midAdjacent() {
		try {
			float distance = calc.getDistance("Derry Road", "Highway 401");
			assertEquals(2.507f, distance, 0.001);
			assertEquals(2.507f * 0.25, calc.getFare(distance), 0.001);
		} catch (InvalidInterchangeNameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void lastAdjacent() {
		try {
			float distance = calc.getDistance("Westney Road", "Salem Road");
			assertEquals(0.0f, distance, 0.0);
			assertEquals(0.0f * 0.25, calc.getFare(distance), 0.001);
		} catch (InvalidInterchangeNameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void firstToThird() {
		try {
			float distance = calc.getDistance("QEW", "Appleby Line");
			assertEquals(9.909f, distance, 0.001);
			assertEquals(9.909f * 0.25, calc.getFare(distance), 0.001);
		} catch (InvalidInterchangeNameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void fifthToSeventh() {
		try {
			float distance = calc.getDistance("Neyagawa Blvd.", "Highway 403");
			assertEquals(6.144f, distance, 0.001);
			assertEquals(6.144f * 0.25, calc.getFare(distance), 0.001);
		} catch (InvalidInterchangeNameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void secondToFirst() {
		try {
			float distance = calc.getDistance("Dundas Street", "QEW");
			assertEquals(6.062f, distance, 0.001);
			assertEquals(6.062f * 0.25, calc.getFare(distance), 0.001);
		} catch (InvalidInterchangeNameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void incorrectDeparture() {
		Assertions.assertThrows(InvalidInterchangeNameException.class, () -> {
			calc.getDistance("QEWW", "Appleby Line");
		});

	}

	@Test
	public void incorrectArrival() {
		Assertions.assertThrows(InvalidInterchangeNameException.class, () -> {
			calc.getDistance("QEW", "Appleby Lineee");
		});
	}

	@Test
	public void incorrectNames() {
		Assertions.assertThrows(InvalidInterchangeNameException.class, () -> {
			calc.getDistance("QEWW", "Appleby Lineee");
		});
	}
}
