import java.util.HashSet;

public class _1016queryString {
    static class Solution {
        public boolean queryString(String S, int n) {
            HashSet seen = new HashSet<Integer>();
            char[] s = S.toCharArray();
            for (int i = 0, m = s.length; i < m; ++i) {
                int x = s[i] - '0';
                if (x == 0) continue; // 二进制数从 1 开始
                for (int j = i + 1; x <= n; j++) {
                    seen.add(x);
                    if (j == m) break;
                    x = (x << 1) | (s[j] - '0'); // 子串 [i,j] 的二进制数
                }
            }
            return seen.size() == n;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().queryString("0110", 4));
    }
}
