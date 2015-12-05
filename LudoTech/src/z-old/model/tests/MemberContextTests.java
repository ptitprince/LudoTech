package model.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.pojos.MemberContext;
import model.services.MemberContextServices;

/**
 * Test sur la manipulation de contextes d'adhérents
 */
public class MemberContextTests extends Tests {

	/**
	 * Objet exposant les services liés aux contextes d'adhérents
	 */
	private MemberContextServices memberContextServices;

	@Before
	public void before() {
		this.memberContextServices = new MemberContextServices();
		super.before();
	}

	@Test
	public void testAddMemberContexts() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Assert.assertNotNull(memberContextServices.addMemberContext(2, 1, sdf.parse("05/11/2015"), true, true));
			Assert.assertNotNull(memberContextServices.addMemberContext(0, 0, sdf.parse("02/07/2012"), false, false));
		} catch (ParseException e) {
			Assert.fail("La date utilisée pour le test n'a pas pû être parsée correctement par Java");
			e.printStackTrace();
		}
	}

	@Test
	public void testEditMemberContext() {
		// Ajout du contexte à modifier
		MemberContext editableMemberContext = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			editableMemberContext = memberContextServices.addMemberContext(0, 0, sdf.parse("11/11/2011"), true, true);
		} catch (ParseException e) {
			Assert.fail("La date utilisée pour le test n'a pas pû être parsée correctement par Java");
			e.printStackTrace();
		}
		Assert.assertNotNull(editableMemberContext);

		// Modification du contexte d'un adhérent (le nombre de retards passe de
		// 0 à 1)
		Assert.assertNotNull(memberContextServices.editMemberContext(editableMemberContext.getId(), 1,
				editableMemberContext.getNbFakeBookings(), editableMemberContext.getLastSubscriptionDate(),
				editableMemberContext.canBorrow(), editableMemberContext.canBook()));
	}

	@Test
	public void testRemoveMemberContext() {
		// Ajout du contexte à supprimer
		MemberContext deletableMemberContext = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			deletableMemberContext = memberContextServices.addMemberContext(0, 0, sdf.parse("11/11/2011"), true, true);
		} catch (ParseException e) {
			Assert.fail("La date utilisée pour le test n'a pas pû être parsée correctement par Java");
			e.printStackTrace();
		}
		Assert.assertNotNull(deletableMemberContext);
		
		// Suppression du contexte d'un adhérent via son identifiant
		Assert.assertTrue(memberContextServices.removeMemberContext(deletableMemberContext.getId()));
	}

	@Test
	public void testGetMemberContext() {
		// Ajout du contexte à obtenir
		MemberContext obtainableMemberContext = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			obtainableMemberContext = memberContextServices.addMemberContext(0, 0, sdf.parse("11/11/2011"), true, true);
		} catch (ParseException e) {
			Assert.fail("La date utilisée pour le test n'a pas pû être parsée correctement par Java");
			e.printStackTrace();
		}
		Assert.assertNotNull(obtainableMemberContext);

		// Obtention du contexte d'un adhérent via son identifiant
		Assert.assertNotNull(memberContextServices.getMemberContext(obtainableMemberContext.getId()));
	}
	
}
