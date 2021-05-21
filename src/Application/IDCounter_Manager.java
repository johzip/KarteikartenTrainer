package Application;

import java.io.File;
import java.io.IOException;

public class IDCounter_Manager {
	private static int idCounter;
	private JsonDataManager jsonManager;
	File counterFile;
	
	public IDCounter_Manager(JsonDataManager pJSONManager, File[] pDataFiles){
		counterFile = getFileOfCounter(pDataFiles);
		jsonManager = pJSONManager;
		idCounter=getIDCounterValueFromDataFile();
	}
	
	public void incrementIDCounter() {
		idCounter++;
		jsonManager.replaceJSONFileContentWith(counterFile.getAbsolutePath(),""+idCounter);
	}
	
	public File getFileOfCounter(File[] dataFiles) {
		for(File datafile : dataFiles) {
			if(datafile.getName().equals("counter.txt")) {
				return datafile;
			}
		}
		return null;
	}
	
	public int getIDCounterValueFromDataFile() {
		try {
			String contentCounterFile = jsonManager.getFileContentOf(counterFile);
			return Integer.parseInt(contentCounterFile);
			
		} catch (IOException e) {
			System.out.println("getIDCounter has Error");
			e.printStackTrace();
			return 0;
		}
	}

	public int getCounter() {
		return idCounter;
	}
	
}
