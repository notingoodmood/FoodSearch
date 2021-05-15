import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPHelper {

    public static final String LocalPath="/usr/local";

    public static String GetURLResult(String URL){
        try {
            java.net.URL url = new URL(URL);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestProperty("accept","*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            //获取URLConnection对象对应的输入流
            InputStream is = httpURLConnection.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            String Line="";
            while ((Line=br.readLine())!=null){
                str+=Line;
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            httpURLConnection.disconnect();
            return str;
     }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

   synchronized public static void DownloadPhoto(String PhotoPath) {
       HttpURLConnection conn = null;
       InputStream inputStream = null;
       BufferedInputStream bis = null;
       FileOutputStream out = null;
       try {
           File file0 = new File(LocalPath),file1=new File(LocalPath+"/test.jpg");
           if (!file0.isDirectory() && !file0.exists()) {
               file0.mkdirs();
           }
           out = new FileOutputStream(file0 + "/"+"test.jpg");
           // 建立链接
           URL httpUrl = new URL(PhotoPath);
           conn = (HttpURLConnection) httpUrl.openConnection();
           //以Post方式提交表单，默认get方式
           conn.setRequestMethod("GET");
           conn.setDoInput(true);
           conn.setDoOutput(true);
           // post方式不能使用缓存
           conn.setUseCaches(false);
           //连接指定的资源
           conn.connect();
           //获取网络输入流
           inputStream = conn.getInputStream();
           bis = new BufferedInputStream(inputStream);
           byte b[] = new byte[1024];
           int len = 0;
           while ((len = bis.read(b)) != -1) {
               out.write(b, 0, len);
           }
           System.out.println("下载完成...");
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           try {
               if (out != null) {
                   out.close();
               }
               if (bis != null) {
                   bis.close();
               }
               if (inputStream != null) {
                   inputStream.close();
               }
           } catch (Exception e2) {
               e2.printStackTrace();
           }
       }
   }

   public static void main(String[] args){
        DownloadPhoto("https://s1.hdslb.com/bfs/static/jinkela/home/asserts/ic_launcher.png");
   }

   }

