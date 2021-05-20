package Application;

import java.awt.Color;

public class InputCorrectnessChecker {
	private VocabelDisplayer gui;
	private Gui_Controller gui_controller;
	
	public InputCorrectnessChecker(VocabelDisplayer vokabeltrainerGUI, Gui_Controller controller) {
		gui=vokabeltrainerGUI;
		gui_controller = controller;
	}

	public void checkifCorrectLearn(String backpage) {
		if(checkIfCorrect(backpage, gui_controller.getDisplayedCard().getBackSide())) {
			gui.setBackpaneColor(Color.green);
		}else {
			gui.setBackpaneColor(Color.red);
		}
	}
	
	public void checkifCorrectExam(String backpage) {
		if(checkIfCorrect(backpage, gui_controller.getDisplayedCard().getBackSide())) {
			gui.setBackpaneColor(Color.green);
			gui_controller.getDataManager().changePhaseOfCardDependingIf(gui_controller.getDisplayedCard(),true);
		}else {
			gui.setBackpaneColor(Color.red);
			gui.setBtn_exam_Correkt(true);
		}
		gui.setBtn_exam_submitText("Nächstes");
	}
	
	public boolean checkIfCorrect(String input, String correctAnswer) {
		if(input.equals(correctAnswer))
			return true;
		return false;
	}
}
