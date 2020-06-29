package com.jeesite.API.controller.bright;

import com.google.common.base.Strings;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.util.RedisTemplateUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.setfocus.dao.tag.TagDao;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.entity.sptype.SpType;
import com.jeesite.modules.bright.sp.service.SpXxService;
import com.jeesite.modules.bright.sp.service.sptype.SpTypeService;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.collect.entity.Collect;
import com.jeesite.modules.collect.service.CollectService;
import com.jeesite.modules.file.entity.FileUploadParams;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.order.service.OrderService;
import com.jeesite.modules.qyhs.entity.Qyhs;
import com.jeesite.modules.qyhs.service.QyhsService;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.qyhsmx.service.QyhsMxService;
import com.jeesite.modules.qyjg.entity.Qyjg;
import com.jeesite.modules.qyjg.service.QyjgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.jeesite.modules.bright.util.QRCodeUtil.qrCode;

@Api(description = "关于权益券的接口")
@RestController
@RequestMapping("${apiPath}/qyq")
public class ApiSpController {
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private SpTypeService spTypeService;
    @Autowired
    private SpXxService spXxService;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private QyhsService qyhsService;
    @Autowired
    private QyjgService qyjgService;

    @Value("${qrCode_realm_name}")
    protected String qrCodeRealmName;
    @Value("${spring.redis.goods_prefix}")
    protected String goodsPrefix;

    @Autowired
    private RedisTemplateUtils redisUtils;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private QyhsMxService qyhsMxService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CollectService collectService;

    //上传文件，不可删除
    @RequestMapping({"upload"})
    public Map<String, Object> upload(FileUploadParams params) {
        params.setFileMd5(UUID.randomUUID().toString());
        //params.setFileName(params.getFile().getName());
        params.setFileName(params.getFile().getOriginalFilename());
        return fileUploadService.uploadFile(params);
    }

    @ApiOperation(value = "getSpTypeAll", notes = "获取所有权益券类型", httpMethod = "GET")
    @RequestMapping(value = "/getSpTypeAll", method = RequestMethod.GET)
    public Response getSpTypeAll() {
        SpType spType = new SpType();
        spType.setSxj(SpType.SPTYPE_DISPLAY_YES);
        return new Response(spTypeService.findList(spType));
    }

