package controller;

import dal.SliderDAO;
import java.io.IOException;
import java.util.List;
import model.Slider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageSliderServlet", urlPatterns = {"/manageslider"})
public class ManageSliderServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10; // Number of sliders per page

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDAO sliderDAO = new SliderDAO();
        
        // Get action parameter
        String action = request.getParameter("action");
        
        if (action != null && action.equals("details")) {
            // Handle details page
            String sliderIdStr = request.getParameter("sliderId");
            if (sliderIdStr != null) {
                Long sliderId = Long.parseLong(sliderIdStr);
                Slider slider = sliderDAO.getSliderById(sliderId);
                request.setAttribute("slider", slider);
                request.getRequestDispatcher("sliderdetails.jsp").forward(request, response);
                return;
            }
        } else if (action != null && action.equals("edit")) {
            // Handle edit page
            String sliderIdStr = request.getParameter("sliderId");
            if (sliderIdStr != null) {
                Long sliderId = Long.parseLong(sliderIdStr);
                Slider slider = sliderDAO.getSliderById(sliderId);
                request.setAttribute("slider", slider);
                request.getRequestDispatcher("editslider.jsp").forward(request, response);
                return;
            }
        } else if (action != null && action.equals("create")) {
            // Handle create page
            request.getRequestDispatcher("createslider.jsp").forward(request, response);
            return;
        }
        
        // Handle list view with search and pagination
        String query = request.getParameter("query");
        String pageStr = request.getParameter("page");
        int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
        
        // Fetch sliders and total count
        List<Slider> sliders;
        int totalSliders;
        if (query != null && !query.trim().isEmpty()) {
            sliders = sliderDAO.searchSlidersByTitle(query, page, PAGE_SIZE);
            totalSliders = sliderDAO.getTotalSlidersByTitle(query);
        } else {
            sliders = sliderDAO.getSlidersByPage(page, PAGE_SIZE);
            totalSliders = sliderDAO.getTotalSliders();
        }
        
        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalSliders / PAGE_SIZE);
        
        // Set attributes for JSP
        request.setAttribute("sliders", sliders);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", query != null ? query : "");
        
        // Forward to JSP
        request.getRequestDispatcher("manageslider.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDAO sliderDAO = new SliderDAO();
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String title = request.getParameter("title");
            String imageUrl = request.getParameter("imageUrl");

            // Validate input
            if (title == null || title.trim().isEmpty() || imageUrl == null || imageUrl.trim().isEmpty()) {
                request.setAttribute("error", "Title and Image URL are required.");
                request.getRequestDispatcher("createslider.jsp").forward(request, response);
                return;
            }

            // Check for duplicate title
            if (sliderDAO.getSliderByTitle(title) != null) {
                request.setAttribute("error", "Slider with this title already exists.");
                request.getRequestDispatcher("createslider.jsp").forward(request, response);
                return;
            }

            Slider slider = new Slider();
            slider.setTitle(title);
            slider.setImage_url(imageUrl);

            if (sliderDAO.insertSlider(slider)) {
                request.setAttribute("message", "Slider created successfully.");
            } else {
                request.setAttribute("error", "Failed to create slider.");
                request.getRequestDispatcher("createslider.jsp").forward(request, response);
                return;
            }
        } else if ("update".equals(action)) {
            String sliderIdStr = request.getParameter("sliderId");
            String title = request.getParameter("title");
            String imageUrl = request.getParameter("imageUrl");

            // Validate input
            if (sliderIdStr == null || title == null || title.trim().isEmpty() || imageUrl == null || imageUrl.trim().isEmpty()) {
                request.setAttribute("error", "Invalid input data.");
                request.getRequestDispatcher("editslider.jsp").forward(request, response);
                return;
            }

            Long sliderId = Long.parseLong(sliderIdStr);
            Slider existingSlider = sliderDAO.getSliderById(sliderId);
            if (existingSlider == null) {
                request.setAttribute("error", "Slider not found.");
                request.getRequestDispatcher("editslider.jsp").forward(request, response);
                return;
            }

            // Check for duplicate title (excluding current slider)
            Slider duplicateSlider = sliderDAO.getSliderByTitle(title);
            if (duplicateSlider != null && duplicateSlider.getSlider_id() != sliderId) {
                request.setAttribute("error", "Slider with this title already exists.");
                request.getRequestDispatcher("editslider.jsp").forward(request, response);
                return;
            }

            Slider slider = new Slider();
            slider.setSlider_id(sliderId);
            slider.setTitle(title);
            slider.setImage_url(imageUrl);

            if (sliderDAO.updateSlider(slider)) {
                request.setAttribute("message", "Slider updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update slider.");
                request.getRequestDispatcher("editslider.jsp").forward(request, response);
                return;
            }
        }

        // Redirect to list view after create/update
        response.sendRedirect("manageslider");
    }
}