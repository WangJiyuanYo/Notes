public class _2446haveConflict {

    static class Solution {
        public boolean haveConflict(String[] event1, String[] event2) {
            return !(event1[0].compareTo(event2[1]) > 0 || event1[1].compareTo(event2[0]) < 0);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().haveConflict(new String[]{"01:15","02:00"},new String[]{"02:00","03:00"}));
    }
}
