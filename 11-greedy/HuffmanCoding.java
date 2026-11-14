import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * Huffman coding: lossless compression using variable-length codes.
 *
 * Greedy idea: frequent characters get SHORT codes and rare characters get LONG
 * codes. Repeatedly merge the two least frequent nodes into one, using a
 * min-heap, until a single tree remains. The path from the root to a leaf
 * (left = 0, right = 1) is that character's code.
 *
 * Time: O(n log n) for n distinct characters. Space: O(n).
 * Used in ZIP, JPEG and MP3 compression.
 */
public class HuffmanCoding {

    private static class Node {
        char ch;
        int freq;
        int order;            // tie-breaker, so results are deterministic
        Node left;
        Node right;

        Node(char ch, int freq, int order) {
            this.ch = ch;
            this.freq = freq;
            this.order = order;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    static Map<Character, String> buildCodes(String text) {
        Map<Character, Integer> freq = new TreeMap<>();
        for (char c : text.toCharArray()) {
            freq.merge(c, 1, Integer::sum);
        }

        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) ->
                a.freq != b.freq ? Integer.compare(a.freq, b.freq)
                                 : Integer.compare(a.order, b.order));
        int order = 0;
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            heap.offer(new Node(entry.getKey(), entry.getValue(), order++));
        }

        // Merge the two rarest nodes until one tree is left
        while (heap.size() > 1) {
            Node a = heap.poll();
            Node b = heap.poll();
            Node parent = new Node('\0', a.freq + b.freq, order++);
            parent.left = a;
            parent.right = b;
            heap.offer(parent);
        }

        Map<Character, String> codes = new TreeMap<>();
        Node root = heap.poll();
        if (root.isLeaf()) {
            codes.put(root.ch, "0");               // text with only one distinct character
        } else {
            assignCodes(root, "", codes);
        }
        return codes;
    }

    private static void assignCodes(Node node, String path, Map<Character, String> codes) {
        if (node.isLeaf()) {
            codes.put(node.ch, path);
            return;
        }
        assignCodes(node.left, path + "0", codes);
        assignCodes(node.right, path + "1", codes);
    }

    static String encode(String text, Map<Character, String> codes) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(codes.get(c));
        }
        return sb.toString();
    }

    static String decode(String bits, Map<Character, String> codes) {
        Map<String, Character> reverse = new HashMap<>();
        for (Map.Entry<Character, String> e : codes.entrySet()) {
            reverse.put(e.getValue(), e.getKey());
        }
        StringBuilder result = new StringBuilder();
        StringBuilder current = new StringBuilder();
        for (char bit : bits.toCharArray()) {
            current.append(bit);
            Character match = reverse.get(current.toString());
            if (match != null) {               // prefix-free codes: first match is correct
                result.append(match);
                current.setLength(0);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String text = "abracadabra";
        Map<Character, String> codes = buildCodes(text);
        System.out.println("codes: " + codes);

        String encoded = encode(text, codes);
        System.out.println("encoded: " + encoded);
        System.out.println("bits used: " + encoded.length()
                + " (vs " + text.length() * 8 + " with 8-bit characters)");
        System.out.println("decoded: " + decode(encoded, codes));
    }
}
