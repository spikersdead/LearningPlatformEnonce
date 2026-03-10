package mooc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlaform {

	private Platform platform;
	private Person bastide;
	private Course histoire;
	private Course geo;

	/**
	 * Sets up the test fixture.
	 *
	 * Called before every test case method.
	 */
	@BeforeEach
	public void setUp() {
		platform = new Platform();
		bastide = new Person("Rémi Bastide");
		List<Book> emptyList = new ArrayList<>();
		histoire = new Course("Histoire", 15, emptyList);
		geo = new Course("Géographie", 20, emptyList);
		platform.addCourse(histoire);
		platform.addCourse(geo);
		platform.registerStudent(bastide);
	}

	/**
	 * Vérifie qu'on ne peut pas dupliquer les inscriptions à un cours
	 */
	@Test
	public void testNoDuplicateRegistration() throws Exception {
		platform.enroll(bastide, geo);
		platform.enroll(bastide, geo);
		assertEquals(1, platform.studentsForCourse(geo).size(),
			"On doit trouver un seul étudiant");
		assertTrue(platform.studentsForCourse(geo).contains(bastide),
			"L'étudiant n'est pas correctement enegistré");
		platform.withdraw(bastide, geo);
		assertFalse(platform.studentsForCourse(geo).contains(bastide),
			"L'étudiant n'est pas correctement désinscrit");
	}

	/**
	 * Vérifier que l'attribution des notes marche bien
	 */
	@Test
	public void testGiveMark() throws Exception {
		platform.enroll(bastide, geo);
		platform.setMark(bastide, geo, 15);
		assertEquals(15, platform.getMark(bastide, geo),
			"La note n'est pas correctement enegistrée");
	}

	/**
	 * Une fois inscrit à un cours, un étudiant n'a pas encore de note pour
	 * ce cours
	 */
	@Test
	public void testNoMark() throws Exception {
		platform.enroll(bastide, geo);
		try {
			int note = platform.getMark(bastide, geo);
			// Si on arrive ici le test échoue
			fail("La note n'est pas enregistrée, on doit avoir une exception");
		} catch (Exception e) {
			// On s'attend a arriver ici
		}
	}

	/**
	 * Vérifier qu'on ne peut pas désinscrire un étudiant qui a déjà une
	 * note
	 */
	@Test
	public void testCannotUnregister() throws Exception {
		platform.enroll(bastide, geo);
		platform.setMark(bastide, geo, 15);
		try {
			platform.withdraw(bastide, geo);
			fail("On ne doit pas désisncrire un étudiant qui a déjà une note");
		} catch (Exception e) {
		}
	}

	/**
	 * Vérifier qu'on ne peut pas changer une note
	 */
	@Test
	public void testCannotChangeMark() throws Exception {
		platform.enroll(bastide, geo);
		platform.setMark(bastide, geo, 15);
		try {
			platform.setMark(bastide, geo, 16);
			fail("On ne peut pas changer une note");
		} catch (Exception e) {
		}
	}

	/**
	 * Vérifier qu'on n'inscrit pas 2x un même étudiant
	 */
	@Test
	public void testAccept() {
		int oldSize = platform.students().size();
		platform.registerStudent(bastide);
		assertTrue(platform.students().contains(bastide));
		assertEquals(oldSize, platform.students().size());
	}

	/**
	 * Vérifier qu'on n'inscrit pas un étudiant "null"
	 */
	@Test
	public void testAcceptNull() {
		try {
			platform.registerStudent(null);
			fail("Il faut un étudiant"); // Si on passe ici, le test échoue
		} catch (NullPointerException e) {
			// Si on passe ici, le test réussit
		}
	}

	/**
	 * Vérifier qu'on n'inscrit pas un étudiant à un cours inconnu
	 */
	@Test
	public void testCheckCourse() {
		List<Book> emptyList = new ArrayList<>();
		Course unknown = new Course("Bidon", 20, emptyList);
		try {
			platform.enroll(bastide, unknown);
			fail("Il faut un cours dispensé par l'université");
		} catch (Exception e) {
		}
	}

	/**
	 * Vérifier qu'on n'inscrit pas un étudiant inconnu à un cours
	 */
	@Test
	public void testCheckPerson() {
		Person unknown = new Person("Bidon");
		try {
			platform.enroll(unknown, geo);
			fail("Il faut un étudiant inscrit à l'université");
		} catch (Exception e) {
		}
	}

}
