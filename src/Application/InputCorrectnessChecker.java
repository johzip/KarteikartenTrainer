package Application;

import java.awt.Color;

public class InputCorrectnessChecker {
	private GuiGetterSetter gui;
	private Gui_Controller gui_controller;
	
	public InputCorrectnessChecker(GuiGetterSetter vokabeltrainerGUI, Gui_Controller controller) {
		gui=vokabeltrainerGUI;
		gui_controller = controller;
	}

	public void checkifCorrectLearn(String backpage) {
		String correctBacksideOfShownCard = gui_controller.getDisplayedCard().getBackSide();
		if(checkIfCorrect(backpage, correctBacksideOfShownCard)) {
			gui.setBackpaneColor(Color.green);
		}else {
			gui.setBackpaneColor(Color.red);
			gui.setBackpane(correctBacksideOfShownCard);
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
		return input.equals(correctAnswer);
	}
}
