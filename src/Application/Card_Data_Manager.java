package Application;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import Domain.CardData;

public class Card_Data_Manager {
	
	private JsonDataManager jsonManager;
	private String storageURL;
	private IDCounter_Manager idCounterManager;

	
	public Card_Data_Manager(String dataFilepath){
		storageURL = dataFilepath;
		jsonManager = new JsonDataManager();
		idCounterManager = new IDCounter_Manager(jsonManager, jsonManager.getAllKategorieDataFiles(storageURL));
	}
	
	public void addCardWith(CardData cardData) {
		int idCounter = idCounterManager.getCounter();
		File kategorieFile = jsonManager.getSpecificKategorieFile(cardData.getTopic(), storageURL);
		jsonManager.addCardWith(cardData, kategorieFile, idCounter);
		idCounterManager.incrementIDCounter();
		
	}
	
	public List<CardData> loadAllCardsOf(int phase, String kategorie) {
		File cardsDataFile = jsonManager.getSpecificKategorieFile(kategorie,storageURL);
		List<CardData> allCards = jsonManager.extractCardDataObjectsFrom(cardsDataFile);
		List<CardData> phaseCards = new ArrayList<CardData>();
		for(CardData card:allCards) {
			if(card.getPhase()==phase)
				phaseCards.add(card);
		}
		return phaseCards;
	}
	
	public void changePhaseOfCardDependingIf(CardData displeayedCard, boolean isCorrect){
		File kategorieFile = jsonManager.getSpecificKategorieFile(displeayedCard.getTopic(), storageURL);
		List<CardData> allCards = jsonManager.extractCardDataObjectsFrom(kategorieFile);
		String currentContentOfNewFile = "{\"Cards\":[]}";
		
		for(CardData card : allCards) {
			if(card.getID() == displeayedCard.getID()) {
				if(isCorrect) {
					card.incrementPhase();
				}else {
					card.resetPhase();
				}
			}
			JSONObject obj = new JSONObject();
		    String jsonText;
		    obj.put("Frontside", card.getFrontSide());
		    obj.put("Backside", card.getBackSide());
		    obj.put("Learningphase", card.getPhase());
		    obj.put("Id", card.getID());
		    jsonText = obj.toString();
		    currentContentOfNewFile = jsonManager.addContentToFilesContent(currentContentOfNewFile, jsonText);
		}
		jsonManager.replaceFileContentWith(kategorieFile.getAbsolutePath(), currentContentOfNewFile);
	}
	
	public List<String> getNamesOfAllKategories() {
		File[] JsonDatafiles = jsonManager.getAllKategorieDataFiles(storageURL);
		List<String> DataFilesNames = new ArrayList<String>();
		for(File file : JsonDatafiles) {
			String[] filename= file.getName().split("\\.");
			if(filename[1].equals("json")) {
				String filenameWithoutDataType = filename[0];
				DataFilesNames.add(filenameWithoutDataType);
			}
		}
		return DataFilesNames;
	}
	
	public void addKategorieNamed(String name) {
		File newJSONfile = new File (storageURL, (""+name+".json"));
		try(FileOutputStream fStream = new FileOutputStream(newJSONfile);
			    DataOutputStream data0 = new DataOutputStream(fStream)) {
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (FileWriter fw = new FileWriter(newJSONfile);
			       BufferedWriter bw = new BufferedWriter(fw)){
			bw.write("{\"Cards\":[]}");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
