package model.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.POJOs.Extension;
import model.POJOs.Game;
import model.services.ExtensionServices;
import model.services.GameServices;

public class ExtensionTests extends Tests {

	/**
	 * L'objet exposant les services liés aux jeux
	 */
	private GameServices gameServices;

	private ExtensionServices extensionServices;

	private Game game;

	@Before
	public void before() {
		this.gameServices = new GameServices();
		this.extensionServices = new ExtensionServices();
		super.before();
		this.game = gameServices.addGame("TestAddGameForExtensions", "Salut, je suis une description", 2015, 12, 2, 4,
				"Cartes", "Marco");
		Assert.assertNotNull(this.game);
	}
	
	@Test
	public void testAddExtension() {
		Assert.assertNotNull(extensionServices.addExtensionToGame("Une extension", this.game.getGameID()));
		Assert.assertNotNull(extensionServices.addExtensionToGame("Une 2ème extension", this.game.getGameID()));
		
		Assert.assertEquals(2, extensionServices.countExtensionOfGame(this.game.getGameID()));
	}
	
	@Test
	public void testDeleteExtension() {
		Extension extension = extensionServices.addExtensionToGame("Une extension à supprimer", this.game.getGameID());
		Assert.assertNotNull(extension);
		
		Assert.assertTrue(extensionServices.deleteExtensionFromGame(extension.getExtensionID()));
	}

	@After
	public void cleanTable() {
		for (Extension extension : extensionServices.getExtensionList(this.game.getGameID())) {
			Assert.assertTrue(extensionServices.deleteExtensionFromGame(extension.getExtensionID()));
		}
		Assert.assertTrue(gameServices.remove(this.game.getGameID()));
	}

}
