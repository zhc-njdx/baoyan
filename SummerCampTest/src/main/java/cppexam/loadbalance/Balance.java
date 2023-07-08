package cppexam.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Balance {
    List<Server> servers;

    int previous;

    Strategy balanceStrategy;

    private static final Map<Integer, Strategy> MAP = new HashMap<>();
    public static final Integer STRATEGY_ONE = 1;
    public static final Integer STRATEGY_TWO = 2;
    public static final Integer STRATEGY_THREE = 3;
    public static final Integer STRATEGY_FOUR = 4;

    static {
        MAP.put(STRATEGY_ONE, new StrategyOne());
        MAP.put(STRATEGY_TWO, new StrategyTwo());
        MAP.put(STRATEGY_THREE, new StrategyThree());
        MAP.put(STRATEGY_FOUR, new StrategyFour());
    }



    public Balance(int n, int strategy) {
        previous = 0;
        this.balanceStrategy = MAP.get(strategy);
        servers = new ArrayList<>();
        for (int i = 1; i <= n; i ++) {
            servers.add(new Server(String.valueOf(i)));
        }
    }

    public Balance(int n, int[] weight) {
        this.balanceStrategy = MAP.get(4);
        servers = new ArrayList<>();
        for (int i = 1; i <= n; i ++) {
            servers.add(new Server(String.valueOf(i), weight[i-1]));
        }
    }

    public void balance(Request request) {
        if (servers.size() == 1) { // 只有1台，没有负载均衡
            request.setServer(servers.get(0));
            return;
        }
        Server target = balanceStrategy.balancing(servers, previous);
        request.setServer(target);
        previous = servers.indexOf(target) + 1;
    }
}
