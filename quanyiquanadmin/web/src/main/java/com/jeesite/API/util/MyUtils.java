/*
package com.thinkgem.jeesite.API.util;


import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.DictLabelDao;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SendMailUtil;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.address.CompanyAddress;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.interaction.entity.offer.InteractionOffer;
import com.thinkgem.jeesite.modules.interaction.entity.recommend.InteractionRecommend;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.undersorder.OrderUnders;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class MyUtils {




    public static String rand(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }


    public static String numberFormat(String str, Integer no) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(no);
        return nf.format(Double.valueOf(str));
    }


    */
/**
     * 生成6位短信验证码
     *
     * @return
     *//*

    public static String generateSmsVerifyCode() {
        long now = System.currentTimeMillis();

        String code = "";
        for (int i = 0; i < 6; ++i) {
            code += now % 10;
            now >>= 1;
        }

        return code;

    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return res;
    }

    */
/**
     * 通过经纬度获取距离(单位：米)	 *
     * *
     *
     * @param lat1     *
     * @param lng1     *
     * @param lat2     *
     * @param lng2     *
     * @return 距离
     *//*

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    */
/**
     * 16      * 指定日期加上天数后的日期
     * 17      * @param num 为增加的天数
     * 18      * @param newDate 创建时间
     * 19      * @return
     * 20      * @throws ParseException
     * 21
     *//*

    public static Date plusDay(int num, Date currdate, int type) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        if (type == 1) {
            ca.add(Calendar.YEAR, num);
        } else if (type == 2) {
            ca.add(Calendar.MONTH, num);
        } else if (type == 3) {
            ca.add(Calendar.DATE, num);
        }
        return ca.getTime();
    }

    */
/**
     * 16      * 指定日期加上天数后的日期
     * 17      * @param num 为增加的天数
     * 18      * @param newDate 创建时间
     * 19      * @return
     * 20      * @throws ParseException
     * 21
     *//*

    public static String plusDay(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }


    */
/**
     * 16      * 指定日期加上天数后的日期
     * 17      * @param num 为增加的天数
     * 18      * @param type 1年 2月 3日
     * 19      * @return
     * 20      * @throws ParseException
     * 21
     *//*

    public static Date plusDay(int num, int type) throws ParseException {
        Calendar ca = Calendar.getInstance();
        if (type == 1) {
            ca.add(Calendar.YEAR, num);
        } else if (type == 2) {
            ca.add(Calendar.MONTH, num);
        } else if (type == 3) {
            ca.add(Calendar.DATE, num);
        }
        // num为增加的天数，可以改变的
        return ca.getTime();
    }


    */
/**
     * 提取Excel中的内容
     *//*

    public static List<String> extract(File file_name) {
        //List<Member> members = new ArrayList<>();
        List<String> list = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(file_name);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);


            //只读取第一个sheet进行处理
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            //处理当前sheet，循环读取每一行
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++) {
                //System.out.println(sheet.getLastRowNum());
                XSSFRow xss_row = sheet.getRow(row_num);
                list.add(getCellValue(xss_row.getCell(0)));
            }
            //}
        } catch (Exception e) {
            try {
                InputStream is = new FileInputStream(file_name);


                HSSFWorkbook lssfWorkbook = new HSSFWorkbook(is);


                //只读取第一个sheet进行处理
                HSSFSheet sheet = lssfWorkbook.getSheetAt(0);

                //处理当前sheet，循环读取每一行
                for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++) {
                    //System.out.println(sheet.getLastRowNum());
                    HSSFRow xss_row = sheet.getRow(row_num);


                    list.add(getCellValue(xss_row.getCell(0)));
                }
            } catch (Exception Ee) {
                Ee.printStackTrace();
            }
            return list;
        }
        return list;
    }

    */
