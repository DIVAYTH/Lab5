import CollectionClasses.StudyGroup;

import java.util.Comparator;

public class ComparatorDefault implements Comparator<StudyGroup> {
    @Override
    public int compare(StudyGroup o1, StudyGroup o2) {
        return (o1).getName().compareTo((o2).getName());
    }
}