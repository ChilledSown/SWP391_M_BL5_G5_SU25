/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Topic;

public class TopicDAO extends DBContext {
    public List<Topic> getAllTopics() {
        List<Topic> list = new ArrayList<>();
        String sql = "SELECT Topic_Id, Name, Description FROM Topic";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Topic t = new Topic();
                t.setTopic_id(rs.getLong("Topic_Id"));
                t.setName(rs.getString("Name"));
                t.setDescription(rs.getString("Description"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
