
package cn.com.czrz.util;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class InviteCode
{

    public void createCode(Integer userId, String path) throws Exception
    {
        String text = "http://192.168.1.154/phone/index!register.action?inviter="
                + userId;
        int width = 200;
        int height = 200;
        // ��ά���ͼƬ��ʽ
        String format = "gif";
        Hashtable hints = new Hashtable();
        // ������ʹ�ñ���
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                BarcodeFormat.QR_CODE, width, height, hints);
        // ��ɶ�ά��
        File outputFile = new File(path);
        QRUtil.writeToFile(bitMatrix, format, outputFile);
    }
}