/**
     * 根据excel单元格类型获取excel单元格值
     *
     * @param cell
     * @return
     *//*

    public static String getCellValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC: {
                    short format = cell.getCellStyle().getDataFormat();
                    if (format == 14 || format == 31 || format == 57 || format == 58) {    //excel中的时间格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = DateUtil.getJavaDate(value);
                        cellvalue = sdf.format(date);
                    }
                    // 判断当前的cell是否为Date
                    else if (HSSFDateUtil.isCellDateFormatted(cell)) {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
                        // 如果是Date类型则，取得该Cell的Date值           // 对2014-02-02格式识别不出是日期格式
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = formater.format(date);
                    } else { // 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());

                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getStringCellValue().replaceAll("'", "''");
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    cellvalue = null;
                    break;
                // 默认的Cell值
                default: {
                    cellvalue = " ";
                }
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }


    */
/**
     * @return
     * @descript:计算期相差的天数
     *//*

    public static int dayDiff(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        long diff = 0L;
        try {
            long d1 = formater.parse(formater.format(date)).getTime();
            long d2 = formater.parse(formater.format(new Date())).getTime();
            ;
            //diff=(Math.abs(d1-d2) / (1000 * 60 * 60 * 24));
            diff = (d2 - d1) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) diff;
    }
    */
/**
     * @return
     * @descript:计算期秒的
     *//*

    public static int secondDiff(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
        long diff = 0L;
        try {
            long d1 = formater.parse(formater.format(date)).getTime();
            long d2 = formater.parse(formater.format(new Date())).getTime();
            ;
            //diff=(Math.abs(d1-d2) / (1000 * 60 * 60 * 24));
            diff = (d2 - d1) / (1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) diff;
    }

    */
/**
     * 获取当前年月日等
     *
     * @return
     *//*

    public static Integer getTimes(Integer integer) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(integer);
    }

    */
/**
     * 16进制字符串转2进制字符串
     *
     * @param hexString
     * @return
     *//*


    public static String hexString2binaryString(String hexString) {
        if (hexString == null) {
            return null;
        }
       */
/* if( hexString.length() % 2 != 0){
            hexString="0"+hexString;
        }*//*

        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    */
