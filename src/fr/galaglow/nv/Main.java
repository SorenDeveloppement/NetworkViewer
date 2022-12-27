package fr.galaglow.nv;

import fr.galaglow.nv.util.IpInfo;
import fr.galaglow.nv.util.PortScanner;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        System.out.println("Thanks to use NetworkViewer !");

        IpInfo ip = new IpInfo();
        ip.getLocalIp();
        ip.getLocalHostName();
        System.out.println(ip.getLocalMachineInfoByIp(ip.localHost, (byte) 1, (byte) 16));
        System.out.println(ip.getMachineInfoByIp((byte) 192, (byte) 168, (byte) 215, (byte) 1));

        /* List<String> result = ip.getConnectedIps();

        System.out.println(result.toString()); */

       /* PortScanner pScan = new PortScanner();
        try {
            System.out.println(pScan.scanPort("192.168.1.16", 200));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }*/
    }
}
