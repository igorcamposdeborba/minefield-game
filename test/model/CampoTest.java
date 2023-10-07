package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CampoTest {

	private Campo camp;
	
	@BeforeEach
	void initializeCamp() {
		camp = new Campo(3, 3);
	}
	@Test
	void testNeighbor1NextTo() {
		Campo neighborLeft = new Campo(3, 2);
		boolean result = camp.addNeighbor(neighborLeft);
		Assertions.assertTrue(result);
	
		Campo neighborAbove = new Campo(2, 3);
		result = camp.addNeighbor(neighborAbove);
		Assertions.assertTrue(result);
	}
	
	@Test
	void testNeighbor2Diagonal() {
		Campo neighborDiagonal = new Campo(2, 2);
		boolean result = camp.addNeighbor(neighborDiagonal);
		Assertions.assertTrue(result);
	}

	@Test
	void testNotNeighbor() {
		Campo notNeighbor = new Campo(1, 1);
		boolean result = camp.addNeighbor(notNeighbor);
		Assertions.assertFalse(result);
	}
}
