package datastructure.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DirectedGraph extends Graph{

    /**
     * 构建有向图
     * @param n 节点的个数
     * @param edges 图的边 edges[i]是[a,b]表示a节点和b节点之间存在一条边
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
