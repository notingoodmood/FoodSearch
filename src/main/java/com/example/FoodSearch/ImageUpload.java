package com.example.FoodSearch;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

@WebServlet(urlPatterns={"/ImageUpload"})
public class ImageUpload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String filename=request.getParameter("filename").replace("data:image/jpg;base64,","");
            if(filename.contains("\\") || filename.contains("/") || filename.contains("!") || filename.contains("@")
                    || filename.contains("&") || filename.contains("*")){
                throw new Exception();
            }
            String PhotoStream=request.getParameter("context");
            COSHelper cosHelper=new COSHelper("rawphotobucket-1257936688");
            //BASE64解码
            Base64.Decoder decoder= Base64.getDecoder();
            File file=new File("/photos/"+filename);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(decoder.decode(PhotoStream));
            fileOutputStream.close();
            cosHelper.UploadNewFileWithLocalFilePath(filename,"/photos/"+filename);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("url","https://rawphotobucket-1257936688.cos.ap-chengdu.myqcloud.com/"+filename);
            PrintWriter printWriter=response.getWriter();
            printWriter.print(jsonObject.toJSONString());
            System.gc();
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(403);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
