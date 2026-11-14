import java.util.HashMap;
import java.util.Map;

/**
 * Recursion basics. A recursive function has two parts:
 *   1. BASE CASE: when to stop.
 *   2. RECURSIVE CASE: solve a smaller version of the same problem.
 *
 * Each call uses stack space, so recursion depth d costs O(d) space.
 */
public class RecursionBasics {

    // Factorial: n! = n * (n-1)!   O(n) time, O(n) space
    static long factorial(int n) {
        if (n <= 1) {
            return 1;                      // base case
        }
        return n * factorial(n - 1);       // recursive case
    }

    // Sum of digits: O(log n) time (number of digits), O(log n) space
    static int sumOfDigits(int n) {
        if (n < 10) {
            return n;
        }
        return (n % 10) + sumOfDigits(n / 10);
    }

    // Reverse a string: O(n) calls (substring copying makes it O(n^2) overall)
    static String reverse(String s) {
        if (s.length() <= 1) {
            return s;
        }
        return reverse(s.substring(1)) + s.charAt(0);
    }

    // Palindrome check: O(n) time, O(n) space
    static boolean isPalindrome(String s, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        return isPalindrome(s, left + 1, right - 1);
    }

    // Greatest common divisor (Euclid's algorithm): O(log min(a, b)) time
    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Naive Fibonacci: O(2^n) time. Each call spawns two more, recomputing the
    // same values over and over. Fine for tiny n, painful for n = 45.
    static long fibNaive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibNaive(n - 1) + fibNaive(n - 2);
    }

    // Fibonacci with memoisation: remember results already computed.
    // O(n) time, O(n) space. This is the key idea behind dynamic programming.
    static long fibMemo(int n, Map<Integer, Long> memo) {
        if (n <= 1) {
            return n;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        long result = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    // Fast exponentiation: x^n by squaring. O(log n) time, O(log n) space
    static double power(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            return 1 / power(x, -n);
        }
        double half = power(x, n / 2);
        return (n % 2 == 0) ? half * half : half * half * x;
    }

    // Tower of Hanoi: move n disks from `from` to `to` using `via`.
    // Needs 2^n - 1 moves, so O(2^n) time, O(n) space.
    static int hanoi(int n, char from, char to, char via, boolean print) {
        if (n == 0) {
            return 0;
        }
        int moves = hanoi(n - 1, from, via, to, print);
        if (print) {
            System.out.println("Move disk " + n + " from " + from + " to " + to);
        }
        moves++;
        moves += hanoi(n - 1, via, to, from, print);
        return moves;
    }

    public static void main(String[] args) {
        System.out.println("factorial(5): " + factorial(5));
        System.out.println("sumOfDigits(12345): " + sumOfDigits(12345));
        System.out.println("reverse(recursion): " + reverse("recursion"));
        System.out.println("isPalindrome(racecar): " + isPalindrome("racecar", 0, 6));
        System.out.println("gcd(48, 18): " + gcd(48, 18));
        System.out.println("fibNaive(20): " + fibNaive(20));
        System.out.println("fibMemo(50): " + fibMemo(50, new HashMap<>()));
        System.out.println("power(2, 10): " + power(2, 10));
        System.out.println("power(2, -2): " + power(2, -2));

        System.out.println("Tower of Hanoi with 3 disks:");
        int moves = hanoi(3, 'A', 'C', 'B', true);
        System.out.println("total moves: " + moves);
    }
}
