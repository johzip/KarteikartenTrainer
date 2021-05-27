package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Application.*;

public class InputCorrectnessCheckerTest {
	
	GuiGetterSetter mockGui;
	Gui_Controller mockController;
	InputCorrectnessChecker checker = new InputCorrectnessChecker(mockGui, mockController);
	
	String correctInput = "right";
	String wrongInput = "wrong";
	
	@Test
	void checkifCorrectTest(){
		assertFalse(checker.checkIfCorrect(wrongInput, correctInput));
		assertTrue(checker.checkIfCorrect(correctInput, correctInput));
	}
}
