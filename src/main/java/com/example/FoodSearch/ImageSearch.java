package com.example.FoodSearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.tiia.v20190529.models.DetectLabelRequest;
import com.tencentcloudapi.tiia.v20190529.models.DetectLabelResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

/*
功能：实现图像识别搜索功能。使用时，先将图像文件存储到存储桶上，之后把图像文件的URL传入API。
日期：2019/10/12
作者：吕志伟
 */

@WebServlet(urlPatterns={"/ImageSearch"})
public class ImageSearch extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //日常处理
            String imageUrl=request.getParameter("url");
            Credential cred = new Credential("AKIDYDrPDCDF91nQErmCzgKaydLtAOk0PfCd", "c3LRNVWQDoKo8yFPPHLFyn1LV5axBIEj");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tiia.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            TiiaClient client = new TiiaClient(cred, "ap-guangzhou", clientProfile);
            String params = "{\"ImageUrl\":\"" +imageUrl+"\"}";
            DetectLabelRequest req = DetectLabelRequest.fromJsonString(params, DetectLabelRequest.class);
            DetectLabelResponse resp = client.DetectLabel(req);
            JSONObject TencentResponseJSON= JSONObject.parseObject(DetectLabelRequest.toJsonString(resp));
            if(TencentResponseJSON.getJSONArray("Labels").size()==0){
                //未查找到有效信息，直接抛出错误
                throw new Exception();
            }
            response.setContentType("Application/JSON;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            JSONArray JS=TencentResponseJSON.getJSONArray("Labels");
            JSONObject Result=new JSONObject();
            Result.put("keyWords", returnFoodName(JS));
            Result.put("Raw", JS);
            printWriter.print(Result.toJSONString());
            System.gc();
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(403);
        }
    }

    //过滤结果标签，查找符合要求的条目
    protected static JSONArray returnFoodName(JSONArray Array) {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.init();
        databaseHelper.ChooseDatabase("foods");
        JSONArray Arrays=new JSONArray();
        for (Object obj : Array) {
            JSONObject js = (JSONObject) obj;
            ResultSet resultSet = databaseHelper
                    .ExecuteQuery("select * from foods_detail where name = \"" + js.getString("Name") + "\";");
            try {
                if (resultSet.next()) {
                    Arrays.add(js);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        databaseHelper.exit();
        return Arrays;
    }



    public static void main(String[] args) {
        try{
            Credential cred = new Credential("AKIDYDrPDCDF91nQErmCzgKaydLtAOk0PfCd", "c3LRNVWQDoKo8yFPPHLFyn1LV5axBIEj");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tiia.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            TiiaClient client = new TiiaClient(cred, "ap-guangzhou", clientProfile);
            String params = "{\"ImageUrl\":\"https://i.zgjm.org/img/new/97895.jpg\"}";
            DetectLabelRequest req = DetectLabelRequest.fromJsonString(params, DetectLabelRequest.class);
            DetectLabelResponse resp = client.DetectLabel(req);
            System.out.println(DetectLabelRequest.toJsonString(resp));
            JSONObject TencentResponseJSON= JSONObject.parseObject(DetectLabelRequest.toJsonString(resp));
            JSONArray JS=TencentResponseJSON.getJSONArray("Labels");
            JSONObject Result=new JSONObject();
            Result.put("keyWords",returnFoodName(JS));
            System.out.println(Result.toJSONString());
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

    }

}
