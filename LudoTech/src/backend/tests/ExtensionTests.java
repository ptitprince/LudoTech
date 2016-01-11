package backend.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.POJOs.Extension;
import backend.POJOs.Game;
import backend.services.ExtensionServices;
import backend.services.GameServices;

public class ExtensionTests extends Tests {

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
		Extension extension = extensionServices.addExtension("Une extension", this.game.getGameID());
		Assert.assertNotNull(extension);
	}
	
	@Test
	public void testCountExtensions() {
		Extension extension1 = extensionServices.addExtension("Une extension à compter 1", this.game.getGameID());
		Assert.assertNotNull(extension1);
		Extension extension2 = extensionServices.addExtension("Une extension à compter 2", this.game.getGameID());
		Assert.assertNotNull(extension2);
		
		Assert.assertEquals(2, extensionServices.countExtensions(this.game.getGameID()));
	}
	
	@Test
	public void testGetExtensions() {
		Extension extension1 = extensionServices.addExtension("Une extension à obtenir 1", this.game.getGameID());
		Assert.assertNotNull(extension1);
		Extension extension2 = extensionServices.addExtension("Une extension à obtenir 2", this.game.getGameID());
		Assert.assertNotNull(extension2);
		
		Assert.assertNotNull(extensionServices.getExtensions(this.game.getGameID()));
	}
	
	@Test
	public void testDeleteExtension() {
		Extension extension = extensionServices.addExtension("Une extension à supprimer", this.game.getGameID());
		Assert.assertNotNull(extension);
		
		Assert.assertTrue(extensionServices.deleteExtension(extension.getExtensionID()));
	}

	@After
	public void cleanTable() {
		for (Extension extension : extensionServices.getExtensions(this.game.getGameID())) {
			Assert.assertTrue(extensionServices.deleteExtension(extension.getExtensionID()));
		}
		Assert.assertTrue(gameServices.removeGame(this.game.getGameID()));
	}

}
