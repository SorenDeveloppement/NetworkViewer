package fr.galaglow.nv.util;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        IpInfo ip = new IpInfo();
        System.out.println(Arrays.toString(ip.getSubNetworkMask()));
        System.out.println(ip.getLocalIp().getHostAddress());
    }

}
