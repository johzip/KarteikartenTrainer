package Testing;

import static org.junit.jupiter.api.Assertions.*;
import Data.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DataManagerTest {
	
	@Mock
	private CardData testCard = new CardData("Baum", "Tree", "Englisch",1);
	
	public DataManagerTest(){}
	
	
	@Test
	void cardDataTest() {
		assertEquals(testCard.getFrontSide(), testCard.getFrontSide());
		assertEquals(testCard.getBackSide(), testCard.getBackSide());
		assertEquals(testCard.getTopic(), testCard.getTopic());
		//TODO assertEquals with timestamp by using mock-timestamp instead of libary call
	}
	
	@Test
	void addCardTest() {
		
		
	}
	
	

}
