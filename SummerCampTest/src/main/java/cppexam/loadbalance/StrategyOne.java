package cppexam.loadbalance;

import java.util.List;

public class StrategyOne implements Strategy{

    @Override
    public Server balancing(List<Server> servers, int previous) {
        return servers.get(balance0(servers.size(), previous) - 1);
    }

    private int balance0(int total, int previous) {
        if (previous == total) return 1;
        return previous + 1;
    }
}
