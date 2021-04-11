package Data;


public class CardData {
	private String frontSide;
	private String backSide;
	private String topic;
	private int id;
	private int phase;
	
	public CardData(String frontSideInput, String backSideInput, String pTopic, int pPhase) {
		//TODO check Inputs for valuable contents
		this.phase = pPhase;
		this.frontSide = frontSideInput;
		this.backSide = backSideInput;
		this.topic = pTopic;
	}

	//Getters
	public int getID() {
		return id;
	}
	public String getBackSide() {
		return backSide;
	}
	public String getFrontSide() {
		return frontSide;
	}
	public String getTopic() {
		return topic;
	}
	public int getPhase() {
		return phase;
	}
}
