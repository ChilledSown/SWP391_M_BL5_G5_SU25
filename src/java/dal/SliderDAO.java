/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Slider;

/**
 *
 * @author sondo
 */
public class SliderDAO extends DBContext{
    
    public List<Slider> getAllSlider() {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM slider ORDER BY created_at DESC";
        
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSlider_id(rs.getLong("Slider_Id"));
                slider.setTitle(rs.getString("Title"));
                slider.setImage_url(rs.getString("Image_Url"));
                slider.setCreated_at(rs.getDate("Created_At"));
                slider.setUpdated_at(rs.getDate("Updated_At"));
                sliders.add(slider);
            }
        } catch (SQLException e) {
            System.err.println("Error getting sliders: " + e.getMessage());
        }
        
        return sliders;
    }
    
    public static void main(String[] args) {
        SliderDAO sdao = new SliderDAO();
        List<Slider> slider = sdao.getAllSlider();
        System.out.println(slider);
    }
}
