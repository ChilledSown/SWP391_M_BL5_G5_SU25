package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Slider;

public class SliderDAO extends DBContext {

    private boolean isValidImageUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return true; 
        }
        return url.matches("^(https?://.*|(/[a-zA-Z0-9_/\\-\\.]+\\.(jpg|jpeg|png|gif)))$");
    }

    public List<Slider> getAllSlider() {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM Slider ORDER BY Slider_Id";

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
            System.out.println("Error getting all sliders: " + e.getMessage());
        }

        return sliders;
    }

    public Slider getSliderById(Long sliderId) {
        String sql = "SELECT * FROM Slider WHERE Slider_Id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, sliderId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Slider slider = new Slider();
                    slider.setSlider_id(rs.getLong("Slider_Id"));
                    slider.setTitle(rs.getString("Title"));
                    slider.setImage_url(rs.getString("Image_Url"));
                    slider.setCreated_at(rs.getDate("Created_At"));
                    slider.setUpdated_at(rs.getDate("Updated_At"));
                    return slider;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting slider by ID: " + e.getMessage());
        }

        return null;
    }

    public Slider getSliderByTitle(String title) {
        String sql = "SELECT * FROM Slider WHERE Title = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Slider slider = new Slider();
                    slider.setSlider_id(rs.getLong("Slider_Id"));
                    slider.setTitle(rs.getString("Title"));
                    slider.setImage_url(rs.getString("Image_Url"));
                    slider.setCreated_at(rs.getDate("Created_At"));
                    slider.setUpdated_at(rs.getDate("Updated_At"));
                    return slider;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting slider by title: " + e.getMessage());
        }
        return null;
    }

    public List<Slider> getSlidersWithPagination(String query, int page, int pageSize) {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM Slider WHERE Title LIKE ? ORDER BY Slider_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Slider slider = new Slider();
                    slider.setSlider_id(rs.getLong("Slider_Id"));
                    slider.setTitle(rs.getString("Title"));
                    slider.setImage_url(rs.getString("Image_Url"));
                    slider.setCreated_at(rs.getDate("Created_At"));
                    slider.setUpdated_at(rs.getDate("Updated_At"));
                    sliders.add(slider);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting sliders with pagination: " + e.getMessage());
        }
        return sliders;
    }

    public int getTotalSliders(String query) {
        String sql = "SELECT COUNT(*) FROM Slider WHERE Title LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting total sliders: " + e.getMessage());
        }
        return 0;
    }

    public List<Slider> searchSlidersByTitle(String query, int page, int pageSize) {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM Slider WHERE Title LIKE ? ORDER BY Slider_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Slider slider = new Slider();
                    slider.setSlider_id(rs.getLong("Slider_Id"));
                    slider.setTitle(rs.getString("Title"));
                    slider.setImage_url(rs.getString("Image_Url"));
                    slider.setCreated_at(rs.getDate("Created_At"));
                    slider.setUpdated_at(rs.getDate("Updated_At"));
                    sliders.add(slider);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching sliders by title: " + e.getMessage());
        }
        return sliders;
    }

    public int getTotalSlidersByTitle(String query) {
        String sql = "SELECT COUNT(*) FROM Slider WHERE Title LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting total sliders by title: " + e.getMessage());
        }
        return 0;
    }
    
    public List<Slider> getSlidersByPage(int page, int pageSize) {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM Slider ORDER BY Slider_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Slider slider = new Slider();
                    slider.setSlider_id(rs.getLong("Slider_Id"));
                    slider.setTitle(rs.getString("Title"));
                    slider.setImage_url(rs.getString("Image_Url"));
                    slider.setCreated_at(rs.getDate("Created_At"));
                    slider.setUpdated_at(rs.getDate("Updated_At"));
                    sliders.add(slider);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting sliders by page: " + e.getMessage());
        }
        return sliders;
    }
    
    public int getTotalSliders() {
        String sql = "SELECT COUNT(*) FROM Slider";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting total sliders: " + e.getMessage());
        }
        return 0;
    }
    
    public boolean updateSlider(Slider slider) {
        if (!isValidImageUrl(slider.getImage_url())) {
            System.out.println("Invalid image URL: " + slider.getImage_url());
            return false;
        }
        String sql = "UPDATE Slider SET Title = ?, Image_Url = ?, Updated_At = GETDATE() WHERE Slider_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, slider.getTitle());
            ps.setString(2, slider.getImage_url());
            ps.setLong(3, slider.getSlider_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating slider: " + e.getMessage());
            return false;
        }
    }

    public boolean insertSlider(Slider slider) {
        if (!isValidImageUrl(slider.getImage_url())) {
            System.out.println("Invalid image URL: " + slider.getImage_url());
            return false;
        }
        String sql = "INSERT INTO Slider (Title, Image_Url, Created_At, Updated_At) VALUES (?, ?, GETDATE(), GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, slider.getTitle());
            ps.setString(2, slider.getImage_url());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting slider: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSlider(long sliderId) {
        String sql = "DELETE FROM Slider WHERE Slider_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, sliderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting slider ID " + sliderId + ": " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        SliderDAO sliderDAO = new SliderDAO();
        List<Slider> sliders = sliderDAO.getAllSlider();
        for (Slider slider : sliders) {
            System.out.println(slider);
        }
    }
}