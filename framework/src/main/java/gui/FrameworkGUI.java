package gui;

import sun.font.FontManagerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Justin on 4/8/2017.
 */
public class FrameworkGUI {

    //The GUI's Frame
    private final JFrame frame;

    //Menu to choose which Data plugin to use.
    private final JMenu dataPluginMenu;
    //Menu to choose which Data Visualization plugin to use.
    private final JMenu visualizationPluginMenu;
    //Menu to choose which Analysis plugin to run.
    private final JMenu analysisPluginMenu;

    //The Top Panel that wraps all other JPanels.
    private final JPanel topPanel;
    //A Panel that displays information about the three current plugins that are running.
    private final JPanel currentPluginInfoPanel;
    //A JLabel that displays the text fo the currentPluginInfoPanel.
    private final JLabel pluginInfoLabel;
    //The Panel from the Visualization Panel.
    //private final JPanel visualizationPanel;


    private static final String FRAME_TITLE = "Relationship Visualization Framework";

    private static final String FILE_MENU_TITLE = "File";
    private static final String FILE_MENU_NEW = "New Window";
    private static final String FILE_MENU_EXIT = "Exit";
    private static final String MENU_SET_DATA = "Data plugin";
    private static final String MENU_SET_VISUALIZATION = "Visualization plugin";
    private static final String MENU_SET_ANALYSIS = "Analysis plugin";


    //List <AnalysisPlugin>
    //List <VisPlugin>
    //List <DataPlugin>
    //DataVisFramework core
    //DataPlugin currentDataPlugin
    //VisualizationPlugin currentVisualizationPlugin
    //AnalysisPlugin currentAnalysisPlugin


    public FrameworkGUI() {
        frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        topPanel = new JPanel();

        currentPluginInfoPanel = new JPanel();
        pluginInfoLabel = new JLabel();
        currentPluginInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        currentPluginInfoPanel.setPreferredSize(new Dimension(1000, 30));

        topPanel.add(currentPluginInfoPanel);

        //topPanel.add(dataVisualizationPanel);


        dataPluginMenu = new JMenu(MENU_SET_DATA);
        visualizationPluginMenu = new JMenu(MENU_SET_VISUALIZATION);
        analysisPluginMenu = new JMenu(MENU_SET_ANALYSIS);

        JMenuItem exitMenuItem = new JMenuItem(FILE_MENU_EXIT);
        exitMenuItem.addActionListener(event -> System.exit(0));

        JMenuItem newFrameworkMenuItem = new JMenuItem(FILE_MENU_NEW);
        //newFrameworkMenuItem.addActionListener(); Make a new everything w/ multithreading?

        JMenu fileMenu = new JMenu(FILE_MENU_TITLE);
        fileMenu.add(newFrameworkMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(dataPluginMenu);
        menuBar.add(visualizationPluginMenu);
        menuBar.add(analysisPluginMenu);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }
}
