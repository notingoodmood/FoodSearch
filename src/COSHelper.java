import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

import java.io.File;

@SuppressWarnings("ALL")
public class COSHelper {

    public static String APPID="100008077353";
    public static String SecretID="AKIDYDrPDCDF91nQErmCzgKaydLtAOk0PfCd";
    public static String SecretKEY="c3LRNVWQDoKo8yFPPHLFyn1LV5axBIEj";
    public static String BucketName="articlebucket-1257936688";

    private COSCredentials cosCredentials;
    private Region region;
    private ClientConfig clientConfig;
    private COSClient cosClient;


    public COSHelper(String bucketname){

        this.BucketName=bucketname;
        this.cosCredentials = new BasicCOSCredentials(SecretID, SecretKEY);
        this.region = new Region("ap-chengdu");
        this.clientConfig = new ClientConfig(this.region);
        this.cosClient = new COSClient(this.cosCredentials, this.clientConfig);

    }
    public COSHelper(){

            this.cosCredentials = new BasicCOSCredentials(SecretID, SecretKEY);
            this.region = new Region("ap-chengdu");
            this.clientConfig = new ClientConfig(this.region);
            this.cosClient = new COSClient(this.cosCredentials, this.clientConfig);

    }
    synchronized public void UploadNewFileWithLocalFilePath(String Key, String LocalPath){
        File file=new File(LocalPath);
        this.cosClient.putObject(BucketName,Key,file);
    }

    synchronized public void UploadNewFileWithString(String Key, String str){
        this.cosClient.putObject(BucketName,Key,str);
    }

    synchronized public void DeleteFile(String Key){
        this.cosClient.deleteObject(BucketName,Key);
    }

    synchronized public Boolean ReplaceArticle(String AID, JSONObject ArticleJSON, String PhotoPath){
        try{
            HTTPHelper.DownloadPhoto(PhotoPath);
            DeleteFile(AID+".json");
            DeleteFile(AID+".jpg");
            UploadNewFileWithLocalFilePath(AID+".jpg",HTTPHelper.LocalPath+"/test.jpg");
            UploadNewFileWithString(AID+".json",ArticleJSON.toJSONString());
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    synchronized public void RemoveArticleFromTail(){
        String AID=ArticleBucketHelper.GetAID();
        DeleteFile(AID+".json");
        DeleteFile(AID+".jpg");
        ArticleBucketHelper.AIDDec(this);
    }

    synchronized public boolean CreateNewArticle(JSONObject ArticleJSON, String PhotoPath){
        try{
            HTTPHelper.DownloadPhoto(PhotoPath);
            int AID= Integer.parseInt(ArticleBucketHelper.GetAID());
            ArticleBucketHelper.AIDInc(this);
            ++AID;
            this.UploadNewFileWithLocalFilePath(AID+".jpg",HTTPHelper.LocalPath+"/test.jpg");
            this.UploadNewFileWithString(AID+".json",ArticleJSON.toJSONString());
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    synchronized public void exit(){
        this.cosClient.shutdown();
        System.gc();
    }

    private class WrongPhotoPathException extends Exception {

    }
}
