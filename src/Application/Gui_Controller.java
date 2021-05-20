package Application;

import java.awt.Color;
import java.util.List;

import javax.swing.JComboBox;

import Domain.CardData;

public class Gui_Controller {
	
	
	//Exam
	
	private Boolean isChecked = false;
	private List<CardData> cardQueue;
	private int queueCounter = 0;
	private CardData displayedCard;
	private List<String> KategoriesNames;
	private JsonDataManager dataManager;
	
	private VocabelDisplayer gui;
	private InputCorrectnessChecker correctnessChecker;
	
	public Gui_Controller(VocabelDisplayer pGui){
		gui = pGui;
		dataManager = new JsonDataManager();
	}
	
	//---------------------Temporary------------------------
	

	public CardData getDisplayedCard() {
		return displayedCard;
	}

	public JsonDataManager getDataManager() {
		return dataManager;
	}
	
	//------------------------------------------------------
	
	
	public void submit_Input(String backpageText) {
		if(!isChecked) {
			correctnessChecker.checkifCorrectExam(backpageText);
			isChecked = true;
		}else {
			submittInputAs(false);
		}
	}
	
	public void submittInputAs(Boolean isCorrect) {
		dataManager.changePhaseOfCardDependingIf(displayedCard,isCorrect);
		displayNextCard();
		gui.setBtn_exam_Correkt(false);
	}
	
	public void addCardWith(String frontpane, String backpane, String selectedKategorie) {
		dataManager.addCardWith(new CardData(frontpane, backpane, selectedKategorie ,1));
		gui.setFrontpane("");
		gui.setBackpane("");
	}
	
	public void addKategorie(JComboBox<String> comboBoxKategorie) {
		comboBoxKategorie.removeAllItems();
		dataManager.addKategorieNamed(gui.getAddKategorieText());
		fillKathegorieBoxes(comboBoxKategorie);
	}
		
	
	public void startQueue() {
		queueCounter = 0;
		displayNextCard();
		
	}
	
	public void updateCardView(JComboBox<String> comboBoxKategorieExam, JComboBox<String> comboBoxPhaseExam) {
		int phase = Integer.parseInt(comboBoxPhaseExam.getSelectedItem().toString());
		String kategorie =comboBoxKategorieExam.getSelectedItem().toString();
		cardQueue = dataManager.loadAllCardsOf( phase, kategorie);
		startQueue();
	}
	
	public void fillKathegorieBoxes( JComboBox<String> comboBoxKategorieLearn) {
		KategoriesNames = dataManager.getNamesOfAllKategories();
		for(String kategorie : KategoriesNames)
			comboBoxKategorieLearn.addItem(kategorie);
	}
	
	public void displayNextCard() {
		isChecked = false;
		gui.setBackpane("");
		gui.setBackpaneColor(Color.BLACK);
		gui.setBtn_exam_submitText("Überprüfen");
		try {
			displayedCard = this.cardQueue.get(queueCounter);
			gui.setFrontpane(displayedCard.getFrontSide());
			queueCounter++;
		}catch(IndexOutOfBoundsException ex) {
			gui.setFrontpane("Es giebt keine Karten mehr in der ausgewählten Phase, von dieser Kategorie");
		}
	}

	public void setCorrectnessChecker(InputCorrectnessChecker checker) {
		correctnessChecker=checker;
	}
}
