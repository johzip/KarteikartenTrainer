package Application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Domain.CardData;

public class JsonDataProcessor implements CardDataProcessor {
	@Override
	public File[] getAllKategorieDataFiles(String storageURL) {
		File JsonDataDirectory = new File(storageURL);
		File[] JsonDatafiles = JsonDataDirectory.listFiles();
		return JsonDatafiles;
	}
	@Override
	public File getSpecificKategorieFile(String cardKategorie,String storageURL) {
		File[] datafiles = getAllKategorieDataFiles(storageURL);
		File kategorieFile = null;
		for(File datafile : datafiles) {
			if(datafile.getName().equals(cardKategorie+".json")) {
				kategorieFile = datafile;
			}
		}
		return kategorieFile;
	}
	@Override
	public void addCardWith(CardData cardData, File kategorieFile, int idCounter){
		try {
			JSONObject obj = new JSONObject();
		    String jsonText;
		    obj.put("Frontside", cardData.getFrontSide());
		    obj.put("Backside", cardData.getBackSide());
		    obj.put("Learningphase", new Integer(1));
		    obj.put("Id",  + 1);
		    jsonText = obj.toString();
			String newFileConent = addContentToFilesContent(getFileContentOf(kategorieFile), jsonText);
			replaceFileContentWith(kategorieFile.getAbsolutePath(), newFileConent);
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
	@Override
	public String addContentToFilesContent(String existingFileContent, String jsonTextToAdd) {
		existingFileContent = existingFileContent.trim();
		int positionWhereToAdd = existingFileContent.indexOf("[") + 1;
		String cobinedJSONString = existingFileContent.substring(0, positionWhereToAdd) + jsonTextToAdd;
		if( !isFirstCard(existingFileContent.substring(positionWhereToAdd)+1)) {
			cobinedJSONString= cobinedJSONString + "," ;
		}
		return (cobinedJSONString + existingFileContent.substring(positionWhereToAdd));
	}
	@Override
	public boolean isFirstCard(String JSONString) {
		String firstChar = JSONString.substring(0, 1);
		if(firstChar.equals("]"))
			return true;
		return false;
	}
	@Override
	public void replaceFileContentWith(String absolutePath, String jsonText){
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
	@Override
	public String getFileContentOf(File kategorieFile) throws IOException {
		Path path = Paths.get(kategorieFile.getAbsolutePath());
		return String.join("\n", Files.readAllLines(path));
	}
	@Override
	public List<CardData> extractCardDataObjectsFrom(File cardsDataFile) {
		try {
			JSONObject dataObject = parseIntoJSONObject(getFileContentOf(cardsDataFile));
			JSONArray JasonCards = (JSONArray) dataObject.get("Cards");
			List<CardData> cards = new ArrayList<CardData>();
			for(int cardNr=0; cardNr<JasonCards.size(); cardNr++) {
				JSONObject obj = (JSONObject) JasonCards.get(cardNr);
				String frontSideInput = obj.get("Frontside").toString();
				String backSideInput = obj.get("Backside").toString();
				int id = Integer.parseInt(obj.get("Id").toString());
				String topic = getKategoriNameFrom(cardsDataFile);
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
	@Override
	public String getKategoriNameFrom(File cardsDataFile) {
		String fullName = cardsDataFile.getName();
		int pos = fullName.lastIndexOf(".");
        String KategoriNameWithoutExtesion = fullName.substring(0, pos);
		return KategoriNameWithoutExtesion;
	}
	
	public JSONObject parseIntoJSONObject(String fileContent) {
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
}
