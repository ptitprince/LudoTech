package backend.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.POJOs.Member;
import backend.services.MemberServices;

public class ProfileTests extends Tests {

	private MemberServices memberServices;

	@Before
	public void before() {
		this.memberServices = new MemberServices();
		super.before();
	}	
	
	@Test
	public void testSaveMember() {
		// Ajout du membre à modifier

		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		try {
			Member editableMember = memberServices.addMember("TestEditMember1", "Nom de famille 1", "Pseudo admin", "mdpAdmin", true,
					sdf.parse("29/03/1995"), "0770564632", "kikou@lol.fr", "02 rue du romarin", "34090", "Montpellier",  0, 0, sdf.parse("02/05/2015"), true, true);
			Assert.assertNotNull(editableMember);
			

			// Modification du membre
			Assert.assertNotNull(memberServices.editMember(editableMember.getMemberID(), editableMember.getFirstName(),
					"Nouveau last name", editableMember.getPseudo(),  editableMember.getPassword(),  editableMember.isAdmin(), editableMember.getBirthDate(),
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