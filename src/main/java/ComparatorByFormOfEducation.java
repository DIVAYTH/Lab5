import CollectionClasses.StudyGroup;

import java.util.Comparator;

/**
 * class comparator сортитует по алфавиту FormOfEducation
 */
public class ComparatorByFormOfEducation implements Comparator<StudyGroup> {
    @Override
    public int compare(StudyGroup o1, StudyGroup o2) {
        return (o2).getFormOfEducation().getDescription().compareTo((o1).getFormOfEducation().getDescription());
    }
}
