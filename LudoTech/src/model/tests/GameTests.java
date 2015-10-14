package model.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.enums.GameCategory;
import model.services.GameServices;

/**
 * Tests orient�s vers la manipulation de jeux
 * @author Th�o Gauchoux
 *
 */
public class GameTests extends Tests{

	/**
	 * L'objet exposant les services li�s aux jeux
	 */
	private GameServices gameServices;

	@Before
	public void before() {
		this.gameServices = new GameServices();
		super.before();
	}

	@Test
	public void testAddGames() {
		Assert.assertNotNull(gameServices.addGame("La feuille bleue de l'ambiance", "Salut je suis une description", GameCategory.CARTES, "Marco", "Polo", 2015, 4));
		Assert.assertNotNull(gameServices.addGame("Encore un jeu", "Avec une description", GameCategory.DES, "Un �diteur", "Un autheur", 2014, 2));
	}

}
