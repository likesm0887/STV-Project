package entity.xPath;

public enum NodeAttribute {
    TEXT("text"),
    RESOURCE_ID("resource-id"),
    INDEX("index"),
    CONTENT_DESC("content-desc"),
    CLASS("class");

    private String value;

    private NodeAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
