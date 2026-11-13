/**
 * Classic linked list problems: reverse, middle, cycle detection,
 * merge sorted lists, remove nth from end.
 */
public class LinkedListProblems {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    // Build a list from an array (helper for testing)
    static ListNode build(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int v : values) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    static String toString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        for (ListNode cur = head; cur != null; cur = cur.next) {
            sb.append(cur.val).append(" -> ");
        }
        return sb.append("null").toString();
    }

    // Reverse a list iteratively: O(n) time, O(1) space
    static ListNode reverse(ListNode head) {
        ListNode previous = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    // Find the middle node with slow and fast pointers: O(n) time, O(1) space
    // For an even length, returns the second middle node.
    static ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // Detect a cycle (Floyd's algorithm): O(n) time, O(1) space
    static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    // Merge two sorted lists: O(n + m) time, O(1) extra space
    static ListNode mergeSorted(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    // Remove the nth node from the end in one pass: O(n) time, O(1) space
    static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode list = build(1, 2, 3, 4, 5);
        System.out.println("original: " + toString(list));

        list = reverse(list);
        System.out.println("reversed: " + toString(list));

        System.out.println("middle of 5,4,3,2,1: " + findMiddle(list).val);
        System.out.println("middle of 1,2,3,4: " + findMiddle(build(1, 2, 3, 4)).val);

        ListNode cyclic = build(1, 2, 3, 4);
        cyclic.next.next.next.next = cyclic.next;   // 4 points back to 2
        System.out.println("hasCycle (normal): " + hasCycle(build(1, 2, 3)));
        System.out.println("hasCycle (cyclic): " + hasCycle(cyclic));

        ListNode merged = mergeSorted(build(1, 3, 5), build(2, 4, 6));
        System.out.println("merged: " + toString(merged));

        ListNode trimmed = removeNthFromEnd(build(1, 2, 3, 4, 5), 2);
        System.out.println("remove 2nd from end: " + toString(trimmed));
    }
}
