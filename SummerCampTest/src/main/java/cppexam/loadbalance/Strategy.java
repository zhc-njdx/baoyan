package cppexam.loadbalance;

import java.util.List;

public interface Strategy {
    Server balancing(List<Server> servers, int previous);
}
