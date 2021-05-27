package Application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Domain.CardData;

public interface CardDataProcessor {
	
	public File[] getAllKategorieDataFiles(String storageURL);
	
	public File getSpecificKategorieFile(String cardKategorie,String storageURL);
	
	public void addCardWith(CardData cardData, File kategorieFile, int idCounter);
	
	public String addContentToFilesContent(String existingFileContent, String jsonTextToAdd);
	
	public boolean isFirstCard(String JSONString);

	public void replaceFileContentWith(String absolutePath, String jsonText);

	public String getFileContentOf(File kategorieFile) throws IOException;

	public List<CardData> extractCardDataObjectsFrom(File cardsDataFile);

	public String getKategoriNameFrom(File cardsDataFile);
	
}
