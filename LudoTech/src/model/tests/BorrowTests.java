package model.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import model.POJOs.Borrow;
import model.services.BorrowServices;

public class BorrowTests extends Tests {
	/**
	 * Objet d'accès aux services de l'emprunt.
	 */
	private BorrowServices borrowServices;
	
	@Before
	public void before()	{
		this.borrowServices = new BorrowServices();
		super.before();
	}
	@Test
	public void testAddBorrows(int itemID, int memberID)	{
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy"); 
		Date beginDate1 = new Date();
		Date endingDate1 = new Date();
		Date beginDate2 = new Date();
		Date endingDate2 = new Date();
		
		try {
			beginDate1 = format1.parse("31/01/2016");
			endingDate1 = format1.parse("07/02/2016");
			beginDate2 = format1.parse("03/02/2016");
			endingDate2 = format1.parse("07/02/2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Borrow addedBorrow1 = borrowServices.addBorrow(itemID, memberID, beginDate1, endingDate1, "En cours", false);
		Borrow addedBorrow2 = borrowServices.addBorrow(itemID, memberID, beginDate1, endingDate1, "En cours", false);
	
		Assert.assertNotNull(addedBorrow1);
		Assert.assertNotNull(addedBorrow2);
		
		Assert.assertNotNull(borrowServices.getBorrow(addedBorrow1.getBorrowId()));
		Assert.assertNotNull(borrowServices.getBorrow(addedBorrow2.getBorrowId()));
	}
	
	@Test
	public void testEditBorrow(int itemID, int memberID)	{
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy"); 
		Date beginDate1 = new Date();
		Date endingDate1 = new Date();
		
		try {
			beginDate1 = format1.parse("21/01/2016");
			endingDate1 = format1.parse("28/01/2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Ajout de l'emprunt pour le test.
		Borrow editableBorrow = borrowServices.addBorrow(itemID, memberID, beginDate1, endingDate1, "En cours", false);
		Assert.assertNotNull(editableBorrow);
		
		//Changement d'un emprunt.
		Assert.assertNotNull(borrowServices.editBorrow(editableBorrow.getItemId(), editableBorrow.getMemberId(),
		editableBorrow.getBeginningDate() ,editableBorrow.getEndingDate() , "En retard", false));
		
		//Vérification du changement.
		Assert.assertEquals("En retard", editableBorrow.getBorrowState());
		
		
	}
	
	@Test
	public void testRemoveBorrow(int itemID, int memberID)	{
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy"); 
		Date beginDate1 = new Date();
		Date endingDate1 = new Date();
		
		try {
			beginDate1 = format1.parse("23/01/2016");
			endingDate1 = format1.parse("30/01/2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Ajout de l'emprunt pour le test.
		Borrow removableBorrow = borrowServices.addBorrow(itemID, memberID, beginDate1, endingDate1, "En cours", false);
		Assert.assertNotNull(removableBorrow);
		
		//suppression de l'emprunt test.
		Assert.assertNotNull(borrowServices.removeBorrow(removableBorrow.getBorrowId()));
		
		//On vérifie que le borrow a bien été supprimé.
		Assert.assertNull(borrowServices.getBorrow(removableBorrow.getBorrowId()));
	}
	
	@Test
	public void testGetBorrow(int itemID, int memberID)	{
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy"); 
		Date beginDate1 = new Date();
		Date endingDate1 = new Date();
		
		try {
			beginDate1 = format1.parse("24/01/2016");
			endingDate1 = format1.parse("31/01/2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Ajout de l'emprunt pour le test.
		Borrow gotBorrow = borrowServices.addBorrow(itemID, memberID, beginDate1, endingDate1, "En cours", false);
		Assert.assertNotNull(gotBorrow);
		
		//Obtention du jeu.
		Assert.assertNotNull(borrowServices.getBorrow(gotBorrow.getBorrowId()));
	}
	
}
