package model.tests;

import org.junit.Assert;

import model.DAOs.DAO;

/**
 * Classe abstraite permettant de préparer la base de données en vue de tests
 */
public abstract class Tests {

	/**
	 * Le nom du schema à utiliser pour les tests unitaires
	 */
	private final static String TEST_SCHEMA_NAME = "TEST";
	
	/**
	 * Vérification et initialisation de la base de données en début de test
	 */
	public void before() {
		Assert.assertTrue(DAO.checkDatabaseDriver());
		DAO.setUsedSchema(TEST_SCHEMA_NAME);
	}

}
