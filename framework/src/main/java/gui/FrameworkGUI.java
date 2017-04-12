package gui;

import core.framework.DataVisualizationFramework;
import core.framework.FrameworkListener;


import javax.swing.*;

import java.awt.*;


/**
 * Created by Justin on 4/8/2017.
 */
public class FrameworkGUI implements FrameworkListener{

    //The GUI's Frame
    private final JFrame frame;

    //The frame's top menuBar.
    private final JMenuBar menuBar;

    //Menu to choose general things
    private final JMenu fileMenu;
    //Menu to choose which Data plugin to use.
    private final JMenu dataPluginMenu;
    //Menu to choose which Data Visualization plugin to use.
    private final JMenu visualizationPluginMenu;
    //Button to switch between the analysis & visualization views.
    private JButton viewSwitchButton;

    //The Top Panel that wraps all other JPanels.
    private final JPanel topPanel;
    //A Panel that displays information about the three current plugins that are running.
    private JPanel currentPluginInfoPanel;
    //A JLabel that displays the text fo the currentPluginInfoPanel.
    private JLabel pluginInfoLabel;

    //The Panel from the Visualization plugin.
    private JPanel visualizationPanel;
    //The Panel with analysis.
    private JPanel analysisPanel;

    private final DataVisualizationFramework framework;

    private static final String FRAME_TITLE = "Relationship Visualization Framework";
    private static final String FILE_MENU_TITLE = "File";
    private static final String FILE_MENU_NEW = "New Window";
    private static final String FILE_MENU_EXIT = "Exit";

    private static final String MENU_SET_DATA = "Data plugin";
    private static final String MENU_SET_VISUALIZATION = "Visualization plugin";

    public FrameworkGUI(DataVisualizationFramework framework) {
        this.framework = framework;
        framework.setListener(this);

        frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 1000));

        JPanel emptyPanel = new JPanel();
        menuBar = new JMenuBar();
        topPanel = new JPanel();
        analysisPanel = new JPanel();
        topPanel.add(emptyPanel);

        currentPluginInfoPanel = new JPanel();
        pluginInfoLabel = new JLabel();
        currentPluginInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        currentPluginInfoPanel.setPreferredSize(new Dimension(1000, 30));

        topPanel.add(currentPluginInfoPanel);

        //topPanel.add(dataVisualizationPanel);
        fileMenu = new JMenu(FILE_MENU_TITLE);
        addFileMenu();


        dataPluginMenu = new JMenu(MENU_SET_DATA);
        visualizationPluginMenu = new JMenu(MENU_SET_VISUALIZATION);
        addPluginMenus();

        addViewSwitchButton();

        frame.add(topPanel);
        frame.setJMenuBar(menuBar);
        //frame.pack();
        frame.setVisible(true);
    }

    private void addPluginMenus() {
        menuBar.add(dataPluginMenu);
        menuBar.add(visualizationPluginMenu);
    }

    private void addFileMenu() {
        JMenuItem exitMenuItem = new JMenuItem(FILE_MENU_EXIT);
        exitMenuItem.addActionListener(event -> System.exit(0));
        JMenuItem newFrameworkMenuItem = new JMenuItem(FILE_MENU_NEW);

        fileMenu.add(newFrameworkMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
    }

    private void addViewSwitchButton() {
        JPanel blankPanel = new JPanel();
        try {
            viewSwitchButton = new JButton(new ImageIcon("src/main/resources/reverse.png"));
            viewSwitchButton.setBackground(Color.white);
        } catch (Exception e) {
            e.printStackTrace();
            viewSwitchButton = new JButton("Switch View");
        }
        viewSwitchButton.setToolTipText("Switch View");
        viewSwitchButton.addActionListener(event -> {
            for (Component c : topPanel.getComponents()) {
                if (c == visualizationPanel) {
                    topPanel.remove(visualizationPanel);
                    topPanel.add(analysisPanel);
                }
                if (c == analysisPanel) {
                    topPanel.remove(analysisPanel);
                    topPanel.add(analysisPanel);
                }
            }
        });

        //To put viewSwitchButton on the very right of the MenuBar.
        menuBar.add(blankPanel);

        menuBar.add(viewSwitchButton);
    }

    private void updateAnalysisPanel() {
        //do things
    }

    @Override
    public void onNewVisualPluginRegistered() {

    }

    @Override
    public void onNewDataPluginRegistered() {

    }
}
