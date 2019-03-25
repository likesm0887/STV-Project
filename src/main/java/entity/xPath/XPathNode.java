package entity.xPath;

import entity.xPath.Predicate.Predicate;
import entity.xPath.Predicate.PredicateFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XPathNode {
    private String identifier;
    private List<Predicate> predicates;
    private PredicateFactory predicateFactory;

    public XPathNode(String identifier) {
        this.identifier = identifier;
        predicates = new ArrayList<>();
        predicateFactory = new PredicateFactory();
    }

    public void which(NodeAttribute attribute, String value) {
        if (!isHasPredicate()) {
            predicates.add(predicateFactory.createUnaryPredicate(attribute, value));
        } else {
            throw new IllegalStateException("method which() can be used only first time");
        }
    }

    public void and(NodeAttribute attribute, String value) {
        if (isHasPredicate()) {
            predicates.add(predicateFactory.createAndPredicate(attribute, value));
        } else {
            throw new IllegalStateException("method and() is available after which() is operated");
        }
    }

    public void or(NodeAttribute attribute, String value) {
        if (isHasPredicate()) {
            predicates.add(predicateFactory.createOrPredicate(attribute, value));
        } else {
            throw new IllegalStateException("method or() is available after which() is operated");
        }
    }

    public String getFullFormat() {
        if (!isHasPredicate())
            return identifier;

        String condition = predicates.stream()
                .map(each -> each.toString())
                .collect(Collectors.joining(" "));
        return identifier + "[" + condition + "]";
    }

    private boolean isHasPredicate() {
        return predicates.size() > 0;
    }
}
