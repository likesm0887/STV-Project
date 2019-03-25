package entity.xPath;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class XPathBuilder {
    List<XPathNode> nodes;
    XPathNode currentNode;

    public XPathBuilder() {
        nodes = new ArrayList<>();
        currentNode = null;
    }

    public XPathBuilder append(String nodeIdentifier) {
        currentNode = new XPathNode(nodeIdentifier);
        nodes.add(currentNode);
        return this;
    }

    @Deprecated
    public XPathBuilder at(NodeAttribute attribute, String value) {
        return which(attribute, value);
    }

    public XPathBuilder which(NodeAttribute attribute, String value) {
        checkCurrentNode();
        currentNode.which(attribute, value);
        return this;
    }

    public XPathBuilder and(NodeAttribute attribute, String value) {
        checkCurrentNode();
        currentNode.and(attribute, value);
        return this;
    }

    public XPathBuilder or(NodeAttribute attribute, String value) {
        checkCurrentNode();
        currentNode.or(attribute, value);
        return this;
    }

    public String toString() {
        return build();
    }

    public String build() {
        StringJoiner sj = new StringJoiner("//", "//", "");
        nodes.forEach(each -> sj.add(each.getFullFormat()));
        return sj.toString();
    }

    public void clean() {
        nodes.clear();
        currentNode = null;
    }

    public void checkCurrentNode() {
        if (currentNode == null) {
            throw new IllegalStateException("Should append a node before doing operations");
        }
    }
}
