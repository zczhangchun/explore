import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author by zhangchun
 * @since 2020/12/6 10:48 上午
 */
public class EncryptionRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        principals.getPrimaryPrincipal();
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //从数据库查到的密码
        String password = "3c88b338102c1a343bcb88cd3878758e";
        //从数据库查到的盐
        String salt = "Q4F%";

        return new SimpleAuthenticationInfo(token.getPrincipal() ,password,
                ByteSource.Util.bytes(salt),this.getName());

    }
}
