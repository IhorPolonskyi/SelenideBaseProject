package services.enums;

public enum SortingTypes {

    PLATFORM_TYPES("Platform Types"),
    TOPICS("Topics"),
    FEATURES("Features"),
    PRICE("Price"),
    COLORS("Colors");

    private String name;

    SortingTypes(String link) {
        this.name = link;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName(){
        return name;
    }
}
