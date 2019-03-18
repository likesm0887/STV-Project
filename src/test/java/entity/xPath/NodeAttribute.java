package entity.xPath;

public enum NodeAttribute {
    TEXT("text"),
    RESOURCE_ID("resource-id"),
    INDEX("index"),
    CONTENT_DESC("content-desc"),
    CLASS("class");

    private String text;

    private NodeAttribute(String text) {
        this.text = text;
    }

    public String getValue() {
        return text;
    }
}
