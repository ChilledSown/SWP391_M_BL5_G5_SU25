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
import model.Topic;

/**
 *
 * @author sondo
 */
public class TopicDAO extends DBContext {
    
    // Get all topics
    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM topic ORDER BY topic_id";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all topics: " + e.getMessage());
        }
        
        return topics;
    }
    
    // Get topic by ID
    public Topic getTopicById(Long topicId) {
        String sql = "SELECT * FROM topic WHERE topic_id = ?";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, topicId);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                return topic;
            }
        } catch (SQLException e) {
            System.out.println("Error getting topic by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // Get top 8 topics (for display in courses page)
    public List<Topic> getTopTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT TOP 8 * FROM topic ORDER BY topic_id";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top topics: " + e.getMessage());
        }
        
        return topics;
    }
    
    // Get topic by course ID
    public Topic getTopicByCourseId(Long courseId) {
        String sql = "SELECT t.* FROM topic t " +
                    "INNER JOIN course c ON t.topic_id = c.topic_id " +
                    "WHERE c.course_id = ?";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, courseId);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                return topic;
            }
        } catch (SQLException e) {
            System.out.println("Error getting topic by course ID: " + e.getMessage());
        }
        
        return null;
    }
}
