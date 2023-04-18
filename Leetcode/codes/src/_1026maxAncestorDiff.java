public class _1026maxAncestorDiff {
    class Solution {
        private int ans;

        public int maxAncestorDiff(TreeNode root) {
            dfs(root, root.val, root.val);
            return ans;
        }

        private void dfs(TreeNode node, int mn, int mx) {
            if (node == null) {
                ans = Math.max(ans, mx - mn);
                return;
            }
            mn = Math.min(mn, node.val);
            mx = Math.max(mx, node.val);
            dfs(node.left, mn, mx);
            dfs(node.right, mn, mx);
        }
    }

    public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
 }
}

