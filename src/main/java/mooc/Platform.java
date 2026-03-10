package mooc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Platform {

    // Set et utiliser pour éviter des doublons
    private Set<Person> students = new HashSet<>();
    private Set<Course> courses = new HashSet<>();
    // on stocke les inscriptions pour ensuite pouvoir lié les notes et étudiants
    private List<Enrollment> enrollments = new ArrayList<>();

    public Platform() {
    }

    /**
     * Ajoute un cours à la liste de cours dispensés
     *
     * @param c le cours à ajouter (non null)
     */
    public void addCourse(Course c) {
        if (c == null) {
            throw new NullPointerException("Le cours ne peut pas être null");
        }
        courses.add(c);
    }

    /**
     * @return les étudiants inscrits à l'université
     */
    public Set<Person> students() {
        return students;
    }

    /**
     * @return les cours dispensés par l'université
     */
    public Set<Course> courses() {
        return courses;
    }

    /**
     * Inscrire un étudiant à l'université
     *
     * @param s l'étudiant à inscrire (non null)
     */
    public void registerStudent(Person s) {
        if (s == null) {
            throw new NullPointerException("L'étudiant ne peut pas être null");
        }
        students.add(s);
    }

    /**
     * Inscrire un étudiant à un cours
     *
     * @param s l'étudiant
     * @param c le cours
     * @throws Exception si l'étudiant n'est pas inscrit à l'université, ou si
     * le cours n'est pas dispensé par l'université
     */
    public void enroll(Person s, Course c) throws Exception {
        // Vérifie si l'etudiant existe déjà
        if (!students.contains(s)) {
            throw new Exception("L'étudiant n'est pas inscrit à l'université");
        }
        // Vérifie le cours existe déjà
        if (!courses.contains(c)) {
            throw new Exception("Le cours n'est pas dispensé par l'université");
        }

        // Vérifie si il existe déjà un étudiant similaire et un cours similaire
        for (Enrollment e : enrollments) {
            if (e.getPerson().equals(s) && e.getCourse().equals(c)) {
                return;
            }
        }

        // Si tout est bon, on crée l'inscription
        enrollments.add(new Enrollment(s, c));
    }

    /**
     * Désinscrire un étudiant à un cours
     *
     * @param s l'étudiant
     * @param c le cours
     * @throws Exception si l'étudiant a déjà une note àce cours
     */
    public void withdraw(Person s, Course c) throws Exception {
		// Recherche de l'étudiant et du cours correspondant
        Enrollment toRemove = null;
        for (Enrollment e : enrollments) {
			//Verifie si l'étudiant à une note
            if (e.getPerson().equals(s) && e.getCourse().equals(c)) {
                if (e.studentHasMark()) {
                    throw new Exception("On ne peut pas désinscrire un étudiant qui a déjà une note");
                }
				//On stocke l'étudiant que on veut supprimer
                toRemove = e;
                break; //on sort de la bloucle
            }
        }
		// On supprime l'étudiant stocker si on en a stocker un
        if (toRemove != null) {
            enrollments.remove(toRemove);
        }
    }

    /**
     * Donner une note à un étudiant pour un cours
     *
     * @param s l'étudiant
     * @param c le cours
     * @param mark la note
     * @throws Exception si l'étudiant n'est pas inscrit à l'université, ou si
     * le cours n'est pas dispensé par l'université, ou si l'étudiant a déjà une
     * note pour ce cours
     */
    public void setMark(Person s, Course c, int mark) throws Exception {
        for (Enrollment e : enrollments) {
            if (e.getPerson().equals(s) && e.getCourse().equals(c)) {
                e.setMark(mark); // Appelle la méthode de la classe Enrollment
                return;
            }
        }
        throw new Exception("Inscription introuvable");
    }

    /**
     * Connaitre la note d'un étudiant pour un cours
     *
     * @param s l'étudiant
     * @param c le cours
     * @return la note de l'étudiant pour le cours
     * @throws Exception si l'étudiant n'est pas inscrit à l'université, ou si
     * le cours n'est pas dispensé par l'université ou si l'étudiant n'a pas
     * encore de note à ce cours
     */
    public int getMark(Person s, Course c) throws Exception {
        for (Enrollment e : enrollments) {
            if (e.getPerson().equals(s) && e.getCourse().equals(c)) {
                return e.getMark(); // Appelle la méthode de la classe Enrollment
            }
        }
        throw new Exception("Inscription introuvable");
    }

    /**
     * @param c le cours considéré
     * @return les étudiants inscrits à ce cours
     * @throws Exception si le cours n'est pas dispensé par l'université
     */
    public Set<Person> studentsForCourse(Course c) throws Exception {
        if (!courses.contains(c)) {
            throw new Exception("Cours inconnu"); //

        }
        Set<Person> result = new HashSet<>();
        for (Enrollment e : enrollments) {
            if (e.getCourse().equals(c)) {
                result.add(e.getPerson()); //
            }
        }
        return result;
    }

    /**
     * @param s l'étudiant considéré
     * @return les cours auxquels un étudiant est incrit
     * @throws Exception si l'étudiant n'est pas inscrit à l'université,
     */
    public Set<Course> coursesForStudent(Person s) throws Exception {
        if (!students.contains(s)) {
            throw new Exception("Étudiant inconnu"); //

        }
        Set<Course> result = new HashSet<>();
        for (Enrollment e : enrollments) {
            if (e.getPerson().equals(s)) {
                result.add(e.getCourse()); //
            }
        }
        return result;
    }

    /**
     * @return les cours auxquels aucun étudiant n'est incrit
     */
    public Set<Course> emptyCourses() {
        Set<Course> empty = new HashSet<>(courses); // On part de tous les cours
        for (Enrollment e : enrollments) {
            empty.remove(e.getCourse()); // On retire ceux qui ont au moins une inscription
        }
        return empty;
    }

	public void unregisterStudent(Person s) {
		throw new UnsupportedOperationException("Pas encore implémenté");
	}

	public void removeCourse(Course c) {
		throw new UnsupportedOperationException("Pas encore implémenté");
	}

	public void getBookForStudent(Person s) {
		throw new UnsupportedOperationException("Pas encore implémenté");
	}

}
