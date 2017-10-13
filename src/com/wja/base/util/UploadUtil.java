package com.wja.base.util;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {

    public static String uploadFile(MultipartFile file, String typeUpload,String path) {
        String originalFileName = file.getOriginalFilename();
        // ȡ��ǰ׺
        // String prefixString =
        // UUID.randomUUID().toString().replace("-","")+UUID.randomUUID().toString().replace("-","").substring(2,7);
        String prefixString = System.currentTimeMillis() + "" + getCharAndNumr(6, "03");
        // ȡ�ú�׺
        String suffixString = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // �趨�ļ�����
        String fileName = prefixString + "." + suffixString;
        // String uploadUrl = request.getSession().getServletContext().getRealPath("/") + "/mould/save_jsp/image/";
        String uploadUrl = path + typeUpload + "/";

        File dir = new File(uploadUrl);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File targetFile = new File(uploadUrl + fileName);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                file.transferTo(targetFile);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * java����������ֺ���ĸ��� 
     * @param length[����������ĳ���]
     * @param sytpe[����������Ĺ���]
     * @return
     */
    public static String getCharAndNumr(int length, String sytpe) {
        String val = "";
        Random random = new Random();
        switch (sytpe) {
        case "01":
            for (int i = 0; i < length; i++) {
                val += String.valueOf(random.nextInt(10));
            }
            break;
        case "02":
            for (int i = 0; i < length; i++) {
                val += (char) (65 + random.nextInt(26));
            }
            break;
        case "03":
            for (int i = 0; i < length; i++) {
                // �����ĸ��������
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                // �ַ���
                if ("char".equalsIgnoreCase(charOrNum)) {
                    // ȡ�ô�д��ĸ����Сд��ĸ
                    // int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    // ���ô�д
                    val += (char) (65 + random.nextInt(26));
                } else if ("num".equalsIgnoreCase(charOrNum)) { // ����
                    val += String.valueOf(random.nextInt(10));
                }
            }
            break;
        default:
            break;
        }
        return val;
    }
}
