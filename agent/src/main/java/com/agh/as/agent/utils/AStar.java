package com.agh.as.agent.utils;

import com.agh.as.agent.model.Branch;
import com.agh.as.agent.model.Node;

import java.util.*;

public class AStar {

    public static List<Node> aStar(Node start, Node target) {
        List<Node> result = new ArrayList<>();
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while(!openList.isEmpty()){
            Node n = openList.peek();
            if(n == target){
                result.add(n);
                return result;
            }

            for(Branch edge : n.getBranches()){
                Node m = edge.getNodes().get(0);
                double totalWeight = n.g + edge.getCost();

                if(!openList.contains(m) && !closedList.contains(m)) {
                    result.add(m);
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    if(totalWeight < m.g) {
                        result.add(m);
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.calculateHeuristic(target);

                        if(closedList.contains(m)){
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        return result;
    }

    public static void printPath(Node target){
        Node n = target;

        if(n==null)
            return;

        List<Integer> ids = new ArrayList<>();

        while(n.parent != null){
            ids.add(n.getId());
            n = n.parent;
        }
        ids.add(n.getId());
        Collections.reverse(ids);

        for(int id : ids){
            System.out.print(id + " ");
        }
        System.out.println("");
    }
}
