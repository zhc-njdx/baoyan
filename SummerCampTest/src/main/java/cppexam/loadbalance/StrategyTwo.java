package cppexam.loadbalance;

import java.util.List;

public class StrategyTwo implements Strategy {

    private boolean asc = true;
    @Override
    public Server balancing(List<Server> servers, int previous) {
        return servers.get(balance0(servers.size(), previous) - 1);
    }

    private int balance0(int total, int previous) {
        if (asc) {
            if (previous == total) {
                asc = false;
                return previous - 1;
            }
            return previous + 1;
        } else {
            if (previous == 1) {
                asc = true;
                return previous + 1;
            }
            return previous - 1;
        }
    }
}
