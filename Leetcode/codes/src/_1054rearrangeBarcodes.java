import java.util.*;

public class _1054rearrangeBarcodes {
    static class Solution {
        public int[] rearrangeBarcodes(int[] barcodes) {
            // 有点偏设计的题目操作
            int n = barcodes.length;
            int[] ans = new int[n];
            int[] cnt = new int[10000+1];
            for(int num:barcodes) cnt[num]++;
            Queue<int[]> queue = new PriorityQueue<int[]>((a, b)->{
                if(a[1]==b[1]) {
                    return a[0]-b[0];
                } else {
                    return b[1]-a[1];
                }
            }){};
            for(int i = 1;i<=10000;i++) {
                if(cnt[i]!=0) queue.add(new int[]{i, cnt[i]});
            }
            // 开始进行构造了，这道题目就是典型的构造题目的案例操作
            int index = 0;
            while(!queue.isEmpty()) {
                int[] poll1 = queue.poll();
                int[] poll2 = queue.poll();
                ans[index++] = poll1[0];
                poll1[1]--;
                if(poll1[1]!=0) queue.add(poll1);
                if(poll2!=null) {
                    ans[index++] = poll2[0];
                    poll2[1]--;
                    if(poll2[1]!=0) queue.add(poll2);
                }
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        int[] rearrangedBarcodes = new Solution().rearrangeBarcodes(new int[]{1, 1, 1, 1, 2, 2, 3, 3});
        for (int i : rearrangedBarcodes) {
            System.out.print(i + " ");
        }
    }
}
