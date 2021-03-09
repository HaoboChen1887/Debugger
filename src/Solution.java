import java.util.*;

public class Solution {
    public String addLargeNumbers(String one, String two) {
        char[] oneArray = one.toCharArray();
        char[] twoArray = two.toCharArray();

        StringBuilder res = new StringBuilder();

        int idxOne = oneArray.length - 1;
        int idxTwo = twoArray.length - 1;
        int carry = 0;
        while (idxOne >= 0 || idxTwo >= 0 || carry != 0) {
            int digitOne = idxOne >= 0 ? oneArray[idxOne--] - '0' : 0;
            int digitTwo = idxTwo >= 0 ? twoArray[idxTwo--] - '0' : 0;
            carry += digitOne + digitTwo;
            res.append((char)((carry % 10) + '0'));
            carry /= 10;
        }

        res.reverse();
        return res.toString();
    }

    public String compress(String input) {
        // Write your solution here
        char[] array = input.toCharArray();

        int start = array.length - 1;
        int fast = array.length - 1;
        while (fast >= 0) {
            int end = fast;
            while (fast >= 0 && array[fast] == array[end]) {
                fast--;
            }
            start = copyDigit(array, start, end - fast);
            array[start--] = array[fast + 1];
        }
        return new String(array, start + 1, array.length - start - 1);
    }

    private int copyDigit(char[] array, int start, int count) {
        if (count > 1) {
            while(count > 0) {
                array[start--] = (char)((count % 10) + '0');
                count /= 10;
            }
        }
        return start;
    }

