package com.wizzy.Wizzy.Banking.Application.util;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationUtils {

    public static String applicationUrl(HttpServletRequest request){

        return "http://"+request.getServerName()+":"
                +request.getServerPort()+
                request.getContextPath();
    }
}
