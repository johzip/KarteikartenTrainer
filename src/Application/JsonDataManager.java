package Application;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Domain.CardData;

public class JsonDataManager {
	
	private String storageURL = System.getProperty("user.dir") + "\\JSON_Data" ;
	private static int idCounter;
	
	public JsonDataManager(){
		idCounter=getIDCounterValue();
	}
	
	public void addCardWith(CardData cardData){
		try {
			File kategorieFile = getSpecificKategorieFile(cardData.getTopic());
			JSONObject obj = new JSONObject();
		    String jsonText;
		    obj.put("Frontside", cardData.getFrontSide());
		    obj.put("Backside", cardData.getBackSide());
		    obj.put("Learningphase", new Integer(1));
		    obj.put("Id", idCounter+1);
		    jsonText = obj.toString();
			String newFileConent = addContentToFilesContent(getFileContentOf(kategorieFile), jsonText);
		    replaceJSONFileContentWith(kategorieFile.getAbsolutePath(), newFileConent);
		    incrementIDCounter();
		}
		catch(NullPointerException e2) {
			System.out.println("addCard has Error");
			System.out.println(e2);
		}
		catch(Exception e3) {
			System.out.println("addCard has Error");
			System.out.println(e3); 
		}
	}
	
	private void incrementIDCounter() {
		idCounter++;
		File idCounterFile = getFileOfCounter();
		replaceJSONFileContentWith(idCounterFile.getAbsolutePath(),""+idCounter);
	}
	private File getFileOfCounter() {
		File[] dataFiles = getAllKategorieDataFiles();
		for(File datafile : dataFiles) {
			if(datafile.getName().equals("counter.txt")) {
				return datafile;
			}
		}
		return null;
	}
	private int getIDCounterValue() {
		File idCounterFile= getFileOfCounter();
		try {
			String contentCounterFile = getFileContentOf(idCounterFile);
			return Integer.parseInt(contentCounterFile);
			
		} catch (IOException e) {
			System.out.println("getIDCounter has Error");
			e.printStackTrace();
			return 0;
		}
	}
	
	public void changePhaseOfCardDependingIf(CardData displeayedCard, boolean isCorrect){
		File kategorieFile = this.getSpecificKategorieFile(displeayedCard.getTopic());
		List<CardData> allCards = extractCardDataObjectsFrom(kategorieFile);
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
		    currentContentOfNewFile = addContentToFilesContent(currentContentOfNewFile, jsonText);
		}
		replaceJSONFileContentWith(kategorieFile.getAbsolutePath(), currentContentOfNewFile);
	}
	
	private String addContentToFilesContent(String existingFileContent, String jsonTextToAdd) {
		existingFileContent = existingFileContent.trim();
		int positionWhereToAdd = existingFileContent.indexOf("[") + 1;
		String cobinedJSONString = existingFileContent.substring(0, positionWhereToAdd) + jsonTextToAdd;
		if( !isFirstCard(existingFileContent.substring(positionWhereToAdd)+1)) {
			cobinedJSONString= cobinedJSONString + "," ;
		}
		return (cobinedJSONString + existingFileContent.substring(positionWhereToAdd));
	}
	
	private boolean isFirstCard(String JSONString) {
		String firstChar = JSONString.substring(0, 1);
		if(firstChar.equals("]"))
			return true;
		return false;
	}


	private void replaceJSONFileContentWith(String absolutePath, String jsonText){
		try (FileWriter fw = new FileWriter(absolutePath, false);
	       BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(jsonText);
			bw.newLine();   
			bw.close();
		}catch (IOException e1) {
			System.out.println("addJsonEntry has Error");
			e1.printStackTrace();
		}
	}


	private String getFileContentOf(File kategorieFile) throws IOException {
		Path path = Paths.get(kategorieFile.getAbsolutePath());
		return String.join("\n", Files.readAllLines(path));
	}

	
	private File getSpecificKategorieFile(String cardKategorie) {
		File[] datafiles = getAllKategorieDataFiles();
		File kategorieFile = null;
		for(File datafile : datafiles) {
			if(datafile.getName().equals(cardKategorie+".json")) {
				kategorieFile = datafile;
			}
		}
		return kategorieFile;
	}
	
	
	public List<String> getNamesOfAllKategories() {
		File[] JsonDatafiles = getAllKategorieDataFiles();
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

	
	private File[] getAllKategorieDataFiles() {
		File JsonDataDirectory = new File(storageURL);
		File[] JsonDatafiles = JsonDataDirectory.listFiles();
		return JsonDatafiles;
	}

	
	public List<CardData> loadAllCardsOf(int phase, String kategorie) {
		File cardsDataFile = getSpecificKategorieFile(kategorie);
		List<CardData> allCards = extractCardDataObjectsFrom(cardsDataFile);
		List<CardData> phaseCards = new ArrayList<CardData>();
		for(CardData card:allCards) {
			if(card.getPhase()==phase)
				phaseCards.add(card);
		}
		return phaseCards;
	}


	private List<CardData> extractCardDataObjectsFrom(File cardsDataFile) {
		try {
			JSONObject dataObject = parseIntoJSONObject(getFileContentOf(cardsDataFile));
			JSONArray JasonCards = (JSONArray) dataObject.get("Cards");
			List<CardData> cards = new ArrayList<CardData>();
			for(int cardNr=0; cardNr<JasonCards.size(); cardNr++) {
				JSONObject obj = (JSONObject) JasonCards.get(cardNr);
				String frontSideInput = obj.get("Frontside").toString();
				String backSideInput = obj.get("Backside").toString();
				int id = Integer.parseInt(obj.get("Id").toString());
				String topic = cardsDataFile.getName().split(".")[0];
				int learningphase = Integer.parseInt(obj.get("Learningphase").toString());
				CardData card =new CardData(frontSideInput, backSideInput, topic, learningphase, id);
				cards.add(card);
			}
			return cards;
		} catch (IOException e) {
			System.out.println("File of the Kategorie may not exist");
			e.printStackTrace();
		}
		return null;
	}
	
	
	private JSONObject parseIntoJSONObject(String fileContent) {
		try {
			JSONParser parser = new JSONParser();
			Object kategorieObject =  parser.parse(fileContent);
			return (JSONObject) kategorieObject;
		}
		catch(ParseException e) {
			System.out.println("parseJSONContent has Error");
			System.out.println("Parsing JSON contet from File files: "+e);
			return null;
		}
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
