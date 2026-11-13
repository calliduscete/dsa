import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Classic tree problems. Most are solved with recursion: solve the problem for
 * the left and right subtrees, then combine the answers.
 *
 * n = number of nodes, h = height of the tree.
 */
public class TreeProblems {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // Maximum depth (number of nodes on the longest path): O(n) time, O(h) space
    static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // Invert (mirror) a tree: O(n) time, O(h) space
    static TreeNode invert(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invert(root.left);
        TreeNode right = invert(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    // Are two trees identical? O(n) time, O(h) space
    static boolean isSameTree(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null || a.val != b.val) {
            return false;
        }
        return isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
    }

    // Is the tree a mirror of itself? O(n) time, O(h) space
    static boolean isSymmetric(TreeNode root) {
        return root == null || isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null || a.val != b.val) {
            return false;
        }
        return isMirror(a.left, b.right) && isMirror(a.right, b.left);
    }

    // Is there a root-to-leaf path adding up to target? O(n) time, O(h) space
    static boolean hasPathSum(TreeNode root, int target) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == target;
        }
        return hasPathSum(root.left, target - root.val)
                || hasPathSum(root.right, target - root.val);
    }

    // Is this a valid BST? Pass down the allowed range: O(n) time, O(h) space
    static boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValid(TreeNode node, long low, long high) {
        if (node == null) {
            return true;
        }
        if (node.val <= low || node.val >= high) {
            return false;
        }
        return isValid(node.left, low, node.val) && isValid(node.right, node.val, high);
    }

    // Diameter: the longest path (in edges) between any two nodes: O(n) time, O(h) space
    private static int diameter;

    static int diameterOfTree(TreeNode root) {
        diameter = 0;
        depth(root);
        return diameter;
    }

    private static int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = depth(node.left);
        int right = depth(node.right);
        diameter = Math.max(diameter, left + right);   // path through this node
        return 1 + Math.max(left, right);
    }

    // Level order grouped by level: O(n) time, O(w) space
    static List<List<Integer>> levelOrderByLevel(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) {
            return levels;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int count = queue.size();             // nodes on this level
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            levels.add(level);
        }
        return levels;
    }

    // Lowest common ancestor in a BST: O(h) time, O(1) space
    static TreeNode lowestCommonAncestorBST(TreeNode root, int p, int q) {
        TreeNode current = root;
        while (current != null) {
            if (p < current.val && q < current.val) {
                current = current.left;
            } else if (p > current.val && q > current.val) {
                current = current.right;
            } else {
                return current;      // the paths split here (or one equals current)
            }
        }
        return null;
    }

    public static void main(String[] args) {
        //         4
        //        / \
        //       2   7
        //      / \ / \
        //     1  3 6  9
        TreeNode bst = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9)));

        System.out.println("maxDepth: " + maxDepth(bst));
        System.out.println("isValidBST: " + isValidBST(bst));
        System.out.println("levelOrderByLevel: " + levelOrderByLevel(bst));
        System.out.println("diameter: " + diameterOfTree(bst));
        System.out.println("hasPathSum(7): " + hasPathSum(bst, 7));    // 4 -> 2 -> 1
        System.out.println("hasPathSum(100): " + hasPathSum(bst, 100));
        System.out.println("LCA(1, 3): " + lowestCommonAncestorBST(bst, 1, 3).val);
        System.out.println("LCA(1, 6): " + lowestCommonAncestorBST(bst, 1, 6).val);

        TreeNode notBst = new TreeNode(5, new TreeNode(1),
                new TreeNode(4, new TreeNode(3), new TreeNode(6)));
        System.out.println("isValidBST (invalid tree): " + isValidBST(notBst));

        TreeNode symmetric = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        System.out.println("isSymmetric: " + isSymmetric(symmetric));
        System.out.println("isSameTree(bst, bst): " + isSameTree(bst, bst));

        invert(bst);
        System.out.println("levelOrder after invert: " + levelOrderByLevel(bst));
    }
}