    public int minCost(int[] stones) {
        // Write your solution here
        // M[i][j] represent the min cost to pileup stones in [i, j];
        // Base Case: M[i][i + 1] = array[i] + array[i + 1];
        // Induction Rule: M[i][j] = min(M[i][k] + M[k + 1][j]), i <= k < j
        final int N = stones.length;
        int[][] minCost = new int[N][N];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i; j < N; j++) {
                if (i == j) {
                    minCost[i][i] = stones[i];
                } else if (j == i + 1) {
                    minCost[i][j] = stones[i] + stones[j];
                } else {
                    minCost[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        if (k == i) {
                            minCost[i][j] = Math.min(minCost[i][j], minCost[i][k] + 2 * minCost[k + 1][j]);
                        } else if (k + 1 == j) {
                            minCost[i][j] = Math.min(minCost[i][j], 2 * minCost[i][k] + minCost[k + 1][j]);
                        } else {
                            minCost[i][j] = Math.min(minCost[i][j], 2 * (minCost[i][k] + minCost[k + 1][j]));
                        }
                    }
                }
            }
        }
        return minCost[0][N - 1];
    }

    public int largestSubGrid(int[][] matrix, int maxSum) {
        final int M = matrix.length;
        final int N = matrix[0].length;
        int[][] prefix = getPrefix(matrix, M, N);
        for (int side = Math.min(M, N); side > 0; side--) {
            int curMax = Integer.MIN_VALUE;
            for (int i = side; i < prefix.length; i++) {
                for (int j = side; j < prefix[0].length; j++) {
                    int sum = prefix[i][j] - prefix[i - side][j] - prefix[i][j - side] + prefix[i - side][j - side];
                    curMax = Math.max(curMax, sum);
                }
            }
            if (curMax != Integer.MIN_VALUE && curMax < maxSum) {
                return side;
            }
        }
        return 0;
    }

    private int[][] getPrefix(int[][] matrix, int M, int N) {
        int[][] prefix = new int[M + 1][N + 1];
        for (int i = 1; i < prefix.length; i++) {
            for (int j = 1; j < prefix[0].length; j++) {
                prefix[i][j] = prefix[i - 1][j] + prefix[i][j - 1] + matrix[i - 1][j - 1] - prefix[i - 1][j - 1];
            }
        }
        return prefix;
    }

    public String wordCompress(String word, int k) {
        return "";
    }

    public String[] sortNameRoman(String[] array) {
        List<Pair> pairs = new ArrayList<>();
        for (String str : array) {
            pairs.add(new Pair(str));
        }

        Collections.sort(pairs);
        for (int i = 0; i < array.length; i++) {
            Pair cur = pairs.get(i);
            array[i] = cur.name + " " + cur.roman;
        }
        return array;
    }

    private class Pair implements Comparable<Pair> {
        String name;
        String roman;
        int value;

        private Pair(String pair) {
            String[] split = pair.split(" ");
            name = split[0];
            roman = split[1];
            value = toDecimal(split[1]);
        }

        private int toDecimal(String roman) {
            int result = 0;
            int i = 0;
            for (i = 0; i < roman.length() - 1; i++) {
                int cur = convert(roman.charAt(i));
                int next = convert(roman.charAt(i + 1));
                if (cur < next) {
                    cur = next - cur;
                    i++;
                }
                result += cur;
            }
            if (i == roman.length() - 1) {
                result += convert(roman.charAt(i));
            }
            return result;
        }

        private int convert(char ch) {
            if (ch == 'I')
                return 1;
            if (ch == 'V')
                return 5;
            if (ch == 'X')
                return 10;
            if (ch == 'L')
                return 50;
            if (ch == 'C')
                return 100;
            if (ch == 'D')
                return 500;
            if (ch == 'M')
                return 1000;

            return -1;
        }

        @Override
        public int compareTo(Pair other) {
            int comp = this.name.compareTo(other.name);
            if (comp == 0) {
                if (this.value == other.value) {
                    return 0;
                }
                return this.value < other.value ? -1 : 1;
            }

            return comp;
        }
    }
    // DP
    // 1. cut dictionary
    public boolean cutDict(String input, List<String> dict) {
        // M[i] represents whether input's substring ending at i have a valid cut
        // Base Case: M[0] = true
        // Induction Rule: M[i] = (Any of M[j] is true, 0<=j<i) && substring[j, i) is in dict
        Set<String> set = getSet(dict);
        boolean[] M = new boolean[input.length() + 1];
        // Base Case: set to true because later calculation will refer to this value
        M[0] = true;
        for (int i = 1; i < M.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (M[j] == true && set.contains(input.substring(j, i))) {
                    M[i] = true;
                    break;
                }
            }
        }
        return M[M.length - 1];
    }

    private Set<String> getSet(List<String> dict) {
        Set<String> set = new HashSet<>();
        for (String s : dict) {
            set.add(s);
        }
        return set;
    }

    // 2. cut palindrome
    public int cutPalindrome(String input) {
        // M[i] represents minimum number of cuts needed for substring ending so that all segment are palindromes
        // Base Case: M[0] = 0 because single letter is a palindrome
        // Induction Rule: Case 1: substring[0, i + 1) is palindrome: M[i] = 0
        //                 Case 2: M[i] = Min(M[j] + 1, 0<=j<i, if substring(j, i + 1) is palindrome)
        int[] minCut = new int[input.length()];
        for (int i = 1; i < minCut.length; i++) {
            if (isPalindrome(input, 0, i)) {
                minCut[i] = 0;
            } else {
                minCut[i] = input.length();
                for (int j = 0; j < i; j++) {
                    if (isPalindrome(input, j, i)) {
                        minCut[i] = Math.min(minCut[i], minCut[j] + 1);
                    }
                }
            }
        }
        return minCut[minCut.length - 1];
    }

    private boolean isPalindrome(String input, int left, int right) {
        while (left < right) {
            if (input.charAt(left++) != input.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    // 3. cut squares
    public int minPack(int swags) {
        // M[i] represents the minimum number of boxes to pack i swags
        // Base Case: M[0] = 0;
        // Induction Rule: M[i] = Min(M[i - j * j] + 1, 0 < j * j <= i)
        int[] minBox = new int[swags + 1];
        for (int i = 1; i < minBox.length; i++) {
            minBox[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minBox[i] = Math.min(minBox[i - j * j] + 1, minBox[i]);
            }
        }
        return minBox[swags];
    }

    // DFS
    // 1. DFS: all schedule
    public List<String> allSchedule(String univ) {
        List<String> result = new ArrayList<>();
        if (univ == null || univ.length() == 0) {
            return result;
        }
        StringBuilder sb = new StringBuilder();
        dfs(univ, sb, 0, result);
        return result;
    }

    private void dfs(String univ, StringBuilder sb, int idx, List<String> result) {
        if (idx == univ.length()) {
            if (sb.charAt(sb.length() - 1) != 'x') {
                result.add(sb.toString());
            }
            return;
        }

        sb.append(univ.charAt(idx));
        dfs(univ, sb, idx + 1, result);

        sb.append('x');
        dfs(univ, sb, idx + 1, result);
        sb.deleteCharAt(sb.length() - 1);

        sb.deleteCharAt(sb.length() - 1);
    }

    // 2. DFS: round table
    public boolean canSitLoop(String[] names) {
        return helper(names, 1);
    }

    private boolean helper(String[] names, int idx) {
        if (idx == names.length) {
            return canSit(names[names.length - 1], names[0]);
        }

        for (int i = idx; i < names.length; i++) {
            if (canSit(names[idx - 1], names[i])) {
                swap(names, idx, i);
                if (helper(names, idx + 1)) {
                    return true;
                }
                swap(names, idx, i);
            }
        }
        return false;
    }

    private boolean canSit(String s1, String s2) {
        return s1.charAt(s1.length() - 1) == s2.charAt(0);
    }

    private void swap(String[] names, int left, int right) {
        String temp = names[left];
        names[left] = names[right];
        names[right] = temp;
    }

    // final
    // 1.
//    public List<String> allSchedule(String univ) {
//        List<String> result = new ArrayList<>();
//        if (univ == null || univ.length() == 0) {
//            return result;
//        }
//        StringBuilder sb = new StringBuilder();
//        char[] array = univ.toCharArray();
//        dfs(array, 0, sb, result);
//        return result;
//    }
//
//    private void dfs(char[] array, int idx, StringBuilder sb, List<String> result) {
//        if (idx == array.length) {
//            if (sb.charAt(sb.length() - 1) != 'x') {
//                result.add(sb.toString());
//            }
//            return;
//        }
//
//        sb.append(array[idx]);
//        dfs(array, idx + 1, sb, result);
//
//        sb.append('x');
//        dfs(array, idx + 1, sb, result);
//        sb.deleteCharAt(sb.length() - 1);
//
//        sb.deleteCharAt(sb.length() - 1);
//    }
//
//    // 2.
//    public boolean isCousin(TreeNode root, TreeNode a, TreeNode b) {
//        Queue<TreeNode> queue = new ArrayDeque<>();
//        queue.offer(root);
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            int found = 0;
//            for (int i = 0; i < size; i++) {
//                TreeNode cur = queue.poll();
//                if (containsBoth(cur, a, b)) {
//                    return false;
//                }
//
//                if (cur.left != null) {
//                    if (cur.left != null && (cur.left == a || cur.left == b)) {
//                        found++;
//                    }
//                    queue.offer(cur.left);
//                }
//                if (cur.right != null) {
//                    if (cur.right != null && (cur.right == a || cur.right == b)) {
//                        found++;
//                    }
//                    queue.offer(cur.right);
//                }
//
//                if (found == 2) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean containsBoth(TreeNode node, TreeNode a, TreeNode b) {
//        return node.left != null && node.right != null &&
//               ((node.left == a && node.right == b) ||
//               (node.left == b && node.right == a));
//    }
//
//    // 3.
//    public int minPack(int swag) {
//        // M[i] represents the minimum number of boxes to pack i swags
//        // M[0] = 0, 0 boxes are needed to pack 0 swag
//        // M[i] = min(M[i - j * j] for all j, 0 < j*j < i)
//        // 0 1 2 3 4 5 6 7 8 9 10
//        // 0 1 2 3 4
//        int[] minBox = new int[swag + 1];
//        for (int i = 1; i < minBox.length; i++) {
//            minBox[i] = Integer.MAX_VALUE;
//            for (int j = 1; j * j <= i; j++) {
//                minBox[i] = Math.min(minBox[i], minBox[i - j * j] + 1);
//            }
//        }
//        return minBox[swag];
//    }
//
//    // 4.
//    public boolean canSit(String[] names) {
//        return canSit(names, 1);
//    }
//
//    private boolean canSit(String[] names, int idx) {
//        if (idx == names.length) {
//            return isValid(names[names.length - 1], names[0]);
//        }
//
//        for (int i = idx; i < names.length; i++) {
//            if (isValid(names[idx - 1], names[i])) {
//                swap(names, idx, i);
//                if (canSit(names, idx + 1)) {
//                    return true;
//                }
//                swap(names, idx, i);
//            }
//        }
//        return false;
//    }
//
//    private boolean isValid(String first, String second) {
//        return first.charAt(first.length() - 1) == second.charAt(0);
//    }
//
//    private void swap (String[] names, int left, int right) {
//        String temp = names[left];
//        names[left] = names[right];
//        names[right] = temp;
//    }

    // midterm
    public int maxPathNodeToNode(TreeNode root) {
        int[] largest = new int[] {Integer.MIN_VALUE};
        maxPathNodeToNode(root, largest);
        return largest[0];
    }

    private int maxPathNodeToNode(TreeNode root, int[] largest) {
        if (root == null) {
            return 0;
        }

        // 1. ask children for maximum of the non-branching subpath
        int leftPath = maxPathNodeToNode(root.left, largest);
        int rightPath = maxPathNodeToNode(root.right, largest);

        // 2. update global max
        // for every node choose max from
        //  1. non-branching left path ending at current node
        //  2. non-branching right path ending at current node
        //  3. branching path (left + right + root.key)
        //  4. root.key
        largest[0] = Math.max(largest[0], rightPath + root.key);
        largest[0] = Math.max(largest[0], leftPath + root.key);
        largest[0] = Math.max(largest[0], leftPath + root.key + rightPath);
        largest[0] = Math.max(largest[0], root.key);

        // 3. return the largest one of
        //  1. non-branching left path
        //  2. non-branching right path
        //  3. root.key
        int ret = Math.max(rightPath + root.key, leftPath + root.key);
        return Math.max(ret, root.key);
    }

    public Integer maxPathLeafToLeaf(TreeNode root) {
        Integer[] largest = new Integer[] {Integer.MIN_VALUE};
        maxPathLeafToLeaf(root, largest);
        return largest[0] == Integer.MIN_VALUE ? null : largest[0];
    }

    private Integer maxPathLeafToLeaf(TreeNode root, Integer[] largest) {
        if (root == null) {
            return null;
        }

        // 1. Ask children for sum of largest leaf-to-self path
        Integer leftPath = maxPathLeafToLeaf(root.left, largest);
        Integer rightPath = maxPathLeafToLeaf(root.right, largest);

        // 2. if a valid leaf-to-self path exist in both left and right subtree
        //      update global max when necessary with (left + right + root.key)


        // 3. need to consider 4 cases when returning
        //          root            root            root            root
        //      left    right   left    null    null    right   null    null
        //      1. return max(left, right) + root.key
        //      2, 3. return non-null path + root.key
        //      4. return root.key;
        if (leftPath != null && rightPath != null) {
            largest[0] = Math.max(largest[0], leftPath + root.key + rightPath);
            return Math.max(leftPath + root.key, rightPath + root.key);
        } else if (leftPath == null && rightPath == null) {
            return root.key;
        }

        return leftPath == null ? rightPath + root.key : leftPath + root.key;
    }

//    public boolean cutDictionary(String str, String[] dict) {
//        // Assume string is not null and string.length >= 1
//
//        // use a memorization array M[str.length + 1]
//        // M[i] reprensents whether substring [0, i - 1] can form a valid cut
//        // Base case: M[0], representing an empty string and should be set to true
//        //            because later computations are dependent on it
//        // Induction Rule: for every substring [0, i - 1], we need to consider
//        //                 if there exist a position j: M[0, j] == true (substring[0,j] can be cut)
//        //                                           && substring[j + 1, i - 1] is in dict
//        //
//
//        Set<String> set = getSet(dict);
//        boolean[] canBreak = new boolean[str.length() + 1];
//        canBreak[0] = true;
//        for (int i = 1; i < canBreak.length; i++) {
//            // cut [0, j], [j + 1, i - 1];
//            for (int j = i - 1; j >= 0; j--) {
//                if (canBreak[j] && set.contains(str.substring(j, i))) {
//                    canBreak[i] = true;
//                    break;
//                }
//            }
//        }
//        return canBreak[canBreak.length - 1];
//    }
//
//    private Set<String> getSet(String[] dict) {
//        Set<String> set = new HashSet<String>();
//        for (String s : dict) {
//            set.add(s);
//        }
//        return set;
//    }
//
//    public int minCutPalindrome(String str) {
//        // Assume string is not null and string.length >= 1
//
//        // use a memorization array M[str.length]
//        // M[i] stores the minimum number of cuts so that substring[0, i] can be cut into palindromes
//        // Base case: M[0] = 0, represent single char is a palindrome without cut
//        // Induction rule: if substring[0, i] is a palindrome, set M[i] = 0
//        //                 otherwise:
//        //                  for every substring[0, i]
//        //                  for every position j from [0, i]
//        //                  check if there exist one combination of substring[0, j], [j + 1, i - 1]
//        //                  such that: substring[j + 1, i - 1] is a palindrome
//        //                      set M[i] = min(M[i], M[j] + 1)
//        int[] minCut = new int[str.length()];
//        for (int i = 1; i < minCut.length; i++) {
//            if (isPalindrome(str, 0, i)) {
//                minCut[i] = 0;
//            } else {
//                for (int j = i - 1; j >= 0; j--) {
//                    if (isPalindrome(str, j + 1, i - 1)) {
//                        minCut[i] = Math.min(minCut[i], minCut[j] + 1);
//                    }
//                }
//            }
//        }
//        return minCut[minCut.length - 1];
//    }
//
//    private boolean isPalindrome(String str, int left, int right) {
//        while (left < right) {
//            if (str.charAt(left++) != str.charAt(right--)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
