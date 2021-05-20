package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import Domain.CardData;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CardDataTest {
	
	@Mock
	private String frontside = "Baum";
	private String backside = "Tree";
	private String kathegorie = "englisch";
	private int phase = 1;
	private int id = 12345;
	private CardData testCard = new CardData(frontside, backside, kathegorie, phase, id);
	
	@Test
	void cardDataTest() {
		assertEquals(testCard.getFrontSide(), frontside);
		assertEquals(testCard.getBackSide(), backside);
		assertEquals(testCard.getTopic(), kathegorie);
		assertEquals(testCard.getID(), id);
		assertEquals(testCard.getPhase(), phase);
	}
	
	@Test
	void PhaseTest() {
		testCard.resetPhase();
		assertEquals(testCard.getPhase(), 1);
		testCard.incrementPhase();
		assertEquals(testCard.getPhase(), 2);
	}
}
