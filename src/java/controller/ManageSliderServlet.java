package controller;

import dal.SliderDAO;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import model.Slider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name = "ManageSliderServlet", urlPatterns = {"/manageslider"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 5,      // 5MB
    maxRequestSize = 1024 * 1024 * 50   // 50MB
)
public class ManageSliderServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10; // Number of sliders per page
    private static final String UPLOAD_DIR = "uploads/sliders"; // Directory for uploaded files
    private static final String DEFAULT_IMAGE_URL = "/assets/img/default-slider.png"; // Default image URL

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDAO sliderDAO = new SliderDAO();
        
        // Get action parameter
        String action = request.getParameter("action");
        
        if (action != null && action.equals("edit")) {
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
        } else if (action != null && action.equals("delete")) {
            // Handle delete action
            String sliderIdStr = request.getParameter("sliderId");
            if (sliderIdStr != null) {
                Long sliderId = Long.parseLong(sliderIdStr);
                if (sliderDAO.deleteSlider(sliderId)) {
                    request.setAttribute("message", "Slider deleted successfully.");
                } else {
                    request.setAttribute("error", "Failed to delete slider.");
                }
                // Fetch updated sliders list
                String query = request.getParameter("query");
                String pageStr = request.getParameter("page");
                int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
                List<Slider> sliders;
                int totalSliders;
                if (query != null && !query.trim().isEmpty()) {
                    sliders = sliderDAO.searchSlidersByTitle(query, page, PAGE_SIZE);
                    totalSliders = sliderDAO.getTotalSlidersByTitle(query);
                } else {
                    sliders = sliderDAO.getSlidersByPage(page, PAGE_SIZE);
                    totalSliders = sliderDAO.getTotalSliders();
                }
                int totalPages = (int) Math.ceil((double) totalSliders / PAGE_SIZE);
                request.setAttribute("sliders", sliders);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("searchQuery", query != null ? query : "");
                request.getRequestDispatcher("manageslider.jsp").forward(request, response);
                return;
            }
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
            String inputType = request.getParameter("inputType");
            String imageUrl = null;

            // Validate title
            if (title == null || title.trim().isEmpty()) {
                request.setAttribute("error", "Title is required.");
                request.getRequestDispatcher("createslider.jsp").forward(request, response);
                return;
            }

            // Check for duplicate title
            if (sliderDAO.getSliderByTitle(title) != null) {
                request.setAttribute("error", "Slider with this title already exists.");
                request.getRequestDispatcher("createslider.jsp").forward(request, response);
                return;
            }

            // Process image based on inputType
            if ("file".equals(inputType)) {
                Part filePart = request.getPart("imageFile");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    // Validate file type
                    String contentType = filePart.getContentType();
                    if (!contentType.startsWith("image/")) {
                        request.setAttribute("error", "Only image files are allowed.");
                        request.getRequestDispatcher("createslider.jsp").forward(request, response);
                        return;
                    }
                    // Save file
                    String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;
                    java.io.File uploadDir = new java.io.File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String filePath = uploadPath + java.io.File.separator + fileName;
                    filePart.write(filePath);
                    imageUrl = "/" + UPLOAD_DIR + "/" + fileName;
                } else {
                    imageUrl = DEFAULT_IMAGE_URL; // Use default if no file provided
                }
            } else {
                imageUrl = request.getParameter("imageUrl");
                if (imageUrl == null || imageUrl.trim().isEmpty()) {
                    imageUrl = DEFAULT_IMAGE_URL; // Use default if no URL provided
                }
            }

            Slider slider = new Slider();
            slider.setTitle(title);
            slider.setImage_url(imageUrl);

            if (sliderDAO.insertSlider(slider)) {
                request.setAttribute("message", "Slider created successfully.");
                request.getRequestDispatcher("createslider.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to create slider.");
                request.getRequestDispatcher("createslider.jsp").forward(request, response);
            }
        } else if ("update".equals(action)) {
            String sliderIdStr = request.getParameter("sliderId");
            String title = request.getParameter("title");
            String inputType = request.getParameter("inputType");
            String imageUrl = null;

            // Validate input
            if (sliderIdStr == null || title == null || title.trim().isEmpty()) {
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

            // Process image based on inputType
            if ("file".equals(inputType)) {
                Part filePart = request.getPart("imageFile");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    // Validate file type
                    String contentType = filePart.getContentType();
                    if (!contentType.startsWith("image/")) {
                        request.setAttribute("error", "Only image files are allowed.");
                        request.getRequestDispatcher("editslider.jsp").forward(request, response);
                        return;
                    }
                    // Save file
                    String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;
                    java.io.File uploadDir = new java.io.File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String filePath = uploadPath + java.io.File.separator + fileName;
                    filePart.write(filePath);
                    imageUrl = "/" + UPLOAD_DIR + "/" + fileName;
                } else {
                    imageUrl = existingSlider.getImage_url(); // Keep existing URL if no new file
                }
            } else {
                imageUrl = request.getParameter("imageUrl");
                if (imageUrl == null || imageUrl.trim().isEmpty()) {
                    imageUrl = existingSlider.getImage_url(); // Keep existing URL if no new URL
                }
            }

            Slider slider = new Slider();
            slider.setSlider_id(sliderId);
            slider.setTitle(title);
            slider.setImage_url(imageUrl);

            if (sliderDAO.updateSlider(slider)) {
                request.setAttribute("message", "Slider updated successfully.");
                request.setAttribute("slider", slider); // Retain slider data for form
                request.getRequestDispatcher("editslider.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to update slider.");
                request.setAttribute("slider", slider); // Retain slider data for form
                request.getRequestDispatcher("editslider.jsp").forward(request, response);
            }
        }
    }
}