/**
     * 2进制字符串转16进制字符串
     *
     * @param bString
     * @return
     *//*

    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("")) {

            return null;
        }
        while (bString.length() % 4 != 0) {
            bString = "0" + bString;
        }
        if (bString.substring(0, 4).equals("0000")) {
            bString = bString.substring(4);
        }
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    public static  List setLabelList(List list) {
        for (Object o : list) {
            o = setLabel(o);
        }
        return list;
    }

    public static Object setLabel(Object o) {
        //
        if (o instanceof Map && "2".equals(((Map) o).get("type"))) {
            ((Map) o).put("status",DictUtils.getDictLabelObject(((Map) o).get("status"), "pay_status", ""));
            ((Map) o).put("payType",DictUtils.getDictLabelObject(((Map) o).get("payType"), "pay_type", ""));
            ((Map) o).put("createDate",DateUtils.formatDate((Date) ((Map) o).get("createDate"),"yyyy-MM-dd HH:mm"));
            if(((Map) o).get("payTime")!=null){
                ((Map) o).put("payTime",DateUtils.formatDate((Date) ((Map) o).get("payTime"),"yyyy-MM-dd HH:mm"));
            }
        }
        if (o instanceof Map && "1".equals(((Map) o).get("type"))) {
            ((Map) o).put("status",DictUtils.getDictLabelObject(((Map) o).get("status"), "pay_status", ""));
            ((Map) o).put("payType",DictUtils.getDictLabelObject(((Map) o).get("payType"), "vip_pay_type", ""));
            ((Map) o).put("createDate",DateUtils.formatDate((Date) ((Map) o).get("createDate"),"yyyy-MM-dd HH:mm"));
            if(((Map) o).get("payTime")!=null){
                ((Map) o).put("payTime",DateUtils.formatDate((Date) ((Map) o).get("payTime"),"yyyy-MM-dd HH:mm"));
            }
        }

        if (o instanceof OrderInvoices) {
            ((OrderInvoices) o).setStatus(DictUtils.getDictLabelObject(((OrderInvoices) o).getStatus(), "invoice_status", ""));
        }
        if (o instanceof OrderUnders) {
            ((OrderUnders) o).setStatus(DictUtils.getDictLabelObject(((OrderUnders) o).getStatus(), "pay_status", ""));
            ((OrderUnders) o).setPayType(DictUtils.getDictLabelObject(((OrderUnders) o).getPayType(), "pay_type", ""));


            setLabel(((OrderUnders) o).getOrderInvoices());
        }
        if (o instanceof School) {
            ((School) o).setF211(DictUtils.getDictLabelObject(((School) o).getF211(), "yes_no", ""));
            ((School) o).setF985(DictUtils.getDictLabelObject(((School) o).getF985(), "yes_no", ""));
            ((School) o).setFD11(DictUtils.getDictLabelObject(((School) o).getFD11(), "yes_no", ""));
            ((School) o).setType(DictUtils.getDictLabelObject(((School) o).getType(), "school_type", ""));
            ((School) o).setNatrue(DictUtils.getDictLabelObject(((School) o).getNatrue(), "school_nature", ""));

            ((School) o).setCity(new DictLabelDao(((School) o).getCity(),DictUtils.getAreaLabel((String) ((School) o).getCity()) ));

        }
        if (o instanceof AccountStudentinfo) {
            ((AccountStudentinfo) o).setEducationBackground(DictUtils.getDictLabelObject(((AccountStudentinfo) o).getEducationBackground(), "student_edu_back", ""));
            ((AccountStudentinfo) o).setSex(DictUtils.getDictLabelObject(((AccountStudentinfo) o).getSex(), "sex", ""));
            ((AccountStudentinfo) o).setIdCardStatus(DictUtils.getDictLabelObject(((AccountStudentinfo) o).getIdCardStatus(), "authentication", ""));
            ((AccountStudentinfo) o).setStudentIdStatus(DictUtils.getDictLabelObject(((AccountStudentinfo) o).getStudentIdStatus(), "authentication", ""));
            ((AccountStudentinfo) o).setIncumbency(DictUtils.getDictLabelObject(((AccountStudentinfo) o).getIncumbency(), "yes_no", ""));

            ((AccountStudentinfo) o).setMajor(new DictLabelDao(((AccountStudentinfo) o).getMajor(),DictUtils.getMajorLabel((String) ((AccountStudentinfo) o).getMajor()) ));
            ((AccountStudentinfo) o).setAge(DateUtils.getYearDiff(((AccountStudentinfo) o).getBirthday()));
        }
        if (o instanceof AccountCompanyhr) {
             ((AccountCompanyhr) o).setSex(DictUtils.getDictLabelObject(((AccountCompanyhr) o).getSex(), "sex", ""));
            ((AccountCompanyhr) o).setStatus(DictUtils.getDictLabelObject(((AccountCompanyhr) o).getStatus(), "authentication", ""));
            ((AccountCompanyhr) o).setMajor(new DictLabelDao(((AccountCompanyhr) o).getMajor(),DictUtils.getMajorLabel((String) ((AccountCompanyhr) o).getMajor()) ));
        }
        if (o instanceof Company) {
            ((Company) o).setScale(DictUtils.getDictLabelObject(((Company) o).getScale(), "company_scale", ""));
            ((Company) o).setStatus(DictUtils.getDictLabelObject(((Company) o).getStatus(), "authentication", ""));
            ((Company) o).setNature(DictUtils.getDictLabelObject(((Company) o).getNature(), "company_nature", ""));

            ((Company) o).setIndustry(new DictLabelDao(((Company) o).getIndustry(),DictUtils.getindustryLabel((String) ((Company) o).getIndustry()) ));


            if(!Strings.isNullOrEmpty((String) ((Company) o).getImg())){
                ((Company) o).setImg((""+((Company) o).getImg()).substring(1).split("\\|"));
            }else{
                ((Company) o).setImg(new String[0]);
            }

        }
        if (o instanceof CompanyAddress) {

            ((CompanyAddress) o).setProvince(new DictLabelDao(((CompanyAddress) o).getProvince(),DictUtils.getAreaLabel((String) ((CompanyAddress) o).getProvince()) ));
            ((CompanyAddress) o).setArea(new DictLabelDao(((CompanyAddress) o).getArea(),DictUtils.getAreaLabel((String) ((CompanyAddress) o).getArea()) ));
            ((CompanyAddress) o).setCity(new DictLabelDao(((CompanyAddress) o).getCity(),DictUtils.getAreaLabel((String) ((CompanyAddress) o).getCity()) ));

        }
        if (o instanceof CompanyProfession) {
           // ((CompanyProfession) o).setExperience(DictUtils.getDictLabelObject(((CompanyProfession) o).getExperience(), "work_experience", ""));
            ((CompanyProfession) o).setEducationalBackground(DictUtils.getDictLabelObject(((CompanyProfession) o).getEducationalBackground(), "student_edu_back", ""));
            ((CompanyProfession) o).setSalary(DictUtils.getDictLabelObject(((CompanyProfession) o).getSalary(), "salary", ""));
            //((CompanyProfession) o).setTrafficSubsidy(DictUtils.getDictLabelObject(((CompanyProfession) o).getTrafficSubsidy(), "yes_no", ""));
            ((CompanyProfession) o).setBoard(DictUtils.getDictLabelObject(((CompanyProfession) o).getBoard(), "board", ""));
            ((CompanyProfession) o).setNature(DictUtils.getDictLabelObject(((CompanyProfession) o).getNature(), "profession_nature", ""));

            //((CompanyProfession) o).setMajor(new DictLabelDao(((CompanyProfession) o).getMajor(),DictUtils.getMajorLabel((String) ((CompanyProfession) o).getMajor()) ));
            ((CompanyProfession) o).setCity(new DictLabelDao(((CompanyProfession) o).getCity(),DictUtils.getAreaLabel((String) ((CompanyProfession) o).getCity()) ));
            ((CompanyProfession) o).setType(new DictLabelDao(((CompanyProfession) o).getType(),DictUtils.getpositionLabel((String) ((CompanyProfession) o).getType()) ));

        }
        if (o instanceof InteractionTeachin) {
            ((InteractionTeachin) o).setTime(DictUtils.getDictLabelObject(((InteractionTeachin) o).getTime(), "teachin_time", ""));
            ((InteractionTeachin) o).setStatus(DictUtils.getDictLabelObject(((InteractionTeachin) o).getStatus(), "teachin_status", ""));


        }
        if (o instanceof InteractionInterview) {
            ((InteractionInterview) o).setInterviewType(DictUtils.getDictLabelObject(((InteractionInterview) o).getInterviewType(), "interview_type", ""));
            ((InteractionInterview) o).setInterviewStatus(DictUtils.getDictLabelObject(((InteractionInterview) o).getInterviewStatus(), "interview_status", ""));
            ((InteractionInterview) o).setInterviewStep(DictUtils.getDictLabelObject(((InteractionInterview) o).getInterviewStep(), "interview_step", ""));
        }
        if (o instanceof MessageLog) {
            ((MessageLog) o).setType(DictUtils.getDictLabelObject(((MessageLog) o).getType(), "message_type", ""));
            ((MessageLog) o).setInterviewType(DictUtils.getDictLabelObject(((MessageLog) o).getInterviewType(), "interview_type", ""));
            ((MessageLog) o).setIsRead(DictUtils.getDictLabelObject(((MessageLog) o).getIsRead(), "is_read", ""));
        }

        if (o instanceof InteractionRecommend) {
            ((InteractionRecommend) o).setType(DictUtils.getDictLabelObject(((InteractionRecommend) o).getType(), "recommend_type", ""));
            ((InteractionRecommend) o).setLinkType(DictUtils.getDictLabelObject(((InteractionRecommend) o).getLinkType(), "recommend_link_type", ""));
         }
        if (o instanceof StudentResumeEducation) {
            ((StudentResumeEducation) o).setEducationBackground(DictUtils.getDictLabelObject(((StudentResumeEducation) o).getEducationBackground(), "student_edu_back", ""));
            ((StudentResumeEducation) o).setMajor(new DictLabelDao(((StudentResumeEducation) o).getMajor(),DictUtils.getMajorLabel((String) ((StudentResumeEducation) o).getMajor()) ));
            }
        if (o instanceof StudentResumePrefer) {
            ((StudentResumePrefer) o).setSalary(DictUtils.getDictLabelObject(((StudentResumePrefer) o).getSalary(), "salary", ""));

            ((StudentResumePrefer) o).setPositionId(new DictLabelDao(((StudentResumePrefer) o).getPositionId(),DictUtils.getindustryLabel((String) ((StudentResumePrefer) o).getPositionId()) ));
            ((StudentResumePrefer) o).setProfessionId(new DictLabelDao(((StudentResumePrefer) o).getProfessionId(),DictUtils.getpositionLabel((String) ((StudentResumePrefer) o).getProfessionId()) ));
            ((StudentResumePrefer) o).setCity(new DictLabelDao(((StudentResumePrefer) o).getCity(),DictUtils.getAreaLabel((String) ((StudentResumePrefer) o).getCity()) ));

        }
        if (o instanceof InteractionOffer) {
            ((InteractionOffer) o).setProvince(new DictLabelDao(((InteractionOffer) o).getProvince(),DictUtils.getAreaLabel((String) ((InteractionOffer) o).getProvince()) ));
             ((InteractionOffer) o).setCity(new DictLabelDao(((InteractionOffer) o).getCity(),DictUtils.getAreaLabel((String) ((InteractionOffer) o).getCity()) ));
            ((InteractionOffer) o).setStatus(DictUtils.getDictLabelObject(((InteractionOffer) o).getStatus(), "offer_status", ""));
        }
        if (o instanceof Order) {
            ((Order) o).setStatus(DictUtils.getDictLabelObject(((Order) o).getStatus(), "pay_status", ""));
            ((Order) o).setPayType(DictUtils.getDictLabelObject(((Order) o).getPayType(), "pay_type", ""));

            setLabel(((Order) o).getTeachin());
            setLabel(((Order) o).getOrderInvoices());
        }
        if (o instanceof OrderVip) {
            ((OrderVip) o).setStatus(DictUtils.getDictLabelObject(((OrderVip) o).getStatus(), "pay_status", ""));
            ((OrderVip) o).setPayType(DictUtils.getDictLabelObject(((OrderVip) o).getPayType(), "vip_pay_type", ""));

            setLabel(((OrderVip) o).getOrderInvoices());
        }
        if (o instanceof Vip) {

            ((Vip) o).setOffCampusInformation(DictUtils.getDictLabelObject(((Vip) o).getOffCampusInformation(), "vip_equity", ""));
            ((Vip) o).setProfession(DictUtils.getDictLabelObject(((Vip) o).getProfession(), "vip_equity", ""));
            ((Vip) o).setUnderTeachinRead(DictUtils.getDictLabelObject(((Vip) o).getUnderTeachinRead(), "vip_equity", ""));
            ((Vip) o).setUnderTeachinSign(DictUtils.getDictLabelObject(((Vip) o).getUnderTeachinSign(), "vip_equity", ""));
            ((Vip) o).setDoubleRead(DictUtils.getDictLabelObject(((Vip) o).getDoubleRead(), "vip_equity", ""));
            ((Vip) o).setDoubleSign(DictUtils.getDictLabelObject(((Vip) o).getDoubleSign(), "vip_equity", ""));
            ((Vip) o).setExchangeMeeting(DictUtils.getDictLabelObject(((Vip) o).getExchangeMeeting(), "vip_equity", ""));
            ((Vip) o).setPayStatus(DictUtils.getDictLabelObject(((Vip) o).getPayStatus(), "vip_pay_status", ""));
              }
        if (o instanceof VipAccount) {

            ((VipAccount) o).setOffCampusInformation(DictUtils.getDictLabelObject(((VipAccount) o).getOffCampusInformation(), "vip_equity", ""));
            ((VipAccount) o).setProfession(DictUtils.getDictLabelObject(((VipAccount) o).getProfession(), "vip_equity", ""));
            ((VipAccount) o).setUnderTeachinRead(DictUtils.getDictLabelObject(((VipAccount) o).getUnderTeachinRead(), "vip_equity", ""));
            ((VipAccount) o).setUnderTeachinSign(DictUtils.getDictLabelObject(((VipAccount) o).getUnderTeachinSign(), "vip_equity", ""));
            ((VipAccount) o).setDoubleRead(DictUtils.getDictLabelObject(((VipAccount) o).getDoubleRead(), "vip_equity", ""));
            ((VipAccount) o).setDoubleSign(DictUtils.getDictLabelObject(((VipAccount) o).getDoubleSign(), "vip_equity", ""));
            ((VipAccount) o).setExchangeMeeting(DictUtils.getDictLabelObject(((VipAccount) o).getExchangeMeeting(), "vip_equity", ""));
              }
        return o;
    }

    public static void main(String[] args){

        SendMailUtil.sendCommonMail("1055341558@qq.com","zhuti","neir");



    }


    public static MessageLog createMessage(InteractionInterview interview) {

            MessageLog messageLog=new MessageLog();
            messageLog.setStudentId(interview.getStudentId());
            messageLog.setCompanyProfessionId(interview.getCompanyProfessionId());
            messageLog.setCompanyId(interview.getCompanyId());
            messageLog.setInterviewId(interview.getId());
            messageLog.setInterviewTime(interview.getInterviewTime());
            messageLog.setInterviewType(interview.getInterviewType());
            messageLog.setIsRead(MessageLog.ISREAD_NO);
            messageLog.setTeachinId(interview.getTeachinId());
            return messageLog;

    }



    public static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    */
