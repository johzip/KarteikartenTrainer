package Data;
//This class uses the Libary JSON.simple from https://code.google.com/archive/p/json-simple/downloads

import java.io.BufferedWriter;
import java.io.File;
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

public class JsonDataManager {
	
	private String storageURL = System.getProperty("user.dir") + "\\JSON_Data" ;
	
	public void addCard(CardData cardData){
		try {
			File kategorieFile = getSpecificJSONFile(cardData.getTopic());
			JSONObject object = parseJSONContent(getFileContent(kategorieFile));
			
			JSONObject obj = new JSONObject();
		    String jsonText;
		    obj.put("Frontside", cardData.getFrontSide());
		    obj.put("Backside", cardData.getBackSide());
		    obj.put("Learningphase", new Integer(0));
		    jsonText = obj.toString();
		    addJsonEntry(kategorieFile.getAbsolutePath(), jsonText);
		}
		catch(NullPointerException e2) {
			System.out.println(e2);
		}
		catch(Exception e3) {
			System.out.println(e3); 
		}
	}

	private void addJsonEntry(String absolutePath, String jsonText){
		try (FileWriter fw = new FileWriter(absolutePath, true);
	       BufferedWriter bw = new BufferedWriter(fw)) {
	     
			bw.write(jsonText);
			bw.newLine();     
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private JSONObject parseJSONContent(String fileContent) {
		try {
			JSONParser parser = new JSONParser();
			Object kategorieObject =  parser.parse(fileContent);
			return (JSONObject) kategorieObject;
		}
		catch(ParseException e) {
			System.out.println("Parsing JSON contet from File files: "+e);
			return null;
		}
	}

	private String getFileContent(File kategorieFile) throws IOException {
		Path path = Paths.get(kategorieFile.getAbsolutePath());
		return String.join("\n", Files.readAllLines(path));
	}

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

	private File[] getJsonDataFiles() {
		File JsonDataDirectory = new File(storageURL);
		File[] JsonDatafiles = JsonDataDirectory.listFiles();
		return JsonDatafiles;
	}
	
	public boolean checkIfAnswerIsCorrect() {
		return false;
	}
	
}
