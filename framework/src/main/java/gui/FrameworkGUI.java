package gui;

import core.framework.DataVisualizationFramework;
import core.framework.FrameworkListener;
import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;


import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;


/**
 * Created by Justin on 4/8/2017.
 */
public class FrameworkGUI implements FrameworkListener{

    //The GUI's Frame
    private final JFrame frame;

    //The frame's top menuBar.
    private final JMenuBar menuBar;

    private final JMenu categoryMenu;
    private final JMenu nodeMenu;
    private final JMenu linkMenu;
    //Menu to choose general things
    private final JMenu fileMenu;
    //Menu to choose which Data plugin to use.
    private final JMenu dataPluginMenu;
    private final ButtonGroup dataPluginGroup;
    private final ButtonGroup visualPluginGroup;
    private final ButtonGroup nodeButtonGroup;
    private final ButtonGroup linkButtonGroup;


    //Menu to choose which Data Visualization plugin to use.
    private final JMenu visualizationPluginMenu;
    //Button to switch between the analysis & visualization views.
    private JButton viewSwitchButton;

    //The Top Panel that wraps all other JPanels.
    private final JPanel topPanel;

    //The Panel from the Visualization plugin.
    private JPanel visualizationPanel;
    //The Panel with analysis.
    private JPanel analysisPanel;

    private final DataVisualizationFramework framework;

    private static final String FRAME_TITLE = "Relationship Visualization Framework";
    private static final String CATEGORY_MENU_TITLE = "Data Category";
    private static final String NODE_MENU_TITLE = "Choose Node Category...";
    private static final String LINK_MENU_TITLE = "Choose Link Category...";
    private static final String FILE_MENU_TITLE = "File";
    private static final String FILE_MENU_NEW = "New Window";
    private static final String FILE_MENU_EXIT = "Exit";
    private static final String FILE_MENU_START = "Apply Plugins";
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

        categoryMenu = new JMenu(CATEGORY_MENU_TITLE);
        nodeMenu = new JMenu(NODE_MENU_TITLE);
        linkMenu = new JMenu(LINK_MENU_TITLE);
        nodeButtonGroup = new ButtonGroup();
        linkButtonGroup = new ButtonGroup();
        categoryMenu.setEnabled(false);
        categoryMenu.add(nodeMenu);
        categoryMenu.add(linkMenu);

        visualPluginGroup = new ButtonGroup();
        dataPluginGroup = new ButtonGroup();


        //topPanel.add(dataVisualizationPanel);
        fileMenu = new JMenu(FILE_MENU_TITLE);
        addFileMenu();


        dataPluginMenu = new JMenu(MENU_SET_DATA);
        visualizationPluginMenu = new JMenu(MENU_SET_VISUALIZATION);
        addPluginMenus();
        menuBar.add(categoryMenu);
        addViewSwitchButton();

        frame.add(topPanel);
        frame.setJMenuBar(menuBar);
        //frame.pack();
        frame.setVisible(true);
    }

    private void addPluginMenus() {
        dataPluginMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        visualizationPluginMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        if (dataPluginMenu.isSelected()) {
            dataPluginMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        }
        if (visualizationPluginMenu.isSelected()) {
            visualizationPluginMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        }
        menuBar.add(dataPluginMenu);
        menuBar.add(visualizationPluginMenu);
    }

    private void addFileMenu() {
        JMenuItem exitMenuItem = new JMenuItem(FILE_MENU_EXIT);
        JMenuItem startMenuItem = new JMenuItem(FILE_MENU_START);
        startMenuItem.addActionListener(event -> {
            visualizationPanel = framework.getDataVisual();
                });
        exitMenuItem.addActionListener(event -> System.exit(0));
        JMenuItem newFrameworkMenuItem = new JMenuItem(FILE_MENU_NEW);

        fileMenu.add(newFrameworkMenuItem);
        fileMenu.add(startMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        fileMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        if (fileMenu.isSelected()) {
            fileMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        }

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
    public void onVisualPluginRegistered(VisualizationPlugin visualPlugin) {
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(visualPlugin.getName());
        menuItem.setToolTipText(visualPlugin.getDescription());
        menuItem.addActionListener(event -> {
            framework.setVisualPlugin(visualPlugin);
        });
        visualPluginGroup.add(menuItem);
        visualizationPluginMenu.add(menuItem);
    }

    @Override
    public void onDataPluginRegistered(DataPlugin dataPlugin) {
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(dataPlugin.getName());
        menuItem.setToolTipText(dataPlugin.getDescription());
        menuItem.addActionListener(event -> {
            framework.setDataPlugin(dataPlugin);
            categoryMenu.setEnabled(true);
            for (String s : dataPlugin.getData().getManager().getCategorySet()) {
                JRadioButtonMenuItem node = new JRadioButtonMenuItem(s);
                JRadioButtonMenuItem link = new JRadioButtonMenuItem(s);

                node.addActionListener(e -> {
                    framework.setNode(s);
                    for (Component c : linkMenu.getComponents()) {
                        c.setEnabled(true);
                    }
                    link.setEnabled(false);
                });
                nodeMenu.add(node);
                nodeButtonGroup.add(node);

                link.addActionListener(e -> {
                    framework.setLink(s);
                    for (Component c : nodeMenu.getComponents()) {
                        c.setEnabled(true);
                    }
                    node.setEnabled(false);
                });
                linkMenu.add(link);
                linkButtonGroup.add(link);

            }
        });
        dataPluginGroup.add(menuItem);
        dataPluginMenu.add(menuItem);
    }
}
