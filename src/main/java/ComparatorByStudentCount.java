import CollectionClasses.StudyGroup;
import java.util.Comparator;

/**
 * class comparator сортитует по значению StudentsCount
 */
public class ComparatorByStudentCount implements Comparator<StudyGroup> {
    @Override
    public int compare(StudyGroup o1, StudyGroup o2) {
        return (o1).getStudentsCount() - (o2).getStudentsCount();
    }
}
