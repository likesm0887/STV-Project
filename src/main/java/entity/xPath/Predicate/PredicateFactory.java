package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;

public class PredicateFactory {
    public Predicate createUnaryPredicate(NodeAttribute attribute, String value) {
        return new UnaryPredicate(attribute, value);
    }

    public Predicate createAndPredicate(NodeAttribute attribute, String value) {
        return new AndPredicate(attribute, value);
    }

    public Predicate createOrPredicate(NodeAttribute attribute, String value) {
        return new OrPredicate(attribute, value);
    }


}
