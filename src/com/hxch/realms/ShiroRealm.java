package com.hxch.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo --> "+authenticationToken);
        System.out.println("【FirstShiroRealm】 --> "+authenticationToken);
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
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
        }else if ("user".equals(username)){
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = null;// new SimpleAuthenticationInfo( principal,  credentials,  realmName);

        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        Object salt = ByteSource.Util.bytes("user");;
        int hashIterations = 1024;
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(simpleHash);
    }

    //授权实现的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1. 从 principalCollection 获取登录用户信息
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        // 2. 利用登录用户的信息获取相应的角色权限（可能需要查询数据库）
        Set<String> roles = new HashSet<>();
        roles.add("user");
        if ("admin".equals(primaryPrincipal)){
            roles.add("admin");
        }
        // 3. 创建SimpleAuthorizationInfo 并返回
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }
}
