import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author by zhangchun
 * @since 2020/12/4 10:03 下午
 */
public class HelloWord {

    /**
     * 1、简单的使用
     */
    @Test
    public void t1(){
        //1、创建SecurityManager的工厂，从ini读取相当于是从数据库读取
        final IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");

        //2、通过工厂获取SecurityManager实例
        final SecurityManager securityManager = iniSecurityManagerFactory.getInstance();

        //3、将SecurityManager绑定到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //4、通过SecurityUtils得到Subject
        final Subject subject = SecurityUtils.getSubject();

        //5、创建用户名/密码身份验证token对象，用来验证，用户在页面输入的用户名密码
        final UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        //6、登陆，即验证用户名密码
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            //身份验证失败
            //......一系列操作
            System.out.println("身份验证失败");
        }
        //7、判断是否验证通过
        Assert.assertTrue(subject.isAuthenticated());

        //8、退出
        subject.logout();
    }

    /**
     * 2、各个身份验证策略使用
     */
    @Test
    public void authenticationStategyTest(){
        final IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-stategy-realm.ini");

        final SecurityManager securityManager = securityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        final Subject subject = SecurityUtils.getSubject();

        final UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            System.out.println("身份验证失败");
        }

        System.out.println(subject.getPrincipals().asList().size());
        Assert.assertTrue(subject.isAuthenticated());


        subject.logout();

    }


    /**
     * 3、角色使用
     */
    @Test
    public void rolesTest(){
        final Subject subject = login("shiro-roles-permission.ini", "zhang", "123");

        //判断用户是否拥有该角色
        Assert.assertTrue(subject.hasRole("role1"));

        //判断用户是否拥有下面的所有角色
        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));

        //判断用户是否拥有该异常，会抛UnauthorizedException异常
        subject.checkRole("role1");

        subject.checkRoles(Arrays.asList("role1", "role2"));
    }

    /**
     * 4、权限的使用
     */
    @Test
    public void permissionTest(){
        final Subject subject = login("shiro-roles-permission.ini", "zhang", "123");

        //校验用户是否拥有该权限
        Assert.assertTrue(subject.isPermitted("user:create"));

        //校验用户是否拥有全部权限，少一个就是false
        Assert.assertTrue(subject.isPermittedAll("user:create", "user:update"));

        //校验用户是否拥有该权限，会抛UnauthorizedException异常
        subject.checkPermission("user:create");

        //校验用户是否拥有全部权限，少一个都是false。会抛UnauthorizedException异常
        subject.checkPermissions("user:create", "user:update");
    }

    /**
     * 5、使用java的方式，不再使用配置文件
     */
    @Test
    public void javaConfigurationTest(){


        //设置realm
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://rm-bp1pmj8cpzd51eqb5ho.mysql.rds.aliyuncs.com:3306/shiro");
        ds.setUsername("zhangchun");
        ds.setPassword("zc123123");
        final JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);
        //开启权限验证
        jdbcRealm.setPermissionsLookupEnabled(true);
        final Subject subject = login(jdbcRealm);

        Assert.assertTrue(subject.isAuthenticated());

        subject.logout();


    }

    /**
     * 6、编码加密解密
     * 其实就是自定义Realm
     */
    @Test
    public void encryptionTest(){

        //自定义编码加密的Realm
        EncryptionRealm encryptionRealm = new EncryptionRealm();

        final HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //使用MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);

        encryptionRealm.setCredentialsMatcher(credentialsMatcher);

        login(encryptionRealm);

    }

    /**
     * 7、使用JdbcRealm实现密码加密验证
     */
    @Test
    public void jdbcRealmEncryptionTest(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://rm-bp1pmj8cpzd51eqb5ho.mysql.rds.aliyuncs.com:3306/shiro");
        ds.setUsername("zhangchun");
        ds.setPassword("zc123123");
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);
        //设置COLIMN机制，代表查询出的SQL第一列是密码，第二列是盐
        jdbcRealm.setSaltStyle(JdbcRealm.SaltStyle.COLUMN);

        //设置查询语句
        jdbcRealm.setAuthenticationQuery("select password, password_salt from users where username = ?");

        //设置密码匹配规则
        final HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);

        jdbcRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        login(jdbcRealm);

    }

    /**
     * 使用java配置登陆
     * @return 用户实体
     */
    public Subject login(Realm realm){

        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        final ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(modularRealmAuthenticator);

        final ModularRealmAuthorizer modularRealmAuthorizer = new ModularRealmAuthorizer();
        modularRealmAuthorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(modularRealmAuthorizer);

        securityManager.setRealm(realm);

        SecurityUtils.setSecurityManager(securityManager);

        final Subject subject = SecurityUtils.getSubject();

        final UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("身份验证失败");
        }

        return subject;


    }


    /**
     * 公共的登陆方法
     */
    public Subject login(String ini, String username, String password){
        final IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:" + ini);

        final SecurityManager securityManager = securityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        final Subject subject = SecurityUtils.getSubject();

        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("身份验证失败");
        }

        return subject;
    }
}
