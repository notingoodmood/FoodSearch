package com.example.FoodSearch;

import java.sql.*;


public class DatabaseHelper {

    private static final String address="jdbc:mysql://cdb-02uep8xs.cd.tencentcdb.com:10113?characterEncoding=utf8&useSSL=true";
    private static final String client="Test";
    private static final String code="Test2021!@";

    private Statement statement;
    private Connection connection;

    public static void main(String[] args) {
        System.out.println("test");
    }

    public void init(){
        if(this.statement==null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager.getConnection(address, client, code);
                statement = connection.createStatement();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("没有找到驱动！！！\n");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("没有找到数据库！！！\n");
            }
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

    synchronized public ResultSet ExecuteQuery(String SQL){
        try{
            if(this.statement==null){
                init();
            }
            return statement.executeQuery(SQL);
        }catch (SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }

    synchronized public boolean ExecuteSQL(String SQL){
        try{
            if(this.statement==null){
                init();
            }
            return statement.execute(SQL);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean ChooseDatabase(String name){
        try {
            if(this.statement==null){
                init();
            }
            statement.executeQuery("use " + name+";");
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public void exit(){
        try {
            statement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
