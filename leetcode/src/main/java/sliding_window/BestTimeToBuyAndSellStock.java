package sliding_window;

/**
 <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">...</a>
 * Time complexity: O(n)
 * Space complexity: O(1)
 */
public class BestTimeToBuyAndSellStock {

    public static int maxProfit(int[] prices) {
        int buy = 0 ;
        int sell = 1;
        int n = prices.length;
        int maxProfit = 0;

        while (sell < n) {
            if (prices[buy] > prices[sell]) {
                buy = sell;
            } else {
                maxProfit = Math.max(maxProfit, prices[sell] - prices[buy]);
            }
            sell++;
        }

        return maxProfit;
    }

    public static int maxProfit_alternative(int[] prices) {
        int start = -1;
        int end = -1;
        int n = prices.length;
        int maxProfit = 0;

        for (int i = 0; i < n - 1; i++) {
            if (prices[i] <= prices[i + 1]){
                end = i + 1;
                if (start == -1 || prices[i-1] > prices[i] && prices[start] > prices[i]) {
                    start = i;
                }
                maxProfit = Math.max(maxProfit, prices[end] - prices[start]);
            } else if (start != -1 && prices[i-1] < prices[i]) {
                end = i;
                maxProfit = Math.max(maxProfit, prices[end] - prices[start]);
            }
        }
        return maxProfit;
    }
}
