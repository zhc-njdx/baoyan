package datastructure.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    int n;
    List<List<Integer>> graph;

    public Graph(int n) {
        this.n = n;
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i ++) {
            graph.add(new ArrayList<>());
        }
    }

    /**
     * 判断src和dst之间是否存在路径
     * @param src 开始节点
     * @param dst 结束节点
     * @return 是否存在路径
     */
    public boolean hasPath(int src, int dst) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        visited[src] = true;
        while(!queue.isEmpty()) {
            Integer node = queue.poll();
            if (node == dst) return true;
            for (Integer nei : graph.get(node)) {
                if (!visited[nei]) {
                    queue.add(nei);
                    visited[nei] = true;
                }
            }
        }
        return false;
    }
}
