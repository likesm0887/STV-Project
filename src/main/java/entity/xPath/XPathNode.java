package entity.xPath;

public class XPathNode {
    private String identifier;
    private boolean hasCondition;
    private String condition;

    public XPathNode(String identifier) {
        this.identifier = identifier;
        this.hasCondition = false;
        this.condition = "";
    }

    public void at(NodeAttribute attribute, String value) {
        if (!hasCondition) {
            hasCondition = true;
            this.condition += concatAttribute(attribute, value);
        } else {
            throw new IllegalStateException("method at() can be used only one time");
        }
    }

    public void and(NodeAttribute attribute, String value) {
        if (hasCondition) {
            this.condition += " and " + concatAttribute(attribute, value);
        } else {
            throw new IllegalStateException("method and() is available after at() is operated");
        }
    }

    public void or(NodeAttribute attribute, String value) {
        if (hasCondition) {
            this.condition += " or " + concatAttribute(attribute, value);
        } else {
            throw new IllegalStateException("method or() is available after at() is operated");
        }
    }

    public String getFullFormat() {
        return identifier + "[" + condition + "]";
    }

    public String toString() {
        return getFullFormat();
    }

    private String concatAttribute(NodeAttribute attribute, String value) {
        return "@" + attribute.getValue() + "='" + value + "'";
    }
}
