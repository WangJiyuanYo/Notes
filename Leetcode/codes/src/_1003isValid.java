public class _1003isValid {
    static class Solution {
        public boolean isValid(String s) {
            char[] array = s.toCharArray();
            int i = 0;
            for (char c : array) {
                if (c > 'a' && (i == 0 || c - array[--i] != 1)) {
                    return false;
                }
                if (c < 'c') {
                    array[i++] = c;
                }
            }
            return i == 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().isValid("abcabcababcc"));
    }
}
