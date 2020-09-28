package com.jeesite.API.controller.bright;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.util.RedisTemplateUtils;
import com.jeesite.API.weixin.api.SnsAPI;
import com.jeesite.API.weixin.api.TokenAPI;
import com.jeesite.API.weixin.bean.token.Token;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.setfocus.dao.tag.TagDao;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.entity.sptype.SpLog;
import com.jeesite.modules.bright.sp.entity.sptype.SpType;
import com.jeesite.modules.bright.sp.service.SpXxService;
import com.jeesite.modules.bright.sp.service.sptype.SpLogService;
import com.jeesite.modules.bright.sp.service.sptype.SpTypeService;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.collect.entity.Collect;
import com.jeesite.modules.collect.service.CollectService;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

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

    //卖方
    @Value("${weixin.appid}")
    private String wxAppId;
    @Value("${weixin.appsecret}")
    private String wxAppSecret;

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

    @Autowired
    private SpLogService spLogService;

    private final Log logger = LogFactory.getLog(getClass());

    @RequestMapping("/testQR")
    @ResponseBody
    public Map testQR(@RequestParam String url) throws Exception {
        String text = getQrCodeByUrl(url);
        Map map = new HashMap();
        map.put("text",text);
        return map;
    }

    private String getQrCodeByUrl(String url) throws Exception{
        MultiFormatReader formatReader = new MultiFormatReader();
        File file = new File(url);

        BufferedImage image = ImageIO.read(file);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        Result result = formatReader.decode(binaryBitmap, hints);

        logger.info("二维码解析结果：" + result.toString());
        logger.info("二维码的格式：" + result.getBarcodeFormat());
        logger.info("二维码的文本内容：" + result.getText());
        return result.getText();
    }

    //识别二维码
    @RequestMapping(value = "/getQRCode",method = RequestMethod.POST)
    public Response getQRCode(@RequestParam String url) {
        logger.info("getQRCode url:" + url);
        ///卖方
        Token token = TokenAPI.token(wxAppId, wxAppSecret);
        logger.info("getQRCode token:" + token);
        if (!token.isSuccess()) {
            logger.info("getQRCode is fail.");
            return new Response(Code.API_USER_AUTH_ERROR);
        }
        JSONObject jsonObject = SnsAPI.getQRInfo(token.getAccess_token(),url);
        logger.info("getQRCode jsonObject:" + JSONObject.toJSONString(jsonObject));
        return new Response(jsonObject);
    }

    //上传文件，不可删除
    @RequestMapping({"upload"})
    public Map<String, Object> upload(FileUploadParams params) {
        logger.info("进入上传  upload begin");
        params.setFileMd5(UUID.randomUUID().toString());
        //params.setFileName(params.getFile().getName());
        params.setFileName(params.getFile().getOriginalFilename());
        //logger.info("打印参数 params getFileMd5：" + params.getFileMd5());
        //logger.info("打印参数 params setFileName：" + params.getFileName());
        Map<String, Object> returnMap = fileUploadService.uploadFile(params);
        //根据文件id，获取文件路径，然后获取二维码值
        getAndSetTextById(returnMap);
        /*if(returnMap != null){
            for(Map.Entry<String,Object> entry: returnMap.entrySet()){
                logger.info("return key:" + entry.getKey() + "; value:" + entry.getValue());
            }
        }*/
        logger.info("最终返回map打印 returnMap：" + returnMap);
        return returnMap;
    }

    private void getAndSetTextById(Map<String, Object> returnMap) {
        logger.info("进入方法 getAndSetTextById returnMap：" + returnMap);
        returnMap.put("sunzy","0711");
        try{
            if(returnMap == null || returnMap.isEmpty()){
                return;
            }
            FileUpload fileUpload = (FileUpload)returnMap.get("fileUpload");
            if(fileUpload == null){
                return;
            }
            FileEntity fileEntity = fileUpload.getFileEntity();
            logger.info("打印fileEntity：" + fileEntity);
            if(fileEntity == null){
                return;
            }
            StringBuffer sb = new StringBuffer();//线程安全
            sb.append("/root/jeesite/userfiles/fileupload/");
            sb.append(fileEntity.getFilePath());
            sb.append(fileEntity.getFileId());
            sb.append(".");
            sb.append(fileEntity.getFileExtension());
            //sb.append(".jpg");
            //     /root/jeesite/userfiles/fileupload/202007/1280510819048443905.jpg
            logger.info("解析服务器图片路径：" + sb.toString());
            String text = getQrCodeByUrl(sb.toString());
            logger.info("二维码解析出的文本内容 text：" + text);
            returnMap.put("text",text);
        }catch (Exception e){
             logger.error("解析二维码图片失败，getAndSetTextById error：",e);
        }
    }

    @ApiOperation(value = "getSpTypeAll", notes = "获取所有权益券类型", httpMethod = "GET")
    @RequestMapping(value = "/getSpTypeAll", method = RequestMethod.GET)
    public Response getSpTypeAll() {
        SpType spType = new SpType();
        spType.setSxj(SpType.SPTYPE_DISPLAY_YES);
        //全部，包括1级和2级
        List<SpType> list = spTypeService.findList(spType);
        List<SpType> listForReturn = new ArrayList<>();
        //遍历出1级
        for(SpType spTypeTemp:list){
            if(spTypeTemp == null){
                continue;
            }
            if(spTypeTemp.getParent() != null && !"".equals(spTypeTemp.getParent())){//必须二级parent为空
                continue;
            }
            listForReturn.add(spTypeTemp);
        }
        //新增二级目录
        for(SpType spTypeTemp:listForReturn){
            String parentIdTemp = spTypeTemp.getId();
            List<SpType> innerList = new ArrayList<>();
            for(SpType spTypeAll:list){//遍历全部的类型
                if(spTypeAll == null){
                    continue;
                }
                if(parentIdTemp.equals(spTypeAll.getParent())){//如果1级目录id与二级目录parent相同，则保留
                    innerList.add(spTypeAll);
                }
            }
            spTypeTemp.setInnerList(innerList);
        }

        return new Response(listForReturn);
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
        /*spXx.setSplx(typeid);
        spXx.getSqlMap().getWhere().andBracket("spmc", QueryType.LIKE, name)
                .or("spfmc", QueryType.LIKE, name).endBracket();*/
        if(typeid == null || "".equals(typeid)){//如果不传商品id，则获取全部
            spXx.getSqlMap().getWhere().andBracket("spmc", QueryType.LIKE, name)
                    .or("spfmc", QueryType.LIKE, name).endBracket();
        }else{
            List<String> stringList = getSplxListByParent(typeid);
            stringList.add(typeid);//将1级id也加入，兼容买家
            spXx.getSqlMap().getWhere().and("splx", QueryType.IN, stringList.toArray()).andBracket("spmc", QueryType.LIKE, name)
                    .or("spfmc", QueryType.LIKE, name).endBracket();
        }
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

            //是否允许上传，1 不允许
            int count = qyhsService.countByQyqAndZt(item.getId());
            if(item.getMaxCount() != null && item.getMaxCount() > 0 && count >= item.getMaxCount()){
                item.setMaxCountFlag(1L);
            }else{
                item.setMaxCountFlag(0L);
            }
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

    private List<String> getSplxListByParent(String typeid) {
        List<String> stringList = spTypeService.findTwoIds(typeid);
        return stringList;
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
        log.info("进入 addQy 方法");
        if(qyhs == null || qyhs.getQyhsMxes() == null || qyhs.getQyhsMxes().isEmpty()){
            return new Response(Code.API_CHECK_NULL);
        }
        String qyqIdTemp = qyhs.getQyqId();
        log.info("qyqIdTemp：" + qyqIdTemp);
        //新增逻辑，卖家券上传时候确定收益
        Qyjg qyjg = new Qyjg();
        qyjg.setQyqId(qyqIdTemp);
        qyjg.setPageSize(1);
        Qyjg qyjg1 = qyjgService.findList(qyjg).get(0);//从后台取，以防止前端模拟数据来提高卖家的收益
        //先从mx明细表中 校验卡密不能重复;校验回收最大数量
        for(QyhsMx mxForSave : qyhs.getQyhsMxes()){
            //QyhsMx mxForSave = qyhs.getQyhsMxes().get(0);//目前前端只允许上次一个卖券
            QyhsMx mxForCheck = new QyhsMx();
            if(mxForSave.getKm() != null && !"".equals(mxForSave.getKm())){
                mxForCheck.setKm(mxForSave.getKm());
            }
            if(mxForSave.getKh() != null && !"".equals(mxForSave.getKh())){
                mxForCheck.setKh(mxForSave.getKh());
            }
            long count = qyhsMxService.findCount(mxForCheck);
            if(count > 0){
                return new Response(Code.API_CHECK_KM);
            }
            mxForSave.setSy(qyjg1.getHsj());
        }

        //校验上传最大数量，根据qyq_id，也就是商品id,获取到商品对应的设置数量
        SpXx spXx = spXxService.get(qyqIdTemp);
        Long maxCount = spXx.getMaxCount();
        //根据权益券
        int count = qyhsService.countByQyqAndZt(qyqIdTemp);
        count = count + qyhs.getQyhsMxes().size();
        if(maxCount != null && maxCount > 0 && count > maxCount){
            return new Response(Code.API_CHECK_COUNT);
        }

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
        if ("3".equals(type)){//查询已售出
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
        for(QyhsMx item :qyhsMxList){
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
        }
        //按照提交时间倒序排列
        Collections.sort(qyhsMxList, new Comparator<QyhsMx>(){//先按照成绩从小到大排序，如果成绩相等则按照年龄从小到大 如果是String则用 String类的compareTo方法
            public int compare(QyhsMx o1, QyhsMx o2) {
                Date d1 = o1.getUpdateDate();
                Date d2 = o2.getUpdateDate() ;
                if(d1 == null || d2 == null){
                    return 0;
                }
                if(d1.before(d2)){
                    return 1;
                }else{
                    return -1;
                }
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


    /**
     * 获取商品名称下拉提示列表
     */
    @RequestMapping(value = "/getSpNames", method = RequestMethod.GET)
    public Page<SpXx> getSpNames(HttpServletRequest request, @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
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

    /**
     * 记录查询日志
     */
    @RequestMapping(value = "/saveSpLog", method = RequestMethod.POST)
    public Response saveSpLog(HttpServletRequest request,
                                 String spId,String type, String name) {
        if(spId == null && name == null){
            return new Response(Code.API_CHECK_NULL);
        }
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        if(khXx == null || khXx.getId() == null){
            return new Response(Code.API_CHECK_NULL);
        }
        SpLog spLogOld = spLogService.getLogsByKhidAndSpid(khXx.getId(),spId);
        if(spLogOld == null || spLogOld.getId() == null || "".equals(spLogOld.getId())){
            //新增
            SpLog spLog = new SpLog();
            spLog.setKhid(khXx.getId());
            spLog.setSpid(spId);
            spLog.setType(type);
            spLog.setName(name);
            spLogService.save(spLog);
        }else{
            //修改
            spLogOld.setCreateDate(new Date());
            spLogOld.setType(type);
            spLogOld.setName(name);
            spLogService.save(spLogOld);
        }
        return new Response("ok");
    }

    /**
     * 记录查询日志
     */
    @RequestMapping(value = "/getSpLog", method = RequestMethod.GET)
    public Response getSpLog(HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        if(khXx == null || khXx.getId() == null){
            return new Response(Code.API_CHECK_NULL);
        }
        List<SpLog> list = spLogService.getLogs(khXx.getId());
        return new Response(list);
    }
}
