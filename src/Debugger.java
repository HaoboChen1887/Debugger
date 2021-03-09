import java.util.ArrayList;
import java.util.List;

public class Debugger {
    public static void main(String[] args) {
//        int k = 3;
//        int[] array = new int[] {3, 4, 1, 5, 6, 1, 1};
//        FindKthSmallest sol = new FindKthSmallest();
//        System.out.println(sol.findKthSmallest(array, k));
        Integer[] array1 = new Integer[] {8,5,13,3,6,9,17,2,4,null,7,null,11,15,null,1,null,null,null,null,null,null,null};
        Integer[] array2 = new Integer[] {0,1,3,3,4,5,5,7,7,9,11,11,13,13,18,19,22,22,22,23,23};
        int[] intArray1 = new int[] {1,2,3,4,5,6};
        int[] intArray2 = new int[] {0,1,3,3,4,5,5,7,7,9,11,11,13,13,18,19,22,22,22,23,23};
        double[] doubleArray = new double[] {-1.0};
        Point[] pointArray = new Point[] {new Point(0, 0), new Point(1, 1), new Point(1, 2)};

        int[][] matrix = new int[][] {{1,0,0,1,1,0,1,1},{1,0,0,0,0,1,0,0},{0,1,1,1,0,0,1,1},{0,0,0,1,0,0,0,1},{0,0,0,0,0,1,1,1},{1,1,1,1,1,1,1,1},{1,0,0,1,0,1,1,0},{0,1,1,0,1,1,1,0}};
        double[][] doubleMatrix = new double[][] {{1,-0.2,3,2}, {-4,1,-1,1}, {-3,2,2,2}, {-1,1,1,1}};
//        char[][] charMatrix = new char[][] {{'C','C','E','O','C'},{'C','C','O','C','E'},{'C','C','E','E','C'},{'C','O','C','E','E'},{'C','C','O','C','C'}};
        char[][] charMatrix = new char[][] {{'b','c','e','e','d'},{'b','b','e','a','e'},{'e','b','c','c','a'},{'a','c','e','c','c'},{'a','b','c','d','c'}};
        //char[][] charMatrix = new char[][] {{'b','a'},{'b','c'},{'a','e'},{'d','d'}};
//        Integer[] array = new Integer[] {11, 1, null, null, -1, -9, 10, -5, 6, -7, null, null, -6, 15};

        List<Integer> list1 = new ArrayList<>();
        for (Integer num : array1) {
            list1.add(num);
        }
        List<Integer> list2 = new ArrayList<>();
        for (Integer num : array2) {
            list2.add(num);
        }

        TreeNode testRoot1 = BuildTreeFromArray.build(list1);
        TreeNode testRoot2 = BuildTreeFromArray.build(list2);


        String str1 = "22222222222222222222";
        String str2 = "3333333333333333333378";
        String str3 = "xxx";

        String[] strArray1 = new String[] {"Susan L","Max VI","Susan IX"};
        List<String> strList = new ArrayList<>();
        for (String s : strArray1) {
            strList.add(s);
        }

        Solution solution = new Solution();
//        solution.removeSpaces(array1, array2);
        System.out.println(solution.addLargeNumbers(str1, str2));
//        PostOrder solution = new PostOrder();
//        List<Integer> res = solution.postOrder(testRoot);
//        System.out.println(res.toString());
    }
}
