package backend.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.POJOs.Member;
import backend.services.MemberServices;



/**
 * Tests sur la manipulation de membre
 */

public class ProfileTests extends Tests {

	/**
	 * L'objet exposant les services liés aux membres
	 */
	
	
	
	
	private MemberServices memberServices;


	@Before
	public void before() {
		this.memberServices = new MemberServices();
		super.before();
	}
/*
	@Test
	public void testAddMember() {
		// Ajout de membres

		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		MemberContext mcontext1 = new MemberContext(0, 0, sdf.parse("02/05/2015"), true, true);
		MemberContext mcontext2 = new MemberContext(1, 4, sdf.parse("10/08/2014"), false, false);

		Member addedMember1 = memberServices.addMember("TestAddMember1", "Nom de famille 1", "Pseudo admin", "mdpAdmin", true,
				sdf.parse("29/03/1995"), "0770564632", "kikou@lol.fr", "02 rue du romarin", "34090", "Montpelllier", mcontext1);
		Member addedMember2 = memberServices.addMember("TestAddMember2", "Nom de famille 2", "Pseudo membreNormal", "mdpMember111", false,
				sdf.parse("30/06/1995"), "0605500000", "kkkkk@hotmail.fr", "45 rue de la république", "11000", "Carcassonne", mcontext2);

		Assert.assertNotNull(addedMember1);
		Assert.assertNotNull(addedMember2);

		// Vérification de l'ajout des membres
		Assert.assertNotNull(memberServices.getMember(addedMember1.getMemberID()));
		Assert.assertNotNull(memberServices.getMember(addedMember2.getMemberID()));
	}
*/
	
	
	/*@Test
	public void testRemoveMember() {
		// Ajout du member à supprimer

		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		MemberContext mcontext1 = new MemberContext(0, 0, sdf.parse("02/05/2015"), true, true);
		
		Member deletableMember = memberServices.addMember("TestEditMember1", "Nom de famille 1", "Pseudo admin", "mdpAdmin", true,
				sdf.parse("29/03/1995"), 0770564632, "kikou@lol.fr", "02 rue du romarin", "34090", "Montpellier", mcontext1);
		Assert.assertNotNull(deletableMember);

		// Suppression du membre
		Assert.assertNotNull(memberServices.remove(deletableMember.getMemberID()));

		// Verification que le membre a bien été supprimé
		Assert.assertNull(memberServices.getMember(deletableMember.getMemberID()));
	}
	*/
	
	@Test
	public void testSaveMember() {
		// Ajout du membre à modifier

		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		try {
			Member editableMember = memberServices.addMember("TestEditMember1", "Nom de famille 1", "Pseudo admin", "mdpAdmin", true,
					sdf.parse("29/03/1995"), "0770564632", "kikou@lol.fr", "02 rue du romarin", "34090", "Montpellier",  0, 0, sdf.parse("02/05/2015"), true, true);
			Assert.assertNotNull(editableMember);
			

			// Modification du membre
			Assert.assertNotNull(memberServices.saveMember(editableMember.getMemberID(), editableMember.getFirstName(),
					"Nouveau last name", editableMember.getPseudo(),  editableMember.getPassword(),  editableMember.getIsAdmin(), editableMember.getBirthDate(),
					editableMember.getPhoneNumber(), editableMember.getEmail(), editableMember.getStreetAddress(), 
					editableMember.getPostalCode(), editableMember.getCity()));

			// Vérification que la colonne description a bien été modifiée
			Assert.assertEquals("Nouveau last name", memberServices.getMember(editableMember.getMemberID()).getLastName());

		} catch (ParseException e) {
			Assert.fail();
			e.printStackTrace();
		}
	
	}

	


	@Test
	public void testGetMember() {
		// Ajout du membre à obtenir

		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		try {
			Member gettableMember = memberServices.addMember("TestEditMember1", "Nom de famille 1", "Pseudo admin", "mdpAdmin", true,
					sdf.parse("29/03/1995"), "0770564632", "kikou@lol.fr", "02 rue du romarin", "34090", "Montpellier", 0, 0, sdf.parse("02/06/2015"), true, true);
			Assert.assertNotNull(gettableMember);

			// Obtention du membre
			Assert.assertNotNull(memberServices.getMember(gettableMember.getMemberID()));
			
		} catch (ParseException e) {
			Assert.fail();
			e.printStackTrace();
		}


	}

}