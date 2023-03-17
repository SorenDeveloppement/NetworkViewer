package fr.galaglow.nv.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@SuppressWarnings("unused")
public class IpInfo {

    protected static List<String> ips = new ArrayList<>();
    public static InetAddress localHost;

    static {
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public InetAddress getLocalIp() {
        try {
            InetAddress ip = InetAddress.getLocalHost();

            System.out.print("IP address : ");
            System.out.println(ip.getHostAddress());

            return ip;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void getLocalHostName() {
        try {
            InetAddress ip = InetAddress.getLocalHost();

            System.out.print("HostName : ");
            System.out.println(ip.getHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getLocalMachineInfoByIp(InetAddress localHost, byte host0, byte host1) {
        try {

            byte[] ip = localHost.getAddress();
            ip[2] = host0;
            ip[3] = host1;

            InetAddress address = InetAddress.getByAddress(ip);

            if (address.isReachable(100)) {
                String output = address.toString().substring(1);
                // System.out.println(address.getHostName() + " : " + output);

                return new String[]{output, address.getHostName()};
            } else {
                System.out.println("Aucune machine n'a été trouvée");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getMachineInfoByIp(byte network, byte underNetwork, byte host0, byte host1) {
        try {

            byte[] ip = localHost.getAddress();
            ip[0] = network;
            ip[1] = underNetwork;
            ip[2] = host0;
            ip[3] = host1;

            InetAddress address = InetAddress.getByAddress(ip);

            if (address.isReachable(100)) {
                String output = address.toString().substring(1);
                // System.out.println(address.getHostName() + " : " + output);

                return Arrays.toString(new String[]{output, address.getHostName()});
            } else {
                System.out.println("Aucune machine n'a été trouvée");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<String> getConnectedLocalIps() {
        ips.clear();
        final ExecutorService es = Executors.newCachedThreadPool();
        try {
            List<Future<ScanResult>> ipsc = new ArrayList<>();
            InetAddress localhost = InetAddress.getLocalHost();
            byte[] ip = localhost.getAddress();

            for (int i = 0; i <= 255; i++) {
                ipsc.add(ipIsConnected(es, ip[0], ip[1], ip[2], (byte) i, 200));
            }
            for (final Future<ScanResult> ip_ : ipsc) {
                if (ip_.get().isConnected()) {
                    ips.add(ip_.get().getIp());
                }
            }
        } catch (UnknownHostException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        es.shutdown();
        return ips;
    }

    public List<String> getConnectedIps() {
        try {
            final ExecutorService es = Executors.newCachedThreadPool();
            List<Future<ScanResult>> ipsc = new ArrayList<>();
            InetAddress localhost = InetAddress.getLocalHost();

            String begin = String.format("%tT", new Date());

            for (int i = 0; i <= 255; i++) {
                for (int j = 0; j <= 113; j++) {
                    System.out.println(j);
                    for (int k = 0; k <= 255; k++) {
                        for (int l = 0; l <= 255; l++) {
                            ipsc.add(ipIsConnected(es, (byte) i, (byte) j, (byte) k, (byte) l, 200));
                            // System.out.printf("%d.%d.%d.%d\n", i, j, k, l);
                        }
                    }
                }
                for (final Future<ScanResult> ip_ : ipsc) {
                    if (ip_.get().isConnected()) {
                        ips.add(ip_.get().getIp());
                    }
                }
                ipsc.clear();

                for (int j = 114; j <= 220; j++) {
                    System.out.println(j);
                    for (int k = 114; k <= 220; k++) {
                        for (int l = 0; l <= 255; l++) {
                            ipsc.add(ipIsConnected(es, (byte) i, (byte) j, (byte) k, (byte) l, 200));
                            //System.out.printf("%d.%d.%d.%d\n", i, j, k, l);
                        }
                    }
                }
                for (final Future<ScanResult> ip_ : ipsc) {
                    if (ip_.get().isConnected()) {
                        ips.add(ip_.get().getIp());
                    }
                }
                ipsc.clear();

                for (int j = 221; j <= 255; j++) {
                    System.out.println(j);
                    for (int k = 0; k <= 255; k++) {
                        for (int l = 0; l <= 255; l++) {
                            ipsc.add(ipIsConnected(es, (byte) i, (byte) j, (byte) k, (byte) l, 200));
                            //System.out.printf("%d.%d.%d.%d\n", i, j, k, l);
                        }
                    }
                }
                for (final Future<ScanResult> ip_ : ipsc) {
                    if (ip_.get().isConnected()) {
                        ips.add(ip_.get().getIp());
                    }
                }
                ipsc.clear();
            }

            String end = String.format("%tT", new Date());
            System.out.println(begin + ' ' + end);

            es.shutdownNow();
        } catch (UnknownHostException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return ips;
    }

    public static Future<ScanResult> ipIsConnected(final ExecutorService es, byte network, byte underNetwork, byte host0, byte host1, final int timeout) {
        return es.submit(() -> {
            byte[] ip = {network, underNetwork, host0, host1};

            try {

                InetAddress address = InetAddress.getByAddress(ip);

                if (address.isReachable(timeout)) {
                    String output = address.toString().substring(1);
                    // System.out.println(address.getHostName() + " : " + output);

                    ips.add(output);
                    return new ScanResult(Arrays.toString(ip), true);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new ScanResult(Arrays.toString(ip), false);
        });
    }

//    public byte[] getSubNetworkMask() {
//        byte[] subNetworkMask = new byte[4];
//
//        subNetworkMask = getLocalIp().getHostAddress().getBytes();
//
//        return subNetworkMask;
//    }

    public static class ScanResult {
        private String ip;

        private boolean isConnected;

        public ScanResult(String ip, boolean isConnected) {
            super();
            this.ip = ip;
            this.isConnected = isConnected;
        }

        public String getIp() {
            return ip;
        }

        public void setPort(String ip) {
            this.ip = ip;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public void setConnected(boolean isConnected) {
            this.isConnected = isConnected;
        }

    }
}