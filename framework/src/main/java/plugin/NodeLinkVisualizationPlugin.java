package plugin;

import core.category.CategoryCollection;
import core.category.DataCategory;
import core.data.AnalysisData;
import core.data.RelationshipData;
import core.plugin.VisualizationPlugin;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianyugu on 4/11/17.
 */
public class NodeLinkVisualizationPlugin implements VisualizationPlugin {
    @Override
    public String getDescription() {
        return "Visualizing the relationship data with node-link graph";
    }

    @Override
    public JPanel getVisual(RelationshipData relation, AnalysisData analysis) {
        Graph graph = new SingleGraph("nodeLinkGraph");

        String stylesheet = "edge {\n" +
                "\tshape: line;\n" +
                "\tfill-mode: dyn-plain;\n" +
                "\tfill-color: white,yellow, red;\n" +
                "\tarrow-size: 50px, 1px;\n" +
                "}";

        graph.setStrict(false);

        graph.setAutoCreate(true);

        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet",stylesheet);

        Map<DataCategory,Map<DataCategory,Double>> relationshipMap = relation.getRelationshipMap();

        for (Map.Entry<DataCategory,Map<DataCategory,Double>> entry : relationshipMap.entrySet()) {
            DataCategory dc0 = entry.getKey();
            Map<DataCategory,Double> dc1map = entry.getValue();

            Node n0 = graph.addNode(dc0.toString());
            n0.setAttribute("ui.label",dc0.getName());

            for (Map.Entry<DataCategory,Double> innerEntry : dc1map.entrySet()) {
                DataCategory dc1 = innerEntry.getKey();
                Double strength = innerEntry.getValue();


                Node n1 = graph.addNode(dc1.toString());
                n1.setAttribute("ui.label",dc1.getName());

                Edge e = graph.addEdge(dc0.toString()+dc1.toString(), dc0.toString(), dc1.toString());
                try{
                    e.addAttribute("ui.color",strength);
                } catch (NullPointerException ne) {
                    //
                }

            }
        }

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        ViewPanel viewPanel = viewer.addDefaultView(false);

        return viewPanel;
    }

    public static void main(String[] args) {

        Set<DataCategory> keySet = new HashSet<DataCategory>();

        DataCategory a1 = new DataCategory("A","a1");
        DataCategory a2 = new DataCategory("A","a2");
        DataCategory a3 = new DataCategory("A","a3");
        DataCategory a4 = new DataCategory("A","a4");
        keySet.add(a1);
        keySet.add(a2);
        keySet.add(a3);
        keySet.add(a4);


        RelationshipData rd = new RelationshipData(keySet);
        rd.addLink(a1,a2,1.0);
        rd.addLink(a1,a3,0.1);


        JFrame frame = new JFrame("in the plugin");
        frame.setLayout(new BorderLayout());

        NodeLinkVisualizationPlugin nv = new NodeLinkVisualizationPlugin();

        JPanel graphPanel = nv.getVisual(rd,null);

        frame.add(graphPanel,BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        curFrame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

    }
}















