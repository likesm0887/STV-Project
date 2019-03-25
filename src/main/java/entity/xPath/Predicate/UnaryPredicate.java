package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;

public class UnaryPredicate extends Predicate {
    public UnaryPredicate(NodeAttribute attribute, String value) {
        super(attribute, value);
    }

    public String toString() {
        return getAttributePairString();
    }
}