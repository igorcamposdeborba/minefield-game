package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.ExplosionException;

public class CampoTest {

	private Campo camp;
	
	@BeforeEach
	void initializeCamp() { // resetar Campo antes de cada teste
		camp = new Campo(3, 3);
	}
	
	@Test
	void safeActualField() {
		Assertions.assertFalse(camp.isFlagged()); // testar se valores padrão estão setados corretamente
		
		camp.changeFlag(); // testar mudança de flag que indica se pode ter uma mina no field
		Assertions.assertTrue(camp.isFlagged());
		
		camp.changeFlag(); // testar se a flag volta a ser false se o usuário retirar a flag
		Assertions.assertFalse(camp.isFlagged()); 
	}
	
	@Test
	void testSafeNeighbor1NextTo() { // testar se vizinhos que deveriam estar sem mina realmente estão seguros
		Campo neighborLeft = new Campo(3, 2);
		boolean result = camp.addNeighbor(neighborLeft);
		Assertions.assertTrue(result);
	
		Campo neighborAbove = new Campo(2, 3);
		result = camp.addNeighbor(neighborAbove);
		Assertions.assertTrue(result);
	}
	
	@Test
	void testSafeNeighbor2Diagonal() {
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
	
	@Test
	void openFieldSafe_WhenAreFalseFlagAndFieldOpened_ThenReturnFieldSafeIsTrue() throws ExplosionException {
		Assertions.assertTrue(camp.open());
	}
	
	@Test
	void notOpenField_WhenFlagIsTrueInField_ThenReturnFalse() throws ExplosionException {
		camp.changeFlag();
		Assertions.assertFalse(camp.open());
	}
	
	@Test
	void notOpenField_WhenAreTrueMinedFieldAndFlaggedField() throws ExplosionException {
		camp.changeFlag();
		camp.mine();
		Assertions.assertTrue(camp.isMined());
	}
	
	@Test
	void minedField_WhenFieldHasNotFlag_ThenThrowsExplosionException() throws ExplosionException {
		camp.mine();
		Assertions.assertThrows(ExplosionException.class, () -> camp.open(), "Review the parameters and logic of class");
	}
	
	@Test
	void openFieldsSafeToTestRecursion_WhenThereAreNeighborSafe_ThenReturnTrue() throws ExplosionException {
		Campo camp1_1 = new Campo(1, 1);
		Campo camp2_2 = new Campo(2, 2);

		camp2_2.addNeighbor(camp1_1); // adicionar vizinho do campo 1
		
		camp.addNeighbor(camp2_2); // adicionar vizinho no campo atual
		camp.open(); // abrir campos para ver se abre o campo 1, 2 e o atual
		
		Assertions.assertTrue(camp2_2.isOpened() && camp1_1.isOpened());
	}
	
	@Test
	void notOpenFieldsUnsafeToTestRecursion_WhenThereAreNeighborUnsafe_ThenReturnFalse() throws ExplosionException {
		Campo camp1_1 = new Campo(1, 1);
		Campo camp1_2 = new Campo(1, 2);
		camp1_2.mine();
		
		Campo camp2_2 = new Campo(2, 2);

		camp2_2.addNeighbor(camp1_1); // adicionar vizinho do campo 1
		camp2_2.addNeighbor(camp1_2);
		
		camp.addNeighbor(camp2_2); // adicionar vizinho no campo atual
		camp.open(); // abrir campos para ver se abre o campo 1, 2 e o atual
		
		Assertions.assertTrue(camp2_2.isOpened() == true && camp1_1.isOpened() == false);
	}
}
