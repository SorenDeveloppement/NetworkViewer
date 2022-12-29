package fr.galaglow.nv;

import fr.galaglow.nv.util.IpInfo;
import fr.galaglow.nv.util.PortScanner;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main extends JFrame implements ActionListener {

    private final JList<String> iplist = new JList<>();
    private final JScrollPane listIpScroller = new JScrollPane(iplist);
    private final JLabel ipHostName = new JLabel("No Ip Selected");
    private final JButton ipHostNameButton = new JButton("Get host name");
    private final JButton ipScanButton = new JButton("Start a local IP scan");
    private final JList<String> portlist = new JList<>();
    private final JScrollPane listPortScroller = new JScrollPane(portlist);
    private final JButton portScanButton = new JButton("Start a port scan");

    public Main() {
        super("NetworkViewer");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension size = new Dimension(1200, 800);
        this.setSize(size);
        this.setMaximumSize(size);

        this.setLocationRelativeTo(null);

        // Adding components

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));

        contentPane.add(ipPanel());
        contentPane.add(portPanel());

    }

    private JPanel ipPanel() {
        JPanel ipContent = new JPanel();
        ipContent.setLayout(new GridLayout(1, 1));

        JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.Y_AXIS));

        // Setting up the ip result list
        iplist.setListData(new String[]{"Null"});
        iplist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        iplist.setLayoutOrientation(JList.VERTICAL);
        iplist.setVisibleRowCount(-1);

        listIpScroller.setPreferredSize(new Dimension(300, 200));
        ipContent.add(listIpScroller);

        // Setting up the selected ip host name
        boxLayout.add(ipHostName);

        // setting up the ip host name button
        ipHostNameButton.addActionListener(this);
        boxLayout.add(ipHostNameButton);

        // Setting up the local ip scan button
        ipScanButton.addActionListener(this);
        boxLayout.add(ipScanButton);

        ipContent.add(boxLayout);

        return ipContent;
    }

    private JPanel portPanel() {
        JPanel portContent = new JPanel();
        portContent.setLayout(new GridLayout(1, 1));

        JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.Y_AXIS));

        // Setting up the port result list
        portlist.setListData(new String[]{"Null"});
        portlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        portlist.setLayoutOrientation(JList.VERTICAL);
        portlist.setVisibleRowCount(-1);

        listPortScroller.setPreferredSize(new Dimension(150, 200));
        portContent.add(listPortScroller);

        // Setting up the port scan button
        portScanButton.addActionListener(this);
        boxLayout.add(portScanButton);

        portContent.add(boxLayout);

        return portContent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ipScanButton) {
            IpInfo ip = new IpInfo();

            List<String> ips = ip.getConnectedLocalIps();
            String[] cIps = new String[ips.size() - 1];

            for (int i = 0; i < ips.size() - 1; i++) {
                cIps[i] = String.valueOf(ips.get(i));
            }

            iplist.setListData(cIps);

        } else if (e.getSource() == ipHostNameButton) {
            try {
                if (iplist.getSelectedValue() != null) {
                    String ip = iplist.getSelectedValue();
                    String[] ipHosts = ip.split("\\.");

                    String hostName = new IpInfo().getLocalMachineInfoByIp(IpInfo.localHost, Byte.parseByte(ipHosts[2]), Byte.parseByte(ipHosts[3]))[1];

                    ipHostName.setText(hostName);
                }
            } catch (RuntimeException ex) {
                ipHostName.setText("No name found");
            }
        } else if (e.getSource() == portScanButton) {
            try {
                if (iplist.getSelectedValue() != null) {
                    String ip = iplist.getSelectedValue();

                    List<Integer> openedPorts = new PortScanner().scanPort(ip, 200);
                    String[] oPort = new String[openedPorts.size() - 1];

                    for (int i = 0; i < openedPorts.size() - 1; i++) {
                        oPort[i] = String.valueOf(openedPorts.get(i));
                    }

                    portlist.setListData(oPort);
                }
            } catch (ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
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