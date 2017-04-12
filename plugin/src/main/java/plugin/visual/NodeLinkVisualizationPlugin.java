package plugin.visual;

import core.category.Data;
import core.data.AnalysisData;
import core.data.RelationshipData;
import core.plugin.VisualizationPlugin;
import javafx.scene.chart.PieChart;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * take in relationship data and analysis data and plot the relationship data
 * in a node-link graph
 */
public class NodeLinkVisualizationPlugin implements VisualizationPlugin {
    @Override
    public String getDescription() {
        return "Visualizing the relationship data with node-link graph";
    }

    /**
     *
     * @param relationshipData the relationshipData to be visualized
     * @param analysisData the analysisData to be visualized
     * @return a JPanel with the plots inside
     */
    @Override
    public JPanel getVisual(RelationshipData relationshipData, AnalysisData analysisData) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.add(getVisualNodeLink(relationshipData,analysisData));
//        panel.add(getAnalysisPanel(relationshipData,analysisData));
        return  panel;
    }

    @Override
    public String getName() {
        return "Node-Link";
    }

    @Override
    public void onRegister() {
        /* Do Nothing */
    }


    private JPanel getVisualNodeLink(RelationshipData relation, AnalysisData analysis) {
        Graph graph = new SingleGraph("nodeLinkGraph");

        String stylesheet = "node {\n" +
                "\tsize: 3px;\n" +
                "\tfill-color: #777;\n" +
                "}\n" +
                "edge {\n" +
                "\tshape: line;\n" +
                "\tfill-mode: dyn-plain;\n" +
                "\tfill-color: blue,green, red;\n" +
                "\tsize: 3px;\n" +
                "}";

        graph.setStrict(false);

        graph.setAutoCreate(true);

        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet",stylesheet);


        Map<Data,Map<Data,Double>> relationshipMap = relation.getRelationshipMap();

        for (Map.Entry<Data,Map<Data,Double>> entry : relationshipMap.entrySet()) {
            Data dc0 = entry.getKey();
            Map<Data,Double> dc1map = entry.getValue();

            Node n0 = graph.addNode(dc0.toString());
            n0.setAttribute("ui.label",dc0.getName());

            for (Map.Entry<Data,Double> innerEntry : dc1map.entrySet()) {
                Data dc1 = innerEntry.getKey();
                Double strength = innerEntry.getValue();


                Node n1 = graph.addNode(dc1.toString());
                n1.setAttribute("ui.label",dc1.getName());
//                n1.setAttribute("layout.weight",2.0);

                Edge e = graph.addEdge(dc0.toString()+dc1.toString(), dc0.toString(), dc1.toString());

                try{
                    e.addAttribute("ui.color",strength);
                    e.setAttribute("layout.weight",2.0);
                } catch (NullPointerException ne) {
                    /* do nothing since the edge has already been visited */
                }

            }
        }

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        ViewPanel viewPanel = viewer.addDefaultView(false);

        return viewPanel;
    }

    private JPanel getAnalysisPanel(RelationshipData relationshipData, AnalysisData analysisData) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        panel.add(getStrengthDonutPanel(relationshipData,analysisData));
        panel.add(getNumberDonutPanel(relationshipData,analysisData));
        return panel;
    }

    private JPanel getStrengthDonutPanel(RelationshipData relationshipData, AnalysisData analysisData) {
        org.knowm.xchart.PieChart chart = new PieChartBuilder().title("strength based").build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.Label);
        chart.getStyler().setAnnotationDistance(.82);
        chart.getStyler().setPlotContentSize(.9);
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);

        Map<Data,Map<Data,Double>> relationshipMap = relationshipData.getRelationshipMap();
        for(Map.Entry<Data,Map<Data,Double>> entry : relationshipMap.entrySet()) {
            String d = entry.getKey().getName();
            Map<Data,Double> inMap = entry.getValue();
            Double strengthTotal = 0.0;
            for (Map.Entry<Data,Double> innerEntry:inMap.entrySet()) {
                Double r = innerEntry.getValue();
                strengthTotal += r;
            }
            chart.addSeries(d,strengthTotal);
        }
        JPanel panel = new XChartPanel(chart);
        panel.validate();

        return panel;
    }

    private JPanel getNumberDonutPanel(RelationshipData relationshipData, AnalysisData analysisData) {
        org.knowm.xchart.PieChart chart = new PieChartBuilder().title("quantity based").build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.Label);
        chart.getStyler().setAnnotationDistance(.82);
        chart.getStyler().setPlotContentSize(.9);
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);

        Map<Data,Map<Data,Double>> relationshipMap = relationshipData.getRelationshipMap();

        for(Map.Entry<Data,Map<Data,Double>> entry : relationshipMap.entrySet()) {
            String d =entry.getKey().getName();
            int r = entry.getValue().size();
            chart.addSeries(d,r);
        }
        JPanel panel = new XChartPanel(chart);
        panel.validate();

        return panel;
    }





//    public static void main(String[] args) {
//
//        Set<Data> keySet = new HashSet<Data>();
//
//        Data a1 = new Data("A","a1");
//        Data a2 = new Data("A","a2");
//        Data a3 = new Data("A","a3");
//        Data a4 = new Data("A","a4");
//        keySet.add(a1);
//        keySet.add(a2);
//        keySet.add(a3);
//        keySet.add(a4);
//
//
//        RelationshipData rd = new RelationshipData(keySet);
//        rd.addLink(a1,a2,1.0);
//        rd.addLink(a1,a3,0.2);
//
//
//        JFrame frame = new JFrame("in the plugin");
//        frame.setLayout(new BorderLayout());
//
//        NodeLinkVisualizationPlugin nv = new NodeLinkVisualizationPlugin();
//
//        JPanel graphPanel = nv.getVisual(rd,null);
//
////        JPanel graphPanel = nv.getStrengthDonutPanel(rd,null);
//
////        JPanel graphPanel = nv.getNumberDonutPanel(rd,null);
//
////        JPanel graphPanel = nv.getAnalysisPanel(rd,null);
//
//        frame.add(graphPanel,BorderLayout.CENTER);
//
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        frame.setSize(400, 800);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//
////        curFrame.pack();
//        frame.setResizable(true);
//        frame.setVisible(true);
//
//    }
}















