package fr.galaglow.nv.util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("unused")
public class PortScanner {

    List<Integer> ports = new ArrayList<>();

    public List<Integer> scanPort(final String ipAddress, final int timeOut) throws InterruptedException, ExecutionException {
        final ExecutorService es = Executors.newCachedThreadPool();
        final List<Future<ScanResult>> futures = new ArrayList<>();
        for (int port = 1; port <= 65535; port++) {
            // for (int port = 1; port <= 80; port++) {
            futures.add(portIsOpen(es, ipAddress, port, timeOut));
        }
        for (final Future<ScanResult> f : futures) {
            if (f.get().isOpen()) {
                ports.add(f.get().getPort());
            }
        }
        es.shutdown();

        return ports;
    }


    public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port, final int timeout) {
        return es.submit(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), timeout);
                socket.close();
                return new ScanResult(port, true);
            } catch (Exception ex) {
                return new ScanResult(port, false);
            }
        });
    }

    public static class ScanResult {
        private int port;

        private boolean isOpen;

        public ScanResult(int port, boolean isOpen) {
            super();
            this.port = port;
            this.isOpen = isOpen;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }

    }
}