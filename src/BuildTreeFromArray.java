import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class BuildTreeFromArray {
    public static TreeNode build(List<Integer> array) {
        if (array == null || array.size() < 1 || array.get(0) == null) {
            return null;
        }

        int idx = 0;
        TreeNode root = new TreeNode(array.get(idx++));

        Queue<TreeNode> curLevel = new ArrayDeque<>();
        curLevel.offer(root);
        while (!curLevel.isEmpty()) {
            int curWidth = curLevel.size();
            for (int i = 0; i < curWidth; i++) {
                TreeNode cur = curLevel.poll();
                if (cur != null) {
                    if (idx >= array.size() || array.get(idx) == null) {
                        cur.left = null;
                        idx++;
                    } else {
                        cur.left = new TreeNode(array.get(idx++));
                    }
                    if (idx >= array.size() || array.get(idx) == null) {
                        cur.right = null;
                        idx++;
                    } else {
                        cur.right = new TreeNode(array.get(idx++));
                    }

                    if (cur.left != null) {
                        curLevel.offer(cur.left);
                    }
                    if (cur.right != null) {
                        curLevel.offer(cur.right);
                    }
                }
            }
        }
        return root;
    }
}
