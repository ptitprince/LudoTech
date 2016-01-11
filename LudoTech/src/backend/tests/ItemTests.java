package backend.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.POJOs.Game;
import backend.services.GameServices;
import backend.services.ItemServices;

public class ItemTests extends Tests {

	private GameServices gameServices;

	private ItemServices itemServices;

	private Game game;

	@Before
	public void before() {
		this.gameServices = new GameServices();
		this.itemServices = new ItemServices();
		super.before();
		this.game = gameServices.addGame("TestAddGameForItems", "Salut, je suis une description", 2015, 12, 2, 4,
				"Cartes", "Marco");
		Assert.assertNotNull(this.game);
	}
	
	@Test
	public void testItems() {
		itemServices.setItemsNumberOfGame(this.game.getGameID(), 4);
		Assert.assertEquals(4, itemServices.countItemsOfGame(this.game.getGameID()));
		
		itemServices.setItemsNumberOfGame(this.game.getGameID(), 2);
		Assert.assertEquals(2, itemServices.countItemsOfGame(this.game.getGameID()));
	}

	@After
	public void cleanTable() {
		Assert.assertTrue(itemServices.setItemsNumberOfGame(this.game.getGameID(), 0));
		Assert.assertTrue(gameServices.removeGame(this.game.getGameID()));
	}

}
