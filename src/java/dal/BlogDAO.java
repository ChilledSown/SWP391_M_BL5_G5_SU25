/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import java.sql.*;
import java.time.LocalDate;

/**
 *
 * @author sondo
 */
public class BlogDAO extends DBContext {
    public List<Blog> getStudentsByClassID(String classID) {
        List<Blog> listBog = new ArrayList<>();
        String sql = "SELECT * FROM Student WHERE classID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, classID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listBog.add(new Blog(rs.getLong("blogId"),rs.getString("title"),
                        rs.getString("content"),rs.getString("thumbnailUrl"), LocalDate.MAX, LocalDate.MAX,rs.getString("createdBy")                   
                ));
            }
            
            System.out.println("Fetched " + listBog.size() + " students for classID: " + classID); 
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BlogDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.out.println("SQL Error: " + ex.getMessage()); // Debug
        }
        return listBog;
    }
    public static void main(String[] args) {
        BlogDAO blog =new BlogDAO();
        
    }

}
