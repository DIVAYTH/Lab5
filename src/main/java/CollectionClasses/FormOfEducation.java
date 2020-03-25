package CollectionClasses;

public enum FormOfEducation {
    DISTANCE_EDUCATION("DISTANCE_EDUCATION"),
    FULL_TIME_EDUCATION("FULL_TIME_EDUCATION"),
    EVENING_CLASSES("EVENING_CLASSES");

    private final String description;

    FormOfEducation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
