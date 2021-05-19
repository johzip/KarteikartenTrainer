package Adapters;

import java.awt.Color;

public interface VocabelDisplayer {
	

	public void setBackpaneColor(Color color);

	public void setFrontpaneColor(Color color);

	public void setBackpane(String text);

	public void setFrontpane(String text);

	public String getAddKategorieText();

	public void setBtn_exam_Correkt(boolean enable);
	
	//temporary
	public void checkifCorrectExam();

}