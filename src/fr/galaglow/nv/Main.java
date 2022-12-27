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

        // Setting up the ip result list
        JList<String> iplist = new JList<>();
        iplist.setListData(new String[]{"Null"});
        iplist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        iplist.setLayoutOrientation(JList.VERTICAL);
        iplist.setVisibleRowCount(-1);

        JScrollPane listIpScroller = new JScrollPane(iplist);
        listIpScroller.setPreferredSize(new Dimension(250, 80));
        contentPane.add(listIpScroller);

        // Setting up the local ip scan button
        JButton ipScanButton = new JButton("Start an local ip scan");
        contentPane.add(ipScanButton);

        // Seting up the ip text field for port scan
        JTextField ipTextField = new JTextField("192.168.1.1");
        contentPane.add(ipTextField);

        // Setting up the port scan button
        JButton portScanButton = new JButton("Start a port scan");
        contentPane.add(portScanButton);

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
