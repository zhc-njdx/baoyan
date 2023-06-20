package datastructure.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 无向无权图
 * 用idx表示节点 idx = [0, n-1]
 * 需要额外有一个 idx -> Object 映射
 */
public class UndirectedGraph extends Graph {

    /**
     * 构建无向图
     * @param n 节点的个数
     * @param edges 图的边 edges[i]是[a,b]表示a节点和b节点之间存在一条边
     */
    public UndirectedGraph(int n, int[][] edges) {
        super(n);
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
    }


}
