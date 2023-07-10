package datastructure.graph;

/**
 * 有向无权图
 * 用idx表示节点 idx = [0, n-1]
 * 需要额外有一个 idx -> Object 映射
 */
public class DirectedGraph extends Graph{

    /**
     * 构建有向图
     * @param n 节点的个数
     * @param edges 图的边 edges[i]是[a,b]表示从a节点到b节点的一条边
     */
    public DirectedGraph(int n, int[][] edges) {
        super(n);
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            graph.get(from).add(to);
        }
    }

}
