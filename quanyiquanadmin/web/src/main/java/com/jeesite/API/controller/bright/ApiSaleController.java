package com.jeesite.API.controller.bright;

import com.jeesite.modules.sale.service.SaleService;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 分销操作
 */

@Api(description = "分销相关接口")
@RestController
@RequestMapping("${apiPath}/sale")
public class ApiSaleController {
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private SaleService saleService;

    /*@RequestMapping(value = "/addPerson",method = RequestMethod.POST)
    public Response getPhone(HttpServletRequest request, @RequestParam String encryptedData, @RequestParam String iv, @RequestParam String code) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        try {
            SnsToken token = SnsAPI.oauth2AccessToken(wxAppId, wxAppSecret, code);
            if (encryptedData != null && iv != null && !"".equals(token.getSession_Key())
                    && token.getSession_Key() != null) {
                Phone phone1 = decrypt(token.getSession_Key(), encryptedData, iv);
                khXx.setSj(phone1.getPhoneNumber());
                khXxService.update(khXx);
                return new Response(Code.SUCCESS);
            }
        } catch(Exception e){
            return new Response(Code.API_PHONE_ERROR);
        }
        return new Response(Code.API_PHONE_ERROR);
    }*/


   /* @ApiOperation(value = "提现审核列表",httpMethod ="GET")
    @RequestMapping(value = "/txshList",method = RequestMethod.GET)
    public Page<Txsh> txshList(HttpServletRequest request,
                               @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                               @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Page<Txsh> txshPage = new Page<>();
        txshPage.setPageNo(page);
        txshPage.setPageSize(size);
        Txsh txsh = new Txsh();
        txsh.setKhid(khXx.getId());
        txsh.setPage(txshPage);
        List<Txsh> txshList = txshService.findList(txsh);
        return txshPage.setList(txshList);
    }*/

}
