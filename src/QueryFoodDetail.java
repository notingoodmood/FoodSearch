import Entity.Data;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/QueryFoodDetail")
public class QueryFoodDetail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String name=request.getParameter("name");
            DatabaseHelper databaseHelper=new DatabaseHelper();
            databaseHelper.init();
            databaseHelper.ChooseDatabase("foods");
            ResultSet resultSet=databaseHelper.ExecuteQuery("select * from foods_detail where name=\""+name+"\";");
            JSONObject jsonObject=new JSONObject();
            if(resultSet.next()){
                jsonObject.put("Name",resultSet.getString("Name"));
                jsonObject.put("Class",resultSet.getString("Class"));
                jsonObject.put("Col",resultSet.getString("Col"));
                jsonObject.put("Protein",resultSet.getString("Protein"));
                jsonObject.put("Fat",resultSet.getString("Fat"));
                jsonObject.put("Carbon",resultSet.getString("Carbon"));
                jsonObject.put("VA",resultSet.getString("VA"));
                jsonObject.put("VB1",resultSet.getString("VB1"));
                jsonObject.put("VB2",resultSet.getString("VB2"));
                jsonObject.put("VB6",resultSet.getString("VB6"));
                jsonObject.put("VB12",resultSet.getString("VB12"));
                jsonObject.put("VC",resultSet.getString("VC"));
                jsonObject.put("VD",resultSet.getString("VD"));
                jsonObject.put("VE",resultSet.getString("VE"));
                jsonObject.put("Na",resultSet.getString("Na"));
                jsonObject.put("K",resultSet.getString("K"));
                jsonObject.put("Ca",resultSet.getString("Ca"));
            }
            JSONObject Result=new JSONObject();
            Result.put("Result",jsonObject);
            response.setContentType("Application/JSON;charset=UTF-8");
            PrintWriter pw=response.getWriter();
            pw.write(Result.toJSONString());
            databaseHelper.exit();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(403);
        }
    }
}
