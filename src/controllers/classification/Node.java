package controllers.classification;

import utils.AttributeType;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Araja Jyothi Babu on 02-Apr-16.
 */
public class Node {

    String name;
    List<Node> children = new ArrayList();

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, List<Node> children) {
        this.name = name;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public static Node insertNode(Node root, AttributeType type){
        return root;
    }

}
