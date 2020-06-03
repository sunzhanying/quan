package com.jeesite.API.controller.bright;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.bright.content.entity.meterail.Meterial;
import com.jeesite.modules.bright.content.entity.meterail.like.MeterialLike;
import com.jeesite.modules.bright.content.entity.meterail.visited.MeterialVisitedLog;
import com.jeesite.modules.bright.content.service.meterail.MeterialService;
import com.jeesite.modules.bright.content.service.meterail.like.MeterialLikeService;
import com.jeesite.modules.bright.content.service.meterail.visited.MeterialVisitedLogService;
import com.jeesite.modules.bright.setfocus.entity.meterialtype.MeterialType;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import com.jeesite.modules.bright.setfocus.service.meterialtype.MeterialTypeService;
import com.jeesite.modules.bright.setfocus.service.others.salesroom.SalesroomService;
import com.jeesite.modules.bright.setfocus.service.tag.TagService;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.service.khxxtag.KhXxTagService;
import com.jeesite.modules.sys.entity.Config;
import com.jeesite.modules.sys.service.ConfigService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jeesite.modules.bright.util.QRCodeUtil.qrCode;

@Api(description = "素材接口")
@RestController
@RequestMapping("${apiPath}/meterial")
public class ApiMeterialController {
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private MeterialService meterialService;
    @Autowired
    private MeterialVisitedLogService meterialVisitedLogService;
    @Autowired
    private MeterialTypeService meterialTypeService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TagService tagService;
    @Autowired
    private KhXxTagService khXxTagService;

    @Autowired
    private SalesroomService salesroomService;
    @Autowired
    private MeterialLikeService meterialLikeService;
    @Value("${qrCode_realm_name}")
    protected String qrCodeRealmName;

    @ApiOperation(value = "getMeterialTypeAll",notes = "获取所有素材类型",httpMethod ="GET")
    @RequestMapping(value = "/getMeterialTypeAll",method = RequestMethod.GET)
    public Response getMeterialTypeAll() {
        List<Map<String, Object>> mapList = ListUtils.newArrayList();
        List<MeterialType> list = meterialTypeService.findList(new MeterialType());
        for (int i=0; i<list.size(); i++){
            MeterialType e = list.get(i);
            // 过滤非正常的数据
            if (!MeterialType.STATUS_NORMAL.equals(e.getStatus())){
                continue;
            }
            if (!e.getParentCode().equals("0")){
                continue;
            }
            Map<String, Object> map = MapUtils.newHashMap();
            map.put("id", e.getId());
            map.put("pId", e.getParentCode());
            map.put("name", e.getName());
            mapList.add(map);
        }
        return new Response(mapList);
    }

    @ApiOperation(value = "isPropagate",notes = "判断是否是传播人",httpMethod ="GET")
    @RequestMapping(value = "/isPropagate",method = RequestMethod.GET)
    public Response isPropagate(HttpServletRequest request) {
        String global = configService.get(new Config("1150627636220055552")).getConfigValue(); //传播人权限
        KhXx khXx = (KhXx)request.getAttribute("khXx");
        String propagate=khXx.getPropagate();
        if("0".equals(global) && "0".equals(propagate)){ //传播人关闭 且用户传播权限为0时
            return new Response(Code.API_NULL_AUTH);
        }
        return new Response(Code.SUCCESS);
    }



