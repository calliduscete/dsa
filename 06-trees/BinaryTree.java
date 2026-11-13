import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * Binary tree basics: nodes, the four traversals, height, size and leaf count.
 *
 * A binary tree is made of nodes, and each node has at most two children
 * (left and right). n = number of nodes, h = height of the tree.
 *
 * Every traversal visits each node once: O(n) time.
 * Recursive traversals use O(h) space for the call stack.
 */
public class BinaryTree {

    static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
        }
    }

    // Depth-first: root, left, right
    static void preorder(Node node, List<Integer> out) {
        if (node == null) {
            return;
        }
        out.add(node.data);
        preorder(node.left, out);
        preorder(node.right, out);
    }

    // Depth-first: left, root, right (gives sorted order for a BST)
    static void inorder(Node node, List<Integer> out) {
        if (node == null) {
            return;
        }
        inorder(node.left, out);
        out.add(node.data);
        inorder(node.right, out);
    }

    // Depth-first: left, right, root (children before parent)
    static void postorder(Node node, List<Integer> out) {
        if (node == null) {
            return;
        }
        postorder(node.left, out);
        postorder(node.right, out);
        out.add(node.data);
    }

    // Breadth-first (level order) with a queue: O(n) time, O(w) space (w = widest level)
    static List<Integer> levelOrder(Node root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            out.add(node.data);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return out;
    }

    // Iterative inorder with an explicit stack: O(n) time, O(h) space
    static List<Integer> inorderIterative(Node root) {
        List<Integer> out = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            out.add(current.data);
            current = current.right;
        }
        return out;
    }

    // Height = number of edges on the longest root-to-leaf path: O(n)
    static int height(Node node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    // Number of nodes: O(n)
    static int size(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

    // Number of leaves (nodes with no children): O(n)
    static int countLeaves(Node node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
    }

    public static void main(String[] args) {
        //         1
        //        / \
        //       2   3
        //      / \   \
        //     4   5   6
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.right = new Node(6);

        List<Integer> out = new ArrayList<>();
        preorder(root, out);
        System.out.println("preorder:   " + out);

        out = new ArrayList<>();
        inorder(root, out);
        System.out.println("inorder:    " + out);

        out = new ArrayList<>();
        postorder(root, out);
        System.out.println("postorder:  " + out);

        System.out.println("levelOrder: " + levelOrder(root));
        System.out.println("inorder (iterative): " + inorderIterative(root));
        System.out.println("height: " + height(root));
        System.out.println("size: " + size(root));
        System.out.println("leaves: " + countLeaves(root));
    }
}
