package cppexam.loadbalance;

import java.util.Comparator;
import java.util.List;

public class StrategyFour implements Strategy {
    @Override
    public Server balancing(List<Server> servers, int previous) {
        Server target = servers.stream()
                .sorted((s1, s2) -> s2.weight - s1.weight)
                .filter(s -> s.need != 0)
                .findFirst().orElse(null);
        if (target == null) {
            servers.forEach(s -> s.need = s.weight);
            target = servers.stream().max(Comparator.comparingInt(s -> s.weight)).orElse(null);
            assert target != null;
        }
        target.need --;
        return target;
    }
}
