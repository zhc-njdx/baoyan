package cppexam.loadbalance;

import java.util.List;

public class StrategyThree implements Strategy{
    @Override
    public Server balancing(List<Server> servers, int previous) {
        return servers.get(balance0(servers.size(), previous) - 1);
    }

    private int balance0(int total, int previous) {
        if (previous % 2 == 0) { // 偶数序列
            if (previous + 2 > total) {
                return 1;
            }
        } else { // 基数序列
            if (previous + 2 > total) {
                return 2;
            }
        }
        return previous + 2;
    }
}
