package fr.galaglow.nv;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        super("NetworkViewer");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension size = new Dimension(1200, 800);
        this.setSize(size);
        this.setMaximumSize(size);

        this.setLocationRelativeTo(null);

        // Components constants

        // Adding components

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new FlowLayout());

        contentPane.add(ipPanel());
        contentPane.add(portPanel());

    }

    private JPanel ipPanel() {
        JPanel ipContent = new JPanel();
        ipContent.setLayout(new BoxLayout(ipContent, BoxLayout.Y_AXIS));

        // Setting up the ip result list
        JList<String> iplist = new JList<>();
        iplist.setListData(new String[]{"Null"});
        iplist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        iplist.setLayoutOrientation(JList.VERTICAL);
        iplist.setVisibleRowCount(-1);

        JScrollPane listIpScroller = new JScrollPane(iplist);
        listIpScroller.setPreferredSize(new Dimension(300, 200));
        ipContent.add(listIpScroller);

        // Setting up the selected ip host name
        JLabel ipHostName = new JLabel("No Ip Selected");
        ipContent.add(ipHostName);

        // Setting up the local ip scan button
        JButton ipScanButton = new JButton("Start an local ip scan");
        ipContent.add(ipScanButton);

        return ipContent;
    }

    private JPanel portPanel() {
        JPanel portContent = new JPanel();
        portContent.setLayout(new BoxLayout(portContent, BoxLayout.Y_AXIS));

        // Setting up the port result list
        JList<String> portlist = new JList<>();
        portlist.setListData(new String[]{"Null", "AA", "aa", "eee"});
        portlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        portlist.setLayoutOrientation(JList.VERTICAL);
        portlist.setVisibleRowCount(-1);

        JScrollPane listPortScroller = new JScrollPane(portlist);
        listPortScroller.setPreferredSize(new Dimension(150, 200));
        portContent.add(listPortScroller);

        // Seting up the ip text field for port scan
        JTextField ipTextField = new JTextField("192.168.1.1");
        portContent.add(ipTextField);

        // Setting up the port scan button
        JButton portScanButton = new JButton("Start a port scan");
        portContent.add(portScanButton);

        return portContent;
    }

    public static void main(String[] args) {
        System.out.println("Thanks to use NetworkViewer !");

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        Main window = new Main();
        window.setVisible(true);
    }
}
