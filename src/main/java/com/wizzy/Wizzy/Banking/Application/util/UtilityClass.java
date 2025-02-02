package com.wizzy.Wizzy.Banking.Application.util;

import jakarta.servlet.http.HttpServletRequest;
import ua_parser.Client;
import ua_parser.Parser;

public class UtilityClass {

    public static String getCurrentIpAddress(HttpServletRequest request){

        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)){
        ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getRemoteAddr();

    }
    return ipAddress;


    }

    public static String getDeviceDetails(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        Parser parser = new Parser();
        Client client = parser.parse(userAgent);

        String browser = client.userAgent.family;
        String os = client.os.family;

        return browser + " 0n " + os;
    }


}
