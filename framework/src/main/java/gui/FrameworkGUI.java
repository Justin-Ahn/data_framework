package gui;

import core.framework.DataVisualizationFramework;
import core.framework.FrameworkListener;
import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The Framework's GUI.
 * Usage:
 * User must:
 * 1) Select a DataPlugin
 *     -In doing so the GUI will pull the data categories from the DataPlugin.
 *     -The user will choose which data category should be a link and a node.
 *     -Link cannot equal Node. (Will be greyed out)
 * 2) Select a VisualizationPlugin
 * 3) Run "Apply Plugins..." to view the results from the VisualizationPlugin.
 *
 * Note: Some Visualization Plugins and Data Plugins may take some time to load.
 * Unable to use multithreading in time to still have a responsive GUI during the loading time, unfortunately.
 */
public class FrameworkGUI implements FrameworkListener{

    //The GUI's Frame
    private final JFrame frame;

    //The frame's top menuBar.
    private final JMenuBar menuBar;

    private final GUIStarter starter;
    //Menu that combines nodeMenu & linkMenu
    private final JMenu categoryMenu;
    //Menu of Data Categories
    private final JMenu nodeMenu;
    //Menu of Data Categories
    private final JMenu linkMenu;
    //Menu to choose general things
    private final JMenu fileMenu;
    //Menu to choose which Data plugin to use.
    private final JMenu dataPluginMenu;
    //Button Groups of different MenuItems. Used for clearing selection & being able to only select 1 at a time.
    private final ButtonGroup dataPluginGroup;
    private final ButtonGroup visualPluginGroup;
    private final ButtonGroup nodeButtonGroup;
    private final ButtonGroup linkButtonGroup;

    //To output a message if something goes wrong.
    private final JLabel messageLabel;
    //Menu to choose which Data Visualization plugin to use.
    private final JMenu visualizationPluginMenu;


    //The Panel from the Visualization plugin.
    private JPanel visualizationPanel;
    //The framework core used by the GUI.
    private final DataVisualizationFramework framework;

    private static final String FRAME_TITLE = "Relationship Visualization Framework";
    private static final String CATEGORY_MENU_TITLE = "Data Category";
    private static final String NODE_MENU_TITLE = "Choose Node Category...";
    private static final String LINK_MENU_TITLE = "Choose Link Category...";
    private static final String FILE_MENU_TITLE = "File";
    private static final String FILE_MENU_NEW = "New Window";
    private static final String FILE_MENU_EXIT = "Exit";
    private static final String FILE_MENU_START = "Apply Plugins...";
    private static final String MENU_SET_DATA = "Data plugin";
    private static final String MENU_SET_VISUALIZATION = "Visualization plugin";

    public FrameworkGUI(DataVisualizationFramework framework, GUIStarter starter) {
        this.framework = framework;
        framework.setListener(this);

        this.starter = starter;
        frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(750, 750));

        menuBar = new JMenuBar();
        visualizationPanel = new JPanel();

        //Set as a gridlayout so that the added panel will span the visualizationPanel
        visualizationPanel.setLayout(new GridLayout(1, 1));

        messageLabel = new JLabel();

        categoryMenu = new JMenu(CATEGORY_MENU_TITLE);
        categoryMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        nodeMenu = new JMenu(NODE_MENU_TITLE);
        linkMenu = new JMenu(LINK_MENU_TITLE);
        nodeButtonGroup = new ButtonGroup();
        linkButtonGroup = new ButtonGroup();
        categoryMenu.setEnabled(false);
        categoryMenu.add(nodeMenu);
        categoryMenu.add(linkMenu);

        visualPluginGroup = new ButtonGroup();
        dataPluginGroup = new ButtonGroup();


        fileMenu = new JMenu(FILE_MENU_TITLE);
        addFileMenu();


        dataPluginMenu = new JMenu(MENU_SET_DATA);
        visualizationPluginMenu = new JMenu(MENU_SET_VISUALIZATION);
        addPluginMenus();
        menuBar.add(categoryMenu);
        JPanel messagePanel = new JPanel();
        messagePanel.add(messageLabel);
        menuBar.add(messagePanel);

        frame.add(visualizationPanel);
        frame.setJMenuBar(menuBar);
        //frame.pack();
        frame.setVisible(true);
    }

    private void addPluginMenus() {
        dataPluginMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        visualizationPluginMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        menuBar.add(dataPluginMenu);
        menuBar.add(visualizationPluginMenu);
    }

    private void addFileMenu() {
        JMenuItem exitMenuItem = new JMenuItem(FILE_MENU_EXIT);
        JMenuItem startMenuItem = new JMenuItem(FILE_MENU_START);
        startMenuItem.addActionListener(event -> {
            String node = null;
            String link = null;
            for (Component c : linkMenu.getMenuComponents()) {
                JRadioButtonMenuItem item = (JRadioButtonMenuItem)c;
                if (item.isSelected()) {
                    link = item.getText();
                }
            }

            for (Component c : nodeMenu.getMenuComponents()) {
                JRadioButtonMenuItem item = (JRadioButtonMenuItem)c;
                if (item.isSelected()) {
                    node = item.getText();
                }
            }

            if (framework.getDataPlugin() == null) {
                setMessageLabelText("You did not set a Data Plugin!");
            }
            else if (framework.getVisualPlugin() == null) {
                setMessageLabelText("You did not set a Visual Plugin!");
            }
            else if (node == null) {
                setMessageLabelText("You did not set a Data Category as the Node!");
            }
            else if (link == null) {
                setMessageLabelText("You did not set a Data Category as the Link!");
            }
            else {
                visualizationPanel.removeAll();
                visualizationPanel.add(framework.getDataVisual(node, link));
                visualizationPanel.revalidate();
            }
                });
        exitMenuItem.addActionListener(event -> System.exit(0));
        JMenuItem newFrameworkMenuItem = new JMenuItem(FILE_MENU_NEW);
        newFrameworkMenuItem.addActionListener(event -> {
            starter.startFramework();
        });
        fileMenu.add(newFrameworkMenuItem);
        fileMenu.add(startMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        fileMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        menuBar.add(fileMenu);
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

            nodeMenu.removeAll();
            linkMenu.removeAll();
            linkMenu.setEnabled(false);

            categoryMenu.setEnabled(true);
            for (String s : dataPlugin.getData().getManager().getCategorySet()) {
                JRadioButtonMenuItem node = new JRadioButtonMenuItem(s);
                JRadioButtonMenuItem link = new JRadioButtonMenuItem(s);

                node.addActionListener(e -> {
                    for (Component c : linkMenu.getMenuComponents()) {
                        JRadioButtonMenuItem item = (JRadioButtonMenuItem)c;
                        item.setEnabled(true);
                    }
                    linkMenu.setEnabled(true);
                    //Seems as though item.setSelection(false) isn't responding... So I did this instead.
                    linkButtonGroup.clearSelection();
                    link.setEnabled(false);
                });

                nodeMenu.add(node);
                nodeButtonGroup.add(node);

                linkMenu.add(link);
                linkButtonGroup.add(link);

            }
        });
        dataPluginGroup.add(menuItem);
        dataPluginMenu.add(menuItem);
    }


    //Credit to Michal Ziober on StackOverflow.
    private void setMessageLabelText(String text) {
        messageLabel.setText(text);
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("");
            }
        });
        timer.start();
    }
}
