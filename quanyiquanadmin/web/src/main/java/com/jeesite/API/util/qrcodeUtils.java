package com.jeesite.API.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.util.Hashtable;


class CreateQRCode {
    public static void main(String[] args) {
        String str = "胡汉三：男 座右铭：Hard Word Pays Off 天道酬勤......";// 二维码内容
        String path = "C:\\Users\\nmf\\Desktop\\aaa.png";
        BitMatrix byteMatrix;
        try {

            Hashtable<EncodeHintType, Integer> hints = new Hashtable<EncodeHintType, Integer>();
            hints.put(EncodeHintType.MARGIN, 1); //设置二维码空白边框的大小 1-4，1是最小 4是默认的国标

            byteMatrix = new MultiFormatWriter().encode(new String(str.getBytes("UTF-8"),"iso-8859-1"),
                    BarcodeFormat.QR_CODE, 200, 200,hints);

            File file = new File(path);
            MatrixToImageWriter.writeToFile(byteMatrix, "png", file);
        }  catch (Exception e) {
            e.printStackTrace();
        }

    }
}
