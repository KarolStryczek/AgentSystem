package com.agh.as.agent.utils;

import com.agh.as.agent.model.Branch;
import com.agh.as.agent.model.Node;

import java.util.*;

public class AStar {

    public static List<Node> aStar(Node start, Node target) {
        start.setG(0);
        List<Node> result = new ArrayList<>();
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while(!openList.isEmpty()){
            Node n = openList.peek();
            if(n == target){
                Node n1 = n;
                while(n1.parent != null){
                    result.add(n1);
                    n1 = n1.parent;
                }
                result.add(n1);
                Collections.reverse(result);
                return result;
            }

            for(Branch edge : n.getBranches()){
                Node m = edge.getNodes().stream().filter(n1 -> n1.getId() != n.getId()).findFirst().get();
                double totalWeight = n.g + edge.getCost();

                if(!openList.contains(m) && !closedList.contains(m)) {
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    if(totalWeight < m.g) {
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
}
