import CollectionClasses.StudyGroup;
import java.util.Comparator;

/**
 * class comparator сортитует по алфавиту FormOfEducation
 */
public class ComparatorByFormOfEducation implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((StudyGroup)o2).getFormOfEducation().getDescription().compareTo(((StudyGroup)o1).getFormOfEducation().getDescription());
    }
}
