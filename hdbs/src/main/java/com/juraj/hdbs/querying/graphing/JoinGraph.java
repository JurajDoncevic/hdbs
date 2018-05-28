package com.juraj.hdbs.querying.graphing;

import com.juraj.hdbs.querying.queryComponents.Join;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** A graph class that represents all joins in a query - G(V,E) = G(tables, joins).
 * Uses JGrapht
 * @author Juraj
 */
public class JoinGraph {

    private List<Join> joins;
    private DefaultDirectedGraph<String, DefaultEdge> directedGraph;


    /** Constructor
     * @param joins List of all joins in a query
     */
    public JoinGraph(List<Join> joins) {
        this.joins = joins;
        directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        HashSet<String> tableIds = new HashSet<>();

        for (Join join : joins){
            tableIds.add(join.getPrimaryKeyTableId());
            tableIds.add(join.getForeignKeyTableId());
        }

        for (String tableId : tableIds){
            directedGraph.addVertex(tableId);
        }

        for (Join join : joins){
            //if (!join.getPrimaryKeyTableId().equals(join.getForeignKeyTableId()))//ignore local cycle joins - cycle len 1
                directedGraph.addEdge(join.getPrimaryKeyTableId(), join.getForeignKeyTableId());

        }

    }

    /** Checks join validity - no cycles, connected graph
     * @return True on valid; else False
     */
    public boolean isJoinGraphValid(){
        CycleDetector cycleDetector = new CycleDetector(directedGraph);

        if (cycleDetector.detectCycles())
            return false;

        ConnectivityInspector connectivityInspector = new ConnectivityInspector(directedGraph);
        if (!connectivityInspector.isGraphConnected())
            return false;

        return true;
    }
}
