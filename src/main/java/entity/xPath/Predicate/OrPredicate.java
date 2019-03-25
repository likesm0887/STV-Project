package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;

public class OrPredicate extends Predicate {
    public OrPredicate(NodeAttribute attribute, String value) {
        super(attribute, value);
    }

    public String toString() {
        return "or " + getAttributePairString();
    }
}