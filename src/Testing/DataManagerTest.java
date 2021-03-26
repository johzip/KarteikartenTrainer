package Testing;

import static org.junit.jupiter.api.Assertions.*;
import Data.*;
import org.junit.jupiter.api.Test;


class DataManagerTest {
	private String dummyFrontSide = "Baum";
	private String dummyBackSide  = "Tree";
	private String dummyTopic	  = "Englisch";
	private CardData testCard = new CardData(dummyFrontSide, dummyBackSide, dummyTopic);
	
	
	@Test
	void cardDataTest() {
		assertEquals(dummyFrontSide, testCard.getFrontSide());
		assertEquals(dummyBackSide, testCard.getBackSide());
		assertEquals(dummyTopic, testCard.getTopic());
		//TODO assertEquals with timestamp by using mock-timestamp instead of libary call
	}
	
	@Test
	void addCardTest() {
		assertEquals(true, testCard.getID());
	}

}
