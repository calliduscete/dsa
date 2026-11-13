import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree (BST).
 *
 * Rule: for every node, all values in the LEFT subtree are smaller and all
 * values in the RIGHT subtree are larger. This lets us discard half the tree
 * at each step, like binary search.
 *
 * Search, insert and delete: O(h), where h is the height.
 *   Balanced tree: h = log n, so O(log n).
 *   Worst case (e.g. inserting already-sorted data): h = n, so O(n).
 * Balanced variants (AVL, Red-Black) keep h at O(log n).
 */
public class BinarySearchTree {

    private static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
        }
    }

    private Node root;
    private int size;

    // Insert: O(h). Duplicates are ignored.
    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            size++;
            return new Node(value);
        }
        if (value < node.data) {
            node.left = insert(node.left, value);
        } else if (value > node.data) {
            node.right = insert(node.right, value);
        }
        return node;
    }

    // Search: O(h), iterative, O(1) space
    public boolean contains(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.data) {
                return true;
            }
            current = (value < current.data) ? current.left : current.right;
        }
        return false;
    }

    // Smallest value: keep going left. O(h)
    public int min() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return minNode(root).data;
    }

    // Largest value: keep going right. O(h)
    public int max() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    private Node minNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Delete: O(h). Three cases:
    //   1. Leaf: just remove it.
    //   2. One child: replace the node with its child.
    //   3. Two children: replace its value with the inorder successor
    //      (smallest value in the right subtree), then delete that successor.
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.data) {
            node.left = delete(node.left, value);
        } else if (value > node.data) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null && node.right == null) {
                size--;
                return null;
            }
            if (node.left == null) {
                size--;
                return node.right;
            }
            if (node.right == null) {
                size--;
                return node.left;
            }
            Node successor = minNode(node.right);
            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }
        return node;
    }

    // Inorder traversal of a BST returns the values in sorted order: O(n)
    public List<Integer> inorder() {
        List<Integer> out = new ArrayList<>();
        inorder(root, out);
        return out;
    }

    private void inorder(Node node, List<Integer> out) {
        if (node == null) {
            return;
        }
        inorder(node.left, out);
        out.add(node.data);
        inorder(node.right, out);
    }

    public int size() {
        return size;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) {
            bst.insert(v);
        }
        System.out.println("inorder (sorted): " + bst.inorder());
        System.out.println("size: " + bst.size() + ", height: " + bst.height());
        System.out.println("contains(60): " + bst.contains(60));
        System.out.println("contains(65): " + bst.contains(65));
        System.out.println("min: " + bst.min() + ", max: " + bst.max());

        bst.delete(20);   // leaf
        System.out.println("after deleting 20 (leaf):       " + bst.inorder());
        bst.delete(30);   // one child (40)
        System.out.println("after deleting 30 (one child):  " + bst.inorder());
        bst.delete(50);   // two children (the root)
        System.out.println("after deleting 50 (two children): " + bst.inorder());
        System.out.println("size: " + bst.size());

        // Worst case: sorted input makes the tree a "linked list"
        BinarySearchTree skewed = new BinarySearchTree();
        for (int i = 1; i <= 7; i++) {
            skewed.insert(i);
        }
        System.out.println("height after inserting 1..7 in order: " + skewed.height());
    }
}
