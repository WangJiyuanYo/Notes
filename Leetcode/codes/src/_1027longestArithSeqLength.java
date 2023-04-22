import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class _1027longestArithSeqLength {
    public int longestArithSeqLength(int[] a) {
        int ans = 0, n = a.length;
        Map<Integer, Integer>[] f = new HashMap[n];
        Arrays.setAll(f, e -> new HashMap<>());
        for (int i = 1; i < n; ++i)
            for (int j = i - 1; j >= 0; --j) {
                int d = a[i] - a[j]; // 公差
                if (!f[i].containsKey(d)) {
                    f[i].put(d, f[j].getOrDefault(d, 1) + 1);
                    ans = Math.max(ans, f[i].get(d));
                }
            }
        return ans;
    }

    public static void main(String[] args) {

    }
}
