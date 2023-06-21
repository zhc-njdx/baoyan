package datastructure.graph;

import java.util.*;

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

    /**
     * 深度遍历图
     * @return 遍历结果
     */
    public List<Integer> dfsTraversal() {
        boolean[] visited = new boolean[n];
        List<Integer> result = new ArrayList<>();
        dfs(0, visited, result);
        return result;
    }

    private void dfs(int v, boolean[] visited, List<Integer> result) {
        if (visited[v]) return;
        visited[v] = true;
        result.add(v);
        for (Integer nei : graph.get(v)) {
            dfs(nei, visited, result);
        }
    }

    /**
     * 广度遍历图
     * @return 遍历结果
     */
    public List<Integer> bfsTraversal() {
        List<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.add(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i ++) { // 每一层
                Integer node = queue.poll();
                if (node == null) continue;
                result.add(node);
                for (Integer nei : graph.get(node)) {
                    if (!visited[nei]) {
                        visited[nei] = true;
                        queue.add(nei);
                    }
                }
            }
        }
        return result;
    }
}
