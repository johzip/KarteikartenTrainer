package Data;

import java.time.*;

public class CardData {
	private String frontSide;
	private String backSide;
	private LocalDateTime timestamp;
	private String topic;
	private int phase;
	
	public CardData(String frontSideInput, String backSideInput, String pTopic, int pPhase) {
		//TODO check Inputs for valuable contents
		this.phase = pPhase;
		this.frontSide = frontSideInput;
		this.backSide = backSideInput;
		this.timestamp = LocalDateTime.now();
		this.topic = pTopic;
	}

	//Getters
	public LocalDateTime getTimestamp() {
		return timestamp;
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