/*
    * KEY+ streamName + txTime
    *//*


    public static String getSafeUrl(String key, String streamName, long txTime) {
        String input = new StringBuilder().
                append(key).
                append(streamName).
                append(Long.toHexString(txTime).toUpperCase()).toString();

        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret  = byteArrayToHexString(
                    messageDigest.digest(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return txSecret == null ? "" :
                new StringBuilder().
                        append("txSecret=").
                        append(txSecret).
                        append("&").
                        append("txTime=").
                        append(Long.toHexString(txTime).toUpperCase()).
                        toString();
    }

    public static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];

        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

    public static List accountStudentinfoSort(List<AccountStudentinfo> list) {
        List l = new ArrayList();
        int start = 0;
        //26字母+#
        for (int i = 0; i <= 26; i++) {
            Map<Object, List> m = new HashMap();

            //最后一个直接全部取出
            if (i == 26) {
                m.put("#", list.subList(start, list.size()));
            } else {
                m.put((char) (97 + i), new ArrayList());
                for (int a = 0; a < list.size(); a++) {
                    if (ChineseUtils.getFirstLetter(list.get(a).getRealname()).equals("" + (char) (97 + i))) {
                        m.get((char) (97 + i)).add(list.get(a));
                        list.remove(a);
                        a--;
                    }
                }
            }

            l.add(m);
        }
        return l;
    }
    public static List companySort(List<Company> list) {
        List l = new ArrayList();
        int start = 0;
        //26字母+#
        for (int i = 0; i <= 26; i++) {
            Map<Object, List> m = new HashMap();

            //最后一个直接全部取出
            if (i == 26) {
                m.put("#", list.subList(start, list.size()));
            } else {
                m.put((char) (97 + i), new ArrayList());
                for (int a = 0; a < list.size(); a++) {
                    if (ChineseUtils.getFirstLetter(list.get(a).getName()).equals("" + (char) (97 + i))) {
                        m.get((char) (97 + i)).add(list.get(a));
                        list.remove(a);
                        a--;
                    }
                }
            }

            l.add(m);
        }
        return l;
    }
}
*/