    @ApiOperation(value = "getMeterialAll",notes = "获取所有素材",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页",required = false),
            @ApiImplicitParam(name="size",value = "每页多少条",required = false),
            @ApiImplicitParam(name="typeid",value = "素材类型id",required = false),
            @ApiImplicitParam(name="attributeName",value = "属性名称 单图 图集 视频 ",required = false),
            @ApiImplicitParam(name="sortid",value = "排序 null 不排序 1:热度 2:上架时间",required = false),
            @ApiImplicitParam(name="name",value = "搜索",required = false)
    })
    @RequestMapping(value = "/getMeterialAll",method = RequestMethod.GET)
    public Response getMeterialAll(@RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                             @RequestParam(required = false, value = "size", defaultValue = "10") Integer size,HttpServletRequest request,String typeid,String attributeName,String sortid,String name) {
        KhXx khXx = (KhXx)request.getAttribute("khXx");
        PageInfo<Meterial> pageInfo = meterialService.findAllList(page,size,typeid,attributeName,sortid,name);
        for (Meterial m:pageInfo.getList()
             ) {
            m.setTagList(getTag(m.getTagsId()));
            //是否关注
            m.setIsLike(isLike(m.getId(),khXx.getId()));
        }
        return new Response(pageInfo);
    }

    @ApiOperation(value = "getMeterialDetails",notes = "获取素材详情",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "素材id",required = true)
    })
    @RequestMapping(value = "/getMeterialDetails",method = RequestMethod.GET)
    public Response getMeterialDetails(@RequestParam String id,String sourceId, HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        MeterialVisitedLog log = new MeterialVisitedLog();
        log.setMeterialId(id);
        log.setKhId(khXx.getId());
        log.setSourceId(Strings.isNullOrEmpty(sourceId)?"1143741427839394733":sourceId); //"1143741427839394733"直接访问(后台不可删除)
        meterialVisitedLogService.save(log);
        Meterial m = meterialService.get(id);
        if(m !=null){
            m.setTagList(getTag(m.getTagsId()));
            //是否关注
            m.setIsLike(isLike(m.getId(),khXx.getId()));
            //添加用户标签记录
            if (m.getTagList().size() > 0){
                khXxTagService.addTags(khXx.getId(),m.getTagList());
            }
            //门店/传播渠道
            if(!StringUtil.isNullOrEmpty(khXx.getSalesroom())){
                 m.setSalesroom(salesroomService.get(khXx.getSalesroom()));
            }else {
                m.setSalesroom(salesroomService.getDefaultRoom());
            }
        }
        return new Response(m);
    }


    @ApiOperation(value = "getMeterialAttrAll",notes = "获取所有属性",httpMethod ="GET")
    @RequestMapping(value = "/getMeterialAttrAll",method = RequestMethod.GET)
    public Response getMeterialAttrAll() {
        return new Response(meterialService.getMeterialAttrAll());
    }


    @ApiOperation(value = "likeMeterial",notes = "关注素材",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "素材id",required = true)
    })
    @RequestMapping(value = "/likeMeterial",method = RequestMethod.POST)
    public Response likeMeterial(@RequestParam String id, HttpServletRequest request) {
         KhXx khXx=(KhXx)request.getAttribute("khXx");
         MeterialLike meterialLike = new MeterialLike();
         meterialLike.setKhId(khXx.getId());
         meterialLike.setMeterialId(id);
         MeterialLike m = meterialLikeService.getMeterialLike(meterialLike);
         if(m != null){
             m.setStatus((MeterialLike.STATUS_LIKE.equals(m.getStatus()))?MeterialLike.STATUS_DISLIKE:MeterialLike.STATUS_LIKE);
             meterialLikeService.updateStatus(m);
         }else {
             meterialLike.setStatus(MeterialLike.STATUS_LIKE);
             meterialLikeService.save(meterialLike);
         }
        m = meterialLikeService.getMeterialLike(meterialLike);
        return new Response(m.getStatus());
    }



    @ApiOperation(value = "getMeterialQrcode",notes = "生成素材二维码",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "素材id",required = true)
    })
    @RequestMapping(value = "/getMeterialQrcode",method = RequestMethod.GET)
    public Response getMeterialQrcode(@RequestParam String id,HttpServletRequest request) {
        KhXx khXx=(KhXx)request.getAttribute("khXx");
        String url = "";
        String text = qrCodeRealmName + "/meterials/?url=/pages/home/recipeDetail/recipeDetail&id="+id+"&khid="+khXx.getId();
        try {
            url =  qrCode(text,"meterials", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获得第一个点的位置
        int index=url.indexOf("/");
        System.out.println(index);
        //根据第一个点的位置 获得第二个点的位置
        index=url.indexOf("/", index+1);
        //根据第二个点的位置，截取 字符串。得到结果 result
        String result=url.substring(index);
        return new Response(result+id+".jpg");
    }

    private String isLike(String meterialId,String khid){
        MeterialLike meterialLike = new MeterialLike();
        meterialLike.setMeterialId(meterialId);
        meterialLike.setKhId(khid);
        MeterialLike meterialLike1 = meterialLikeService.getMeterialLike(meterialLike);
       return meterialLike1==null?MeterialLike.STATUS_DISLIKE:meterialLike1.getStatus();
    }


    private List<Tag> getTag(String str){
        String [] arr = str.split(",");
        List<Tag> tags = new ArrayList<>();
        for (String s:arr) {
            Tag tag = tagService.get(s);
            if(tag!= null){
                tags.add(tag);
            }
        }

        return  tags;
    }



    @ApiOperation(value = "getRecommend",notes = "更多推荐",httpMethod ="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "素材id",required = false)
    })
    @RequestMapping(value = "/getRecommend",method = RequestMethod.GET)
    public Response getRecommend(@RequestParam(required = false) String id) {
        if(!Strings.isNullOrEmpty(id)){
            Meterial meterial = meterialService.get(id);
            return new Response(meterialService.getRecommendByAttribute(meterial));
        }
        return new Response(meterialService.getRecommendByLike());

    }

}
