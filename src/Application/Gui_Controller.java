package Application;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Adapters.VokabeltrainerGUI;
import Domain.CardData;

public class Gui_Controller {
	
	
	//Exam
	
	private Boolean isChecked = false;
	

	private List<CardData> cardQueue;
	
	private int queueCounter = 0;
	private CardData displayedCard;
	
	private List<String> KategoriesNames;
	private JsonDataManager dataManager;

	private VokabeltrainerGUI gui;
	
	public Gui_Controller(VokabeltrainerGUI pGui){
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
	
	
	public void submit_Input() {
		if(!isChecked) {
			gui.checkifCorrectExam();
			isChecked = true;
		}else {
			submittInputAs(false);
		}
	}
	
	public void submittInputAs(Boolean isCorrect) {
		dataManager.changePhase(displayedCard,isCorrect);
		displayNextCard();
		gui.setBtn_exam_Correkt(false);
	}
	
	public void addCard(JComboBox<String> comboBoxKategorie) {
		dataManager.addCard(new CardData(gui.getFrontpane(), gui.getBackpane(), comboBoxKategorie.getSelectedItem().toString(),1));
		gui.setFrontpane("");
		gui.setBackpane("");
	}
	

	public void addKategorie(JComboBox<String> comboBoxKategorie) {
		dataManager.addKategorie(gui.getAddKategorieText());
		fillKathegorieBoxes(comboBoxKategorie);
	}
		
	
	public void startQueue() {
		queueCounter = 0;
		displayNextCard();
		
	}
	
	public void updateCardView(JComboBox<String> comboBoxKategorieExam, JComboBox<String> comboBoxPhaseExam) {
		int phase = Integer.parseInt(comboBoxPhaseExam.getSelectedItem().toString());
		String kategorie =comboBoxKategorieExam.getSelectedItem().toString();
		cardQueue = dataManager.loadCards( phase, kategorie);
		startQueue();
	}
	
	public void fillKathegorieBoxes( JComboBox<String> comboBoxKategorieLearn) {
		KategoriesNames = dataManager.getKategorie();
		for(String kategorie : KategoriesNames)
			comboBoxKategorieLearn.addItem(kategorie);
	}
	
	public void displayNextCard() {
		isChecked = false;
		gui.setBackpane("");
		gui.setBackpaneColor(Color.BLACK);
		try {
			displayedCard = this.cardQueue.get(queueCounter);
			gui.setFrontpane(displayedCard.getFrontSide());
			queueCounter++;
		}catch(IndexOutOfBoundsException ex) {
			gui.setFrontpane("Es giebt keine Karten mehr in der ausgewählten Phase, von dieser Kategorie");
		}
	}
}
