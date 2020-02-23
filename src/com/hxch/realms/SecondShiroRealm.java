package com.hxch.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecondShiroRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo --> "+authenticationToken);
        System.out.println("【SecondShiroRealm】 --> "+authenticationToken);
        // 1.将 authenticationToken 强转 UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        // 2.获取用户名
        String username = token.getUsername();
        // 3.通过用户名 从数据库中查找 该用户对应的信息
        System.out.println("从数据库中查找 username = "+username+" 所对应的用户信息。");
        // 4. 抛出相关异常
        if("Unkonwn".equals(username)){
            throw new UnknownAccountException("用户不存在！");
        }

        if ("monster".equals(username)){
            throw new LockedAccountException("该用户被锁定！");
        }
        // 5.根据从数据库中查询的信息 构建AuthenticationInfo 实现类对象 并放回 一般用 SimpleAuthenticationInfo
        // 以下信息是从 数据库中获取的
        // 5.1 principal 认证的实体信息 可以是username 也可以是 数据表中对用的用户的实体类对象
        // 5.2 credentials 密码
        // 5.3 盐值
        // 5.4 realmName 当前realm 对象的名字 调用 父类的 getName()方法即可

        Object principal = username;
        String credentials = null; //"fc1709d0a95a6be30bc5926fdb7f22f4";
        if ("admin".equals(username)){
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        }else if ("user".equals(username)){
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
        }
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = null;// new SimpleAuthenticationInfo( principal,  credentials,  realmName);

        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "SHA1";
        String credentials = "123456";
        Object salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(simpleHash);
    }
}
