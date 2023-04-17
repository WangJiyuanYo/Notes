public class _2409countDaysTogether {
    public static void main(String[] args) {
        int daysTogether = new Solution().countDaysTogether("08-15", "08-18", "08-16", "08-19");
        System.out.println(daysTogether);
   }
}

    class Solution {
        private static final int[] START = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};

        public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
            return Math.max(0, Math.min(convert(leaveAlice), convert(leaveBob)) - Math.max(convert(arriveAlice), convert(arriveBob)) + 1);
        }

        private static int convert(String s) {
            return START[(s.charAt(0) - '0') * 10 + s.charAt(1) - '1'] + (s.charAt(3) - '0') * 10 + s.charAt(4) - '1';
        }

    }
