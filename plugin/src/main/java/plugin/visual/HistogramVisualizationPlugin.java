package plugin.visual;

import core.category.Data;
import core.data.AnalysisData;
import core.data.RelationshipData;
import core.plugin.VisualizationPlugin;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * take in relationship data and analysis data
 * plot the number of connections of each instance in a histogram
 */
public class HistogramVisualizationPlugin implements VisualizationPlugin {
    @Override
    public String getName() {
        return "Histogram";
    }

    @Override
    public void onRegister() {
        /* do nothing */
    }

    @Override
    public String getDescription() {
        return "the number of connections plotted against the corresponding data entry";
    }

    /**
     *
     * @param relation the relationship data to be visualized
     * @param analysis the analysis data to be visualized
     * @return JPanel instance with the figure inside
     */
    @Override
    public JPanel getVisual(RelationshipData relation, AnalysisData analysis) {
        Map<Data, Map<Data, Double>> relationshipMap = relation.getRelationshipMap();
        List<String> dl = new ArrayList<String>();
        List<Integer> numl = new ArrayList<Integer>();

        for (Map.Entry<Data, Map<Data, Double>> entry : relationshipMap.entrySet()) {
            Data d = entry.getKey();
            Integer num = entry.getValue().size();
            dl.add(d.getName());
            numl.add(num);
        }

        CategoryChart chart =
                new CategoryChartBuilder().xAxisTitle("data").yAxisTitle("number of connections").build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setHasAnnotations(true);

        chart.addSeries("instances vs number of connections", dl, numl);

        JPanel panel = new XChartPanel(chart);
        panel.validate();

        return panel;
    }

    public static void main(String[] args) {
        Set<Data> keySet = new HashSet<Data>();

        Data a1 = new Data("A","a1");
        Data a2 = new Data("A","a2");
        Data a3 = new Data("A","a3");
        Data a4 = new Data("A","a4");
        keySet.add(a1);
        keySet.add(a2);
        keySet.add(a3);
        keySet.add(a4);


        RelationshipData rd = new RelationshipData(keySet);
        rd.addLink(a1,a2,1.0);
        rd.addLink(a1,a3,0.1);

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        HistogramVisualizationPlugin hp = new HistogramVisualizationPlugin();
        frame.add(hp.getVisual(rd,null),BorderLayout.CENTER);
        frame.setSize(400,400);
        frame.setResizable(true);
        frame.setVisible(true);
    }

}
