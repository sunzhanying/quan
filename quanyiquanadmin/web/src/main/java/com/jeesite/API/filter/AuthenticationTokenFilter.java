package com.jeesite.API.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.util.TokenUtils;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 身份过滤器.
 * 通过Header中的X-Auth-Token项, jwt校验.
 * <p>
 * 此过滤器需要在跨域过滤器过滤后执行.
 */
@WebFilter
public class AuthenticationTokenFilter extends GenericFilterBean {
    private Log log = LogFactory.getLog(getClass());


    @Value("${weixin.token.header}")
    private String tokenHeader/* = Global.getConfig("token.header")*/;

    @Value("${server.servlet.context-path}")
    private String site;
    @Value("${apiPath}")
    private  String apiPath;


    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private KhXxDao khXxDao;

    public AuthenticationTokenFilter() {
        log.info("=================初始化过滤器================");
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        // 不过滤OPTIONS请求
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())) {
            chain.doFilter(req, res);
            return;
        }
        //开发阶段不需要验证token
       /* if (true) {
            chain.doFilter(req, res);
            return;
        }*/

        // 开始过滤
        String uri = request.getRequestURI();
        log.info("请求地址uri================" + uri + "    方式:" + request.getMethod());


        if (uri.startsWith(site + apiPath) ) {
            if (isUriNotApiNeedToken(uri)) {
                chain.doFilter(req, res);
                return;
            }
            // X-Auth-Token携带确认
            final String authHeader = request.getHeader(tokenHeader);
            System.out.println("Bearer " + tokenUtils.generateToken("1161566266154573824" ,""));
            log.info("Auth Header: " + authHeader);
            if ((authHeader == null || !authHeader.startsWith("Bearer "))) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
            final String token = authHeader.substring(7); // The part after "Bearer "

            KhXx khXx = verifyKhXx(response, token);
            if (khXx != null) {
                request.setAttribute("khXx", khXx);
            } else {
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private KhXx verifyKhXx(HttpServletResponse response, String token) throws IOException {
        String id = tokenUtils.getUsernameFromToken(token);
        String role = tokenUtils.getRoles(token);
        KhXx khXx = khXxDao.get(new KhXx(id));
        if (khXx == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return null;
        }

        if (!checkKhXxStatus(khXx, response)
                || !checkKhXxStatus(khXx, response)) {
            return null;
        }


        // token校验
        if (!tokenUtils.validateToken(token, id, "")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        String status = khXx.getStatus();
        if (!status.equals(KhXx.STATUS_NORMAL)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        return khXx;
    }

    private boolean checkKhXxStatus(KhXx khXx, HttpServletResponse response) throws IOException {
        if (khXx.getStatus().equals(KhXx.STATUS_NORMAL)) {
            return true;
        }
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(new Response<>(Code.API_USER_AUTH_ERROR));

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return false;
    }


    private boolean isUriNotApiNeedToken(final String uri) {
        if (uri.startsWith(site + apiPath + "/auth")) {
            return true;
        }
        // 本地调试用，上线之后注释掉
        /*if (uri.startsWith(site + apiPath + "/qyq")) {
            return true;
        }*/
        if (uri.startsWith(site + apiPath + "/wx/notify")) {
            return true;
        }
        if (uri.startsWith(site + apiPath + "/vip/notify")) {
            return true;
        }
        if (uri.startsWith(site + apiPath + "/sp/updateTags")) {
            return true;
        }
        return false;

    }
}

