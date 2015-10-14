package model.tests;

import org.junit.Assert;

import model.DAOs.DAO;

/**
 * Classe abstraite permettant de pr�parer la base de donn�es en vue de tests
 * @author Th�o Gauchoux
 *
 */
public abstract class Tests {

	/**
	 * Le nom du schema � utiliser pour les tests unitaires
	 */
	private final static String TEST_SCHEMA_NAME = "TEST";
	
	/**
	 * V�rification et initialization de la base de donn�es en d�but de test
	 */
	public void before() {
		Assert.assertTrue(DAO.checkDatabaseDriver());
		DAO.setUsedSchema(TEST_SCHEMA_NAME);
	}

}
