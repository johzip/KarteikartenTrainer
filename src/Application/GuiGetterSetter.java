package Application;

import java.awt.Color;

public interface GuiGetterSetter {
	

	public void setBackpaneColor(Color color);

	public void setFrontpaneColor(Color color);

	public void setBackpane(String text);

	public void setFrontpane(String text);

	public String getAddKategorieText();

	public void setBtn_exam_Correkt(boolean enable);
	
	public void setBtn_exam_submitText(String text);
}