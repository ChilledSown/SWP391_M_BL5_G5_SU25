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
import model.Cart;
import model.CartItem;

/**
 *
 * @author sondo
 */
public class CartDAO extends DBContext {
    
    public Cart getCartByUserId(long userId) {
        String sql = "SELECT * FROM Cart WHERE User_Id = ? AND Status = 'pending'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setCart_id(rs.getLong("Cart_Id"));
                cart.setUser_id(rs.getLong("User_Id"));
                cart.setStatus(rs.getString("Status"));
                cart.setCreated_at(rs.getTimestamp("Created_At"));
                cart.setUpdated_at(rs.getTimestamp("Updated_At"));
                return cart;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Cart findOrCreateActiveCart(long userId) {
        Cart existing = getCartByUserId(userId);
        if (existing != null) {
            return existing;
        }
        createCart(userId);
        return getCartByUserId(userId);
    }

    public void createCart(long userId) {
        String sql = "INSERT INTO Cart (User_Id, Status, Created_At, Updated_At) VALUES (?, 'pending', GETDATE(), GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addCourseToCart(long cartId, long courseId, double price) {
        String sql = "INSERT INTO CartItem (Cart_Id, Course_Id, Price, Created_At, Updated_At) VALUES (?, ?, ?, GETDATE(), GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            ps.setLong(2, courseId);
            ps.setDouble(3, price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<CartItem> getCartItemsByCartId(long cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM CartItem WHERE Cart_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCart_item_id(rs.getLong("Cart_Item_Id"));
                cartItem.setCart_id(rs.getLong("Cart_Id"));
                cartItem.setCourse_id(rs.getLong("Course_Id"));
                cartItem.setPrice(rs.getDouble("Price"));
                cartItem.setCreated_at(rs.getTimestamp("Created_At"));
                cartItem.setUpdated_at(rs.getTimestamp("Updated_At"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    
    public boolean isCourseInCart(long cartId, long courseId) {
        String sql = "SELECT COUNT(*) as Count FROM CartItem WHERE Cart_Id = ? AND Course_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            ps.setLong(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void removeCourseFromCart(long cartItemId) {
        String sql = "DELETE FROM CartItem WHERE Cart_Item_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cartItemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public double getCartTotal(long cartId) {
        String sql = "SELECT SUM(Price) as Total FROM CartItem WHERE Cart_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
