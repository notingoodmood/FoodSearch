import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/QueryKeyWord")
public class QueryKeyWord extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String keyword=request.getParameter("keyword");
            DatabaseHelper databaseHelper=new DatabaseHelper();
            databaseHelper.init();
            databaseHelper.ChooseDatabase("foods");
            ResultSet resultSet=databaseHelper.ExecuteQuery("select * from foods_detail where Name like \"%"+keyword+"%\" ;");
            JSONArray Array=new JSONArray();
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("Name",resultSet.getString("Name"));
                jsonObject.put("Col",resultSet.getString("Col"));
                Array.add(jsonObject);
            }
            JSONObject js=new JSONObject();
            js.put("Result",Array);
            response.setContentType("Application/JSON;charset=UTF-8");
            PrintWriter pr=response.getWriter();
            pr.write(js.toJSONString());
            databaseHelper.exit();
            pr.close();
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(403);
        }
    }

}
