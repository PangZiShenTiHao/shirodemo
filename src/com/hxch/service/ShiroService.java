package com.hxch.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

import java.util.Date;

public class ShiroService {

    @RequiresRoles({"admin"})
    public void testMethod(){
        System.out.println("ShiroService --> testMethod --> time : "+ new Date());

        Session session = SecurityUtils.getSubject().getSession();
        String key123 = (String)session.getAttribute("key123");

        System.out.println("key123 = " + key123);
    }
}
