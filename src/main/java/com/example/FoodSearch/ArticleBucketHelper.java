package com.example.FoodSearch;

import com.alibaba.fastjson.JSONObject;

public class ArticleBucketHelper {

    public static final String BucketURL="https://articlebucket-1257936688.cos.ap-chengdu.myqcloud.com";
    public static final String Pointer="info.json";

    //返回文章指针AID
    synchronized public static String GetAID(){
       return JSONObject.parseObject(HTTPHelper.GetURLResult(BucketURL+"/"+Pointer)).getString("AID");
    }

    synchronized public static void AIDInc(COSHelper cosHelper){
        int AID= Integer.parseInt(GetAID());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("AID",AID+1);
       cosHelper.DeleteFile("info.json");
       cosHelper.UploadNewFileWithString("info.json",jsonObject.toJSONString());
    }

    synchronized public static void AIDDec(COSHelper cosHelper){
        int AID= Integer.parseInt(GetAID());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("AID",AID-1);
        cosHelper.DeleteFile("info.json");
        cosHelper.UploadNewFileWithString("info.json",jsonObject.toJSONString());
    }

}
