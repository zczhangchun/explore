import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author by zhangchun
 * @since 2020/12/4 10:06 下午
 */
public class MyRealm1  implements Realm {

    /**
     * 获取realm名称
     * @return
     */
    @Override
    public String getName() {
        return "myRealm1";
    }

    /**
     * 校验token
     * @param token token
     * @return 校验是否成功
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        //只接收UsernamePasswordToken类型的token
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 获取身份验证信息
     * 可以在这里校验各种数据，并抛出各种异常
     * @param token token
     * @return 身份验证信息
     * @throws AuthenticationException 异常
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行myRealm1");
        //获取用户名
        final String username = (String) token.getPrincipal();

        //获取密码
        final String password = new String(((char[]) token.getCredentials()));

        //校验用户名
        if (!"zhang".equals(username)){
            //用户名错误
            throw new UnknownAccountException();
        }

        if (!"123".equals(password)){
            //密码错误
            throw new IncorrectCredentialsException();
        }

        //身份验证都通过，返回一个AuthenticationInfo的实现类
        return new SimpleAuthenticationInfo(username, password, getName());

    }
}
