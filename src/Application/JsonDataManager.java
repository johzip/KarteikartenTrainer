package Application;
//This class uses the Libary JSON.simple from https://code.google.com/archive/p/json-simple/downloads

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
		idCounter=getIDCounter();
	}
	/*
	 * Adds a new Card to the acoording Json File defined by the Topic.
	 * By extracting all Data from the CardData Object 
	 * then creating the according JSON Object with it 
	 * and at last calling addJsonEntry() with the path of the JSON File and the JSOn Object
	 * Params:
	 * cardData: object of the Card including all data for saving it in a JSON File
	 */
	public void addCard(CardData cardData){
		try {
			File kategorieFile = getSpecificJSONFile(cardData.getTopic());
			JSONObject obj = new JSONObject();
		    String jsonText;
		    obj.put("Frontside", cardData.getFrontSide());
		    obj.put("Backside", cardData.getBackSide());
		    obj.put("Learningphase", new Integer(1));
		    obj.put("Id", idCounter+1);
		    jsonText = obj.toString();
			String newFileConent = addJSONContent(getFileContent(kategorieFile), jsonText);
		    addJsonEntry(kategorieFile.getAbsolutePath(), newFileConent);
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
		File idCounterFile = getCounterTXT();
		addJsonEntry(idCounterFile.getAbsolutePath(),""+idCounter);
	}
	private File getCounterTXT() {
		File[] dataFiles = getJsonDataFiles();
		for(File datafile : dataFiles) {
			if(datafile.getName().equals("counter.txt")) {
				return datafile;
			}
		}
		return null;
	}
	private int getIDCounter() {
		File idCounterFile= getCounterTXT();
		try {
			String contentCounterFile = getFileContent(idCounterFile);
			return Integer.parseInt(contentCounterFile);
			
		} catch (IOException e) {
			System.out.println("getIDCounter has Error");
			e.printStackTrace();
			return 0;
		}
	}
	
	public void changePhase(CardData displeayedCard, boolean increment){
		File kategorieFile = this.getSpecificJSONFile(displeayedCard.getTopic());
		List<CardData> allCards = extractCardsFromDataFile(kategorieFile, displeayedCard.getTopic());
		String currentContentOfNewFile = "{\"Cards\":[]}";
		
		for(CardData card : allCards) {
			if(card.getID() == displeayedCard.getID()) {
				if(increment) {
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
		    currentContentOfNewFile = addJSONContent(currentContentOfNewFile, jsonText);
		}
		addJsonEntry(kategorieFile.getAbsolutePath(), currentContentOfNewFile);
	}

	/*
	 * Combines the to Strings
	 * params: 
	 * existingFileContent: Content of the existing File
	 * jsonTextToAdd: Content of the String to add
	 */
	private String addJSONContent(String existingFileContent, String jsonTextToAdd) {
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


	/* 
	 * Adds a JSON-Object to an Existing JSON File.
	 * Params:
	 * absolutePath: absolute Path of the JSON File
	 * jsonText: The Text in JSON Syntax that is beeing added
	 */
	private void addJsonEntry(String absolutePath, String jsonText){
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


	/*
	 * Params:
	 * kategorieFile: the JSON-File-Object in the Windows explorer
	 */
	private String getFileContent(File kategorieFile) throws IOException {
		Path path = Paths.get(kategorieFile.getAbsolutePath());
		return String.join("\n", Files.readAllLines(path));
	}

	/*
	 * Params:
	 * cardKategorie:  
	 */
	private File getSpecificJSONFile(String cardKategorie) {
		File[] datafiles = getJsonDataFiles();
		File kategorieFile = null;
		for(File datafile : datafiles) {
			if(datafile.getName().equals(cardKategorie+".json")) {
				kategorieFile = datafile;
			}
		}
		return kategorieFile;
	}
	
	/*
	 * 
	 */
	public List<String> getKategorie() {
		File[] JsonDatafiles = getJsonDataFiles();
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

	/*
	 * 
	 */
	private File[] getJsonDataFiles() {
		File JsonDataDirectory = new File(storageURL);
		File[] JsonDatafiles = JsonDataDirectory.listFiles();
		return JsonDatafiles;
	}

	
	public List<CardData> loadCards(int phase, String kategorie) {
		File cardsDataFile = getSpecificJSONFile(kategorie);
		List<CardData> allCards = extractCardsFromDataFile(cardsDataFile, kategorie);
		List<CardData> phaseCards = new ArrayList<CardData>();
		for(CardData card:allCards) {
			if(card.getPhase()==phase)
				phaseCards.add(card);
		}
		return phaseCards;
	}


	private List<CardData> extractCardsFromDataFile(File cardsDataFile, String kategorie) {
		try {
			JSONObject dataObject = parseJSONObjectContent(getFileContent(cardsDataFile));
			JSONArray JasonCards = (JSONArray) dataObject.get("Cards");
			List<CardData> cards = new ArrayList<CardData>();
			for(int cardNr=0; cardNr<JasonCards.size(); cardNr++) {
				JSONObject obj = (JSONObject) JasonCards.get(cardNr);
				String frontSideInput = obj.get("Frontside").toString();
				String backSideInput = obj.get("Backside").toString();
				int id = Integer.parseInt(obj.get("Id").toString());
				String topic = kategorie;
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
	
	
	private JSONObject parseJSONObjectContent(String fileContent) {
		try {
			JSONParser parser = new JSONParser();
			//System.out.println("parseJSONContent: " + fileContent);
			Object kategorieObject =  parser.parse(fileContent);
			return (JSONObject) kategorieObject;
		}
		catch(ParseException e) {
			System.out.println("parseJSONContent has Error");
			System.out.println("Parsing JSON contet from File files: "+e);
			return null;
		}
	}


	public void addKategorie(String text) {
		File newJSONfile = new File (storageURL, (""+text+".json"));
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