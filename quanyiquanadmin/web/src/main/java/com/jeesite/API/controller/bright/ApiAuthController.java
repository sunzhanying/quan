package com.jeesite.API.controller.bright;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.util.RedisTemplateUtils;
import com.jeesite.API.util.TokenUtils;
import com.jeesite.API.weixin.api.SnsAPI;
import com.jeesite.API.weixin.bean.sns.SnsToken;
import com.jeesite.modules.bright.banner.entity.Banner;
import com.jeesite.modules.bright.banner.service.BannerService;
import com.jeesite.modules.bright.t.service.khxx.KhXxService;
import com.jeesite.modules.order.service.ExpireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * 客户操作
 */

@Api(description = "登录等不需要token验证的接口.")
@RestController
@RequestMapping("${apiPath}/auth")
public class ApiAuthController {
    private final Log log = LogFactory.getLog(getClass());
    //卖方
    @Value("${weixin.appid}")
    private String wxAppId;
    @Value("${weixin.appsecret}")
    private String wxAppSecret;
    //买方
    @Value("${weixin.appidA}")
    private String wxAppIdA;
    @Value("${weixin.appsecretA}")
    private String wxAppSecretA;

    @Autowired
    private KhXxService khXxService;
    @Autowired
    private BannerService bannerService;

    @Autowired
    private RedisTemplateUtils redisUtils;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ExpireService expireService;

    /*@GetMapping(value = "setString")
    public Response setString(){
        redisUtils.set("")
        return new Response(nameTemp);

    }*/

    @GetMapping(value = "test1")
    public Response test1(){
        //用户sp
        //redisUtils.hincr("userId","SpXxId");
        //用户获取SpXxId的缓存
        //System.out.println(redisUtils.hget("userId","SpXxId"));
        //用户获取所有spxx缓存
        return new Response("hello test");

    }
    @GetMapping(value = "expire")
    public Response expire(){
        return expireService.expireCard();

    }

    @PostMapping(value = "test2")
    public Response test2(String name){
        String token = tokenUtils.generateToken(name,"");
        //String nameTemp = tokenUtils.getUsernameFromToken(token);
        //String role = tokenUtils.getRoles(token);
        return new Response(token);

    }

    @GetMapping(value = "test")
    public Response test(){
        //用户sp
        redisUtils.hincr("userId","SpXxId");
        //用户获取SpXxId的缓存
        System.out.println(redisUtils.hget("userId","SpXxId"));
        //用户获取所有spxx缓存
        return new Response(redisUtils.hmget("userId"));

    }

/**
     * 微信用户登录
     *
     * @param code 微信授权后获取的code
     * @param tjrid   推荐人id
     * @return 授权结果，返回包含JWT的字符串
     */
    @ApiOperation(value = "微信登录",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="code",value = "code",required = true),
            @ApiImplicitParam(name="source",value = "来源 1 卖方 2 买方",required = true),
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response login(@RequestParam String code,
                          @RequestParam(defaultValue = "1") String source) {
        SnsToken token = null;
        ///卖方
        if ("1".equals(source)){
            token = SnsAPI.oauth2AccessToken(wxAppId, wxAppSecret, code);
        }
        ///买方
        if ("2".equals(source)){
            token = SnsAPI.oauth2AccessToken(wxAppIdA, wxAppSecretA, code);
        }
        if (!token.isSuccess()) {
            return new Response(Code.API_USER_AUTH_ERROR);
        }

        return new Response(khXxService.login(token, source));
    }


    @ApiOperation(value = "首页banner",httpMethod ="GET",response = Banner.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value = "推荐位类型，1首页头部，2首页中部，默认传1",required = false)
    })
    @RequestMapping(value = "/banner",method = RequestMethod.GET)
    public Response banner(@RequestParam(value = "type" ,required = false,defaultValue = "1") String type) {
        Banner banner=new Banner();
        banner.setType(type);
        return new Response(bannerService.findList(banner));
    }


}
