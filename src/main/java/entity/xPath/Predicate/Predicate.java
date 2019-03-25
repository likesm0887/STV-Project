package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;

import java.util.Objects;

public abstract class Predicate {
    private NodeAttribute attribute;
    private String value;

    public Predicate(NodeAttribute attribute, String value) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(value);
        checkAttributePair(attribute, value);

        this.attribute = attribute;
        this.value = value;
    }

    public abstract String toString();

    protected String getAttributePairString() {
        return "@" + attribute.getValue() + "='" + value + "'";
    }

    private void checkAttributePair(NodeAttribute attribute, String value) {
        if (attribute == NodeAttribute.INDEX) {
            Integer.parseInt(value);
        }
    }
}