    @ApiOperation(value = "getSpAll", notes = "获取所有权益券", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = false),
            @ApiImplicitParam(name = "size", value = "每页多少条", required = false),
            @ApiImplicitParam(name = "typeid", value = "权益券类型id", required = false),
            @ApiImplicitParam(name = "name", value = "搜索", required = false),
    })
    @RequestMapping(value = "/getSpAll", method = RequestMethod.GET)
    public Page<SpXx> getSpAll(HttpServletRequest request, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                         @RequestParam(required = false, value = "size", defaultValue = "10") Integer size,
                         String typeid, String name) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        Page<SpXx> spXxPage = new Page<>();
        spXxPage.setPageSize(size);
        spXxPage.setPageNo(page);
        SpXx spXx = new SpXx();
        spXx.setPage(spXxPage);
        spXx.setSplx(typeid);
        spXx.getSqlMap().getWhere().andBracket("spmc", QueryType.LIKE, name)
                .or("spfmc", QueryType.LIKE, name).endBracket();
        List<SpXx> spXxes = spXxService.findList(spXx);
        Qyjg qyjg = new Qyjg();

        Collect collect = new Collect();
        collect.setKhid(khXx.getId());
        spXxes.forEach(item ->{
            //价格
            qyjg.setQyqId(item.getId());
            qyjg.setPageSize(1);
            item.setQyjg(qyjgService.findList(qyjg).get(0));
            QyhsMx qyhsMx = new QyhsMx();
            qyhsMx.setZt(QyhsMx.STATUS_CSZ);
            //库存
            qyhsMx.setQyqId(item.getId());
            item.setKc((int) qyhsMxService.findCount(qyhsMx));
            //成交量
            qyhsMx.setZt("");
            qyhsMx.getSqlMap().getWhere().and("zt", QueryType.GT, QyhsMx.STATUS_DFK);
            item.setCjl(qyhsMxService.findCount(qyhsMx));
            //是否收藏
            collect.setSpId(item.getId());
            if (collectService.findCount(collect) > 0){
                item.setIssc(true);
            }else {
                item.setIssc(false);
            }
        });
        return spXxPage.setList(spXxes);
    }

    @ApiOperation(value = "addQy",notes = "卖方小程序添加券（json格式）",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="qyqId",value = "权益券id",required = true),
            @ApiImplicitParam(name="type",value = "卡类型",required = true),
            @ApiImplicitParam(name="source",value = "来源",required = false),
            @ApiImplicitParam(name="qyhsMxes",value = "券明细 [{kh://卡号，km://卡密，img://图片，yxqDate://有效期2020-12-09}]",required = true)
    })
    @RequestMapping(value = "/addQy",method = RequestMethod.POST)
    public Response isKyYhq(HttpServletRequest request, @RequestBody Qyhs qyhs) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        qyhs.setKhid(khXx.getId());
        qyhs.setZt(Qyhs.STATUS_DSH);
        qyhsService.save(qyhs);
        return new Response(Code.SUCCESS);
    }

    @ApiOperation(value = "getSpDetails", notes = "获取权益券详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权益券id", required = true)
    })
    @RequestMapping(value = "/getSpDetails", method = RequestMethod.GET)
    public Response getSpDetails(@RequestParam String id) {
        SpXx spXx = spXxService.getSpXx(id);
        return new Response(spXx);
    }


    @ApiOperation(value = "getQyType", notes = "卖方待审核 出售中 已售出 无效券", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1待审核 2出售中 3已售出 4无效券", required = true)
    })
    @RequestMapping(value = "/getQyType", method = RequestMethod.GET)
    public Page<QyhsMx> getQyType(HttpServletRequest request,
                                    @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(required = false, value = "size", defaultValue = "10") Integer size
                                    ,@RequestParam String type) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        Page<QyhsMx> qyhsMxPage = new Page<>();
        qyhsMxPage.setPageNo(page);
        qyhsMxPage.setPageSize(size);
        QyhsMx qyhsMx = new QyhsMx();
        qyhsMx.setKhid(khXx.getId());
        if ("1".equals(type)){//待审核
            qyhsMx.setZt(QyhsMx.STATUS_DSH);
        }
        if ("2".equals(type)){//出售中
            qyhsMx.setZt(QyhsMx.STATUS_CSZ);
        }
        if ("1".equals(type)){//待审核的，不合并展示
            String extColumn = "count(a.id) AS \"count\"";
            qyhsMx.getSqlMap().add("extColumn", extColumn);
            qyhsMx.getSqlMap().add("extWhere", "GROUP BY a.id");
        }
        if (/*"1".equals(type) || */"2".equals(type)){//出售中，不合并展示
            /*String extColumn = "count(a.id) AS \"count\"";
            qyhsMx.getSqlMap().add("extColumn", extColumn);
            qyhsMx.getSqlMap().add("extWhere", "GROUP BY a.qyq_id");*/
            String extColumn = "count(a.id) AS \"count\"";
            qyhsMx.getSqlMap().add("extColumn", extColumn);
            qyhsMx.getSqlMap().add("extWhere", "GROUP BY a.id");
        }
        if ("3".equals(type)){
            qyhsMx.getSqlMap().getWhere().and("zt", QueryType.GT, QyhsMx.STATUS_DFK);
            String extColumn = "count(a.id) AS \"count\"";
            qyhsMx.getSqlMap().add("extColumn", extColumn);
            qyhsMx.getSqlMap().add("extWhere", "GROUP BY a.qyq_id,a.order_id");
        }
        if ("4".equals(type)){//无效券
            qyhsMx.setZt(QyhsMx.STATUS_TH);
            String extColumn = "count(a.id) AS \"count\"";
            qyhsMx.getSqlMap().add("extColumn", extColumn);
            qyhsMx.getSqlMap().add("extWhere", "GROUP BY a.id");
        }
        qyhsMx.setPage(qyhsMxPage);
        List<QyhsMx> qyhsMxList = qyhsMxService.findList(qyhsMx);
        Qyjg qyjg = new Qyjg();
        qyhsMxList.forEach(item ->{
            if ("1".equals(type) || "2".equals(type)){
                //价格
                qyjg.setQyqId(item.getQyqId());
                qyjg.setPageSize(1);
                item.setQyjg(qyjgService.findList(qyjg).get(0));
            }
            //权益
            item.setSpXx(spXxService.get(item.getQyqId()));
            if ("3".equals(type)){
                item.setOrder(orderService.get(item.getOrderId()));
            }
        });
        return qyhsMxPage.setList(qyhsMxList);
    }

    @ApiOperation(value = "getMx", notes = "买方明细 1 全部 2 提交投诉 3 待付款 4 已付款 5 纠纷中", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1 全部 2 提交投诉 3 待付款 4 纠纷中", required = true)
    })
    @RequestMapping(value = "/getMx", method = RequestMethod.GET)
    public Page<Order> getMx(HttpServletRequest request,
                             @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                             @RequestParam(required = false, value = "size", defaultValue = "10") Integer size
                            , @RequestParam String type) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        Page<Order> orderPage = new Page<>();
        orderPage.setPageNo(page);
        orderPage.setPageSize(size);
        Order order = new Order();
        order.setUserId(khXx.getId());
        if ("1".equals(type)){
            order.setZt("");
        }
        if ("2".equals(type)){
            order.setZt("4");
        }
        if ("3".equals(type)){
            order.setZt(Order.PAY_STATUS_DZF);
        }
        if ("4".equals(type)){
            order.setZt(Order.PAY_STATUS_YZF);
        }
        if ("5".equals(type)){
            order.setZt("5");
        }
        order.setPage(orderPage);
        List<Order> orderList = orderService.findList(order);
        orderList.forEach(item ->{
            item.setSpXx(spXxService.get(item.getSpId()));
        });
        return orderPage.setList(orderList);
    }

    @ApiOperation(value = "getOrder", notes = "买方显示订单详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderid", value = "订单id", required = true)
    })
    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    public Response getOrder(@RequestParam String orderid) {
        Order order = orderService.get(orderid);
        order.setSpXx(spXxService.get(new SpXx(order.getSpId())));
        //已支付订单显示购买明细
        if (Order.PAY_STATUS_YZF.equals(order.getZt())){
            QyhsMx qyhsMx = new QyhsMx();
            qyhsMx.setOrderId(orderid);
            order.setQyhsMxList(qyhsMxService.findList(qyhsMx));
        }
        return new Response(order);
    }
}
