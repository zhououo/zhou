package com.ctgu.jat.dao;

import com.ctgu.jat.vo.User;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static String DB_URL = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";
//    public static String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_USER = "root";
    public static String DB_PASSWORD = "root";

    private Connection connection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;

    public UserDao() {
        try{
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            if(connection == null){
                throw new SQLException("数据库连接创建失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void closeDB(){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }



    public int addUser(User user) throws SQLException {

        String addSql = "insert into t_user(username,password,realname,age) values(?,?,?,?)" ;

        pstmt = connection.prepareStatement(addSql);
        pstmt.setString(1,user.getUserName());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getRealName());
        pstmt.setInt(4,user.getAge());


        return pstmt.executeUpdate();
    }

    public int modifyUser(User user) throws SQLException{
        String addSql = "update t_user set username=?, password=?, realname=?, age=? where id=?" ;

        pstmt = connection.prepareStatement(addSql);
        pstmt.setString(1,user.getUserName());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getRealName());
        pstmt.setInt(4,user.getAge());
        pstmt.setInt(5,user.getId());


        return pstmt.executeUpdate();
    }

    public int deleteUser(int id) throws SQLException{
        String deleteSql = "delete from t_user where id=?" ;

        pstmt = connection.prepareStatement(deleteSql);
        pstmt.setInt(1,id);
        return  pstmt.executeUpdate();
    }

    public List<User> findAllUsers() throws SQLException{
        List<User> users = new ArrayList<>();
        String addSql = "select * from t_user" ;

        pstmt = connection.prepareStatement(addSql);
        resultSet = pstmt.executeQuery();


        while (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setUserName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setRealName(resultSet.getNString(4));
            user.setAge(resultSet.getInt(5));

            users.add(user);
        }


        return users;
    }


    public int getUserCount() throws SQLException{
        String addSql = "select count(*) from t_user" ;
        int  count = 0;

        pstmt = connection.prepareStatement(addSql);
        resultSet = pstmt.executeQuery();
        if(resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

    public User getUserByName(String userName) throws SQLException{
        User user = null;
        String addSql = "select * from t_user where username = ?" ;

        pstmt = connection.prepareStatement(addSql);
        pstmt.setString(1,userName);
        resultSet = pstmt.executeQuery();


        if (resultSet.next()) {
            user.setId(resultSet.getInt(1));
            user.setUserName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setRealName(resultSet.getNString(4));
            user.setAge(resultSet.getInt(5));

        }
        return user;
    }

    public User getUserById(int id) throws SQLException{
        User user = null;
        String addSql = "select * from t_user where id = ?" ;

        pstmt = connection.prepareStatement(addSql);
        pstmt.setInt(1,id);
        resultSet = pstmt.executeQuery();


        if (resultSet.next()) {
            user.setId(resultSet.getInt(1));
            user.setUserName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setRealName(resultSet.getNString(4));
            user.setAge(resultSet.getInt(5));

        }
        return user;
    }

    public int deleteAllUsers() throws SQLException{
        String deleteSql = "delete from t_user" ;

        pstmt = connection.prepareStatement(deleteSql);
        return  pstmt.executeUpdate();
    }
//    public static void main(String[] args) {
//        Connection connection = null;
//        PreparedStatement pstmt = null;
//        ResultSet resultSet = null;
//
//        try {
//            Class.forName(DB_DRIVER);
//            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
//
//            if(connection!=null){
//                System.out.println("数据库连接成功！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
