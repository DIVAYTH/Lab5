import CollectionClasses.StudyGroup;
import java.util.Comparator;

/**
 * class comparator сортитует по значению StudentsCount
 */
public class ComparatorByStudentCount implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((StudyGroup)o1).getStudentsCount() - ((StudyGroup)o2).getStudentsCount();
    }
}
