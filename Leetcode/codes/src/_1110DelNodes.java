public class _1110DelNodes {
    static class Solution {
        /**
         public List<TreeNode> delNodes(TreeNode root, int[] toDelete) {
         var ans = new ArrayList<TreeNode>();
         var s = new HashSet<Integer>();
         for (int x : toDelete) s.add(x);
         if (dfs(ans, s, root) != null) ans.add(root);
         return ans;
         }

         private TreeNode dfs(List<TreeNode> ans, Set<Integer> s, TreeNode node) {
         if (node == null) return null;
         node.left = dfs(ans, s, node.left);
         node.right = dfs(ans, s, node.right);
         if (!s.contains(node.val)) return node;
         if (node.left != null) ans.add(node.left);
         if (node.right != null) ans.add(node.right);
         return null;
         }
         **/
    }
}
