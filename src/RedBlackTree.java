import java.awt.*;

public class RedBlackTree {
    private static final Color RED   = Color.RED;
    private static final Color BLACK = Color.BLACK;
    private static final Color NO_COLOR = Color.WHITE;
    private Node root;
    private final Node nullNode = new Node();
    public RedBlackTree() {
        nullNode.color = BLACK;
        nullNode.left = null;
        nullNode.right = null;
        this.root = nullNode;
    }
    public Node getRoot(){
        return this.root;
    }
    public int getDepth() {
        return this.getDepth(this.root);
    }
    private int getDepth(Node node) {
        if (node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.left);
            return left_depth > (right_depth = this.getDepth(node.right)) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    }
    public void leftRotate(Node node) {
        Node newNode = node.right;
        node.right = newNode.left;
        if (newNode.left != nullNode) {
            newNode.left.parent = node;
        }
        newNode.parent = node.parent;
        if (node.parent == null) {
            this.root = newNode;
        } else if (node == node.parent.left) {
            node.parent.left = newNode;
        } else {
            node.parent.right = newNode;
        }
        newNode.left = node;
        node.parent = newNode;
    }
    public void rightRotate(Node node) {
        Node newNode = node.left;
        node.left = newNode.right;
        if (newNode.right != nullNode) {
            newNode.right.parent = node;
        }
        newNode.parent = node.parent;
        if (node.parent == null) {
            this.root = newNode;
        } else if (node == node.parent.right) {
            node.parent.right = newNode;
        } else {
            node.parent.left = newNode;
        }
        newNode.right = node;
        node.parent = newNode;
    }
    private void fixInsert(Node node){
        Node uncle;
        while (node.parent.color == RED) {
            if (node.parent == node.parent.parent.right) {
                uncle = node.parent.parent.left;
                if (uncle.color == RED) {
                    uncle.color = BLACK;
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    leftRotate(node.parent.parent);
                }
            } else {
                uncle = node.parent.parent.right;
                if (uncle.color == RED) {
                    uncle.color = BLACK;
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rightRotate(node.parent.parent);
                }
            }
            if (node == root) {
                break;
            }
        }
        root.color = BLACK;
    }
    public void insert(int value) {
        Node node = new Node();
        node.parent = null;
        node.value = value;
        node.left = nullNode;
        node.right = nullNode;
        node.color = RED;
        Node root = this.root;
        Node current = null;
        while (root != nullNode) {
            current = root;
            if (node.value < root.value) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        node.parent = current;
        if (current == null) {
            this.root = node;
        } else if (node.value < current.value) {
            current.left = node;
        } else {
            current.right = node;
        }
        if (node.parent == null) {
            node.color = BLACK;
            System.out.println("Value " + value +" has been inserted.");
            return;
        }
        if (node.parent.parent == null) {
            System.out.println("Value " + value +" has been inserted.");
            return;
        }
        fixInsert(node);
        System.out.println("Value " + value +" has been inserted.");
    }
    private void transplant(Node target, Node with){
        if (target.parent == null) {
            root = with;
        } else if (target == target.parent.left){
            target.parent.left = with;
        } else {
            target.parent.right = with;
        }
        with.parent = target.parent;
    }
    public Node minimum(Node subTreeRoot) {
        while (subTreeRoot.left != nullNode) {
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }
    private void fixDelete(Node node) {
        Node sibling;
        while (node != root && node.color == BLACK) {
            if (node == node.parent.left) {
                sibling = node.parent.right;
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    leftRotate(node.parent);
                    sibling = node.parent.right;
                }
                if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling.right.color == BLACK) {
                        sibling.left.color = BLACK;
                        sibling.color = RED;
                        rightRotate(sibling);
                        sibling = node.parent.right;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = BLACK;
                    sibling.right.color = BLACK;
                    leftRotate(node.parent);
                    node = root;
                }
            } else {
                sibling = node.parent.left;
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    rightRotate(node.parent);
                    sibling = node.parent.left;
                }
                if (sibling.right.color == BLACK) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling.left.color == BLACK) {
                        sibling.right.color = BLACK;
                        sibling.color = RED;
                        leftRotate(sibling);
                        sibling = node.parent.left;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = BLACK;
                    sibling.left.color = BLACK;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }
        node.color = BLACK;
    }
    public void delete(int value) {
        Node root = this.root;
        Node current = nullNode;
        Node x, y;
        if (this.root == nullNode) {
            System.out.println("The red-black tree is empty, value couldn't be found.");
            return;
        }
        while (root != nullNode){
            if (root.value == value) {
                current = root;
            }
            if (root.value <= value) {
                root = root.right;
            } else {
                root = root.left;
            }
        }
        if (current == nullNode) {
            System.out.println("Couldn't find key in the tree");
            return;
        }
        y = current;
        Color yOriginalColor = y.color;
        if (current.left == nullNode) {
            x = current.right;
            transplant(current, current.right);
        } else if (current.right == nullNode) {
            x = current.left;
            transplant(current, current.left);
        } else {
            y = minimum(current.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == current) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = current.right;
                y.right.parent = y;
            }
            transplant(current, y);
            y.left = current.left;
            y.left.parent = y;
            y.color = current.color;
        }
        if (yOriginalColor == NO_COLOR){
            fixDelete(x);
        }
        System.out.println("Value " + value +" has been deleted.");
    }

    boolean search(int value) {
        Node current = root;
        if (this.root == nullNode) {
            System.out.println("The red-black tree is empty, value couldn't be found.");
            return false;
        }
        while (current != nullNode){
            if (current.value == value){
                System.out.println("The value " + value + " is in the red-black tree.");
                return true;
            }
            else if (current.value < value)
                current = current.right;
            else
                current = current.left;
        }
        System.out.println("The value " + value + " isn't in the red-black tree.");
        return false;
    }
    public void print(Node root, String indent, boolean last) {
        if(this.root == nullNode){
            System.out.println("The red-black tree is empty.");
        } else if (root != nullNode) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent = indent + "     ";
            } else {
                System.out.print("L----");
                indent = indent +  "|    ";
            }
            String stringColor = root.color == RED?"RED":"BLACK";
            System.out.println(root.value + "(" + stringColor + ")");
            print(root.left, indent, false);
            print(root.right, indent, true);
        }
    }
    public void clear(){
        if(this.root == nullNode || this.root == null)
            System.out.println("The red-black tree is already empty.");
        else{
            this.root.right = nullNode;
            this.root.left = nullNode;
            this.root = nullNode;
            System.out.println("The red black tree has been deleted.");
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
}