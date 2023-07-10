package datastructure.graph;

import java.util.*;

/**
 * 抽象的图
 * 底层使用邻接表存储
 * 邻接表中的元素表示节点的ID
 * @attention 本实现中默认是针对id范围为[0, n-1]的
 * 如果一个节点需要存储复杂的内容，可以建立ID和对象的映射
 */
public abstract class Graph {
    /**
     * 图的节点的个数
     */
    int n;
    /**
     * 邻接表
     */
    List<List<Integer>> graph;

    public Graph(int n) {
        this.n = n;
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i ++) {
            graph.add(new ArrayList<>());
        }
    }

    /**
     * 针对节点在[1, n]范围做的转换处理
     * @param outside 外部的id
     * @return 内部的id
     */
    private int wrap(int outside) {
        return outside - 1;
    }

    /**
     * 将内部的id转换为外部id
     * @param inside 内部id
     * @return 外部id
     */
    private int unwrap(int inside) {
        return inside + 1;
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
