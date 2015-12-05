package model.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.pojos.Game;
import model.services.GameServices;

/**
 * Tests sur la manipulation de jeux
 */
public class GameTests extends Tests {

	/**
	 * L'objet exposant les services liés aux jeux
	 */
	private GameServices gameServices;

	@Before
	public void before() {
		this.gameServices = new GameServices();
		super.before();
	}

	@Test
	public void testAddGames() {
		// Ajout des jeux
		Game addedGame1 = gameServices.addGame("TestAddGame1", "Salut, je suis une description", 2015, 12, 2, 4, "Cartes", "Marco");
		Game addedGame2 = gameServices.addGame("TestAddGame2", "Coucou, moi j'en suis une autre", 2011, 4, 1, 2, "Plateau", "Marco");
		Assert.assertNotNull(addedGame1);
		Assert.assertNotNull(addedGame2);
		
		// Vérification de l'ajout des jeux
		Assert.assertNotNull(gameServices.getGame(addedGame1.getGameID()));
		Assert.assertNotNull(gameServices.getGame(addedGame2.getGameID()));
	}
	
	@Test
	public void testEditGames() {
		// Ajout du jeu à modifier
		Game editableGame = gameServices.addGame("TestEditGame1", "Ancienne description", 2015, 8, 2, 6, "Dés", "Machin");
		Assert.assertNotNull(editableGame);

		// Modification du jeu
		Assert.assertNotNull(gameServices.editGame(editableGame.getGameID(), editableGame.getName(),
				"Nouvelle description", editableGame.getPublishingYear(), editableGame.getMinimumAge(),
				editableGame.getMinimumPlayers(), editableGame.getMaximumPlayers(), editableGame.getCategory(),
				editableGame.getEditor()));
		
		// Vérification que la colonne description a bien été modifiée
		Assert.assertEquals("Nouvelle description", gameServices.getGame(editableGame.getGameID()).getDescription());
	}
	
	@Test
	public void testRemoveGames() {
		// Ajout du jeu à supprimer
		Game deletableGame = gameServices.addGame("TestRemoveGame1", "Salut, je suis une description", 2015, 8, 2, 6, "Dés", "Machin");
		Assert.assertNotNull(deletableGame);

		// Suppression du jeu
		Assert.assertNotNull(gameServices.remove(deletableGame.getGameID()));
		
		// Verification que le jeu a bien été supprimé
		Assert.assertNull(gameServices.getGame(deletableGame.getGameID()));
	}
	
	@Test
	public void testGetGames() {
		// Ajout du jeu à obtenir
		Game gettableGame = gameServices.addGame("TestGetGame1", "Description", 2015, 8, 2, 6, "Dés", "Machin");
		Assert.assertNotNull(gettableGame);
		
		// Obtention du jeu
		Assert.assertNotNull(gameServices.getGame(gettableGame.getGameID()));
	}

}
