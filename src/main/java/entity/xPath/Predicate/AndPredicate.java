package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;

public class AndPredicate extends Predicate {
    public AndPredicate(NodeAttribute attribute, String value) {
        super(attribute, value);
    }

    public String toString() {
        return "and " + getAttributePairString();
    }
}