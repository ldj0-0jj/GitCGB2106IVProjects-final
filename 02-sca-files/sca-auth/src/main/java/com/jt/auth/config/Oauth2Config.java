package com.jt.auth.config;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * 在这个对象中负责将所有的认证和授权相关配置进行整合，例如
 * 业务方面：
 * 1)如何认证(认证逻辑的设计)
 * 2)认证通过以后如何颁发令牌(令牌的规范)
 * 3)为谁颁发令牌(客户端标识，client_id,...)
 * 技术方面：
 * 1)SpringSecurity (提供认证和授权的实现)
 * 2)TokenConfig(提供了令牌的生成，存储，校验)
 * 3)Oauth2(定义了一套认证规范，例如为谁发令牌，都发什么，...)
 */
@AllArgsConstructor //生成一个全参构造函数
@Configuration
@EnableAuthorizationServer//启动认证和授权
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {
    //@Autowired
    private AuthenticationManager authenticationManager;
    //@Autowired
    private UserDetailsService userDetailsService;
    //@Autowired
    private TokenStore tokenStore;
    //@Autowired
    private PasswordEncoder passwordEncoder;
    //@Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * oauth2中的认证细节配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        //super.configure(endpoints);
        endpoints
        //由谁完成认证？(认证管理器)
        .authenticationManager(authenticationManager)
        //谁负责访问数据库？(认证时需要两部分信息：一部分来自客户端，一部分来自数据库)
        .userDetailsService(userDetailsService)
        //支持对什么请求进行认证（默认支持post方式）
        .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
        //认证成功以后令牌如何生成和存储？(默认令牌生成UUID.randomUUID(),存储方式为内存)
        .tokenServices(tokenService());

    }
    //系统底层在完成认证以后会调用TokenService对象的相关方法
    //获取TokenStore，基于tokenStore获取token对象
    @Bean
    public AuthorizationServerTokenServices tokenService(){
      //1.构建TokenService对象(此对象提供了创建，获取，刷新token的方法)
        DefaultTokenServices tokenServices=
                new DefaultTokenServices();
      //2.设置令牌生成和存储策略
        tokenServices.setTokenStore(tokenStore);
      //3.设置是否支持令牌刷新(访问令牌过期了，是否支持通过令牌刷新机制，延长令牌有效期)
        tokenServices.setSupportRefreshToken(true);
      //4.设置令牌增强(默认令牌会比较简单，没有业务数据，
        //就是简单随机字符串，但现在希望使用jwt方式)
        TokenEnhancerChain tokenEnhancer=new TokenEnhancerChain();
        tokenEnhancer.setTokenEnhancers(Arrays.asList(
                jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancer);
      //5.设置访问令牌有效期
        tokenServices.setAccessTokenValiditySeconds(3600);//1小时
      //6.设置刷新令牌有效期
        tokenServices.setRefreshTokenValiditySeconds(3600*72);//3天
        return tokenServices;
    }

    /**
     * 假如我们要做认证，我们输入了用户名和密码，然后点提交
     * ，提交到哪里(url-去哪认证)，这个路径是否需要认证？还有令牌过期了，
     * 我们要重新生成一个令牌，哪个路径可以帮我们重新生成？
     * 如下这个方法就可以提供这个配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer security)
            throws Exception {

       //super.configure(security);
        security
        //1.定义(公开)要认证的url(permitAll()是官方定义好的)
         //公开oauth/token_key端点
        .tokenKeyAccess("permitAll()") //return this
        //2.定义(公开)令牌检查的url
         //公开oauth/check_token端点
        .checkTokenAccess("permitAll()")
        //3.允许客户端直接通过表单方式提交认证
        .allowFormAuthenticationForClients();
    }

    /**
     * 认证中心是否要给所有的客户端发令牌呢？假如不是，那要给哪些客户端
     * 发令牌，是否在服务端有一些规则的定义呢？
     * 例如：老赖不能做飞机，不能做高铁
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
         //super.configure(clients);
          clients.inMemory()
                //定义客户端的id(客户端提交用户信息进行认证时需要这个id)
               .withClient("gateway-client")
                //定义客户端密钥(客户端提交用户信息时需要携带这个密钥)
               .secret(passwordEncoder.encode("123456"))
                //定义作用范围(所有符合规则的客户端)
               .scopes("all")
                //允许客户端基于密码方式，刷新令牌方式实现认证
               .authorizedGrantTypes("password","refresh_token");

    }
}
/**
 访问令牌
 eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzI3MzM4NDAsInVzZXJfbmFtZSI6ImphY2siLCJhdXRob3JpdGllcyI6WyJzeXM6cmVzOmNyZWF0ZSIsInN5czpyZXM6cmV0cmlldmUiXSwianRpIjoiNmI1YWZkZmEtNTZmZS00MDM4LWE5OWQtYWY5NmQyY2UxMjVlIiwiY2xpZW50X2lkIjoiZ2F0ZXdheS1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.DGrrJSrr_wcwKNBcy3ARmFtSJPZueq2aru05agegxN4
 刷新令牌
 eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqYWNrIiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjZiNWFmZGZhLTU2ZmUtNDAzOC1hOTlkLWFmOTZkMmNlMTI1ZSIsImV4cCI6MTYzMjk4OTQ0MCwiYXV0aG9yaXRpZXMiOlsic3lzOnJlczpjcmVhdGUiLCJzeXM6cmVzOnJldHJpZXZlIl0sImp0aSI6IjE1ZThmYTQyLTMyMzQtNGM0YS05ZTI1LTg5ZWEyMDBiMTc4MyIsImNsaWVudF9pZCI6ImdhdGV3YXktY2xpZW50In0.rheSxJP1utFOE01vxe353YDJf8lgWQ1z5zKYNsEsYG0
 */
