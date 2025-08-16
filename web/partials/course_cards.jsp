<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.*" %>

<%
List<Course> pageCourses = (List<Course>) request.getAttribute("pageCourses");
Map<Long, Topic> courseTopicsMap = (Map<Long, Topic>) request.getAttribute("courseTopicsMap");
if (pageCourses != null) {
    for (Course course : pageCourses) {
%>
<div class="col-lg-4">
    <div class="properties properties2 mb-30">
        <div class="properties__card">
            <div class="properties__img overlay1">
                <a href="#"><img src="<%= course.getThumbnail_url() != null ? course.getThumbnail_url() : "assets/img/gallery/featured1.png" %>" alt="<%= course.getTitle() %>"></a>
            </div>
            <div class="properties__caption">
                <h3><a href="#"><%= course.getTitle() %></a></h3>
                <p><%= course.getDescription() != null && course.getDescription().length() > 100 ? course.getDescription().substring(0, 100) + "..." : course.getDescription() %></p>
                <%
                    if (courseTopicsMap != null && courseTopicsMap.containsKey(course.getCourse_id())) {
                        Topic courseTopic = courseTopicsMap.get(course.getCourse_id());
                        if (courseTopic != null) {
                %>
                <div class="course-topic">
                    <span class="badge">
                        <i class="fas fa-tag"></i><%= courseTopic.getName() %>
                    </span>
                </div>
                <%
                        }
                    }
                %>
                <div class="properties__footer d-flex justify-content-between align-items-center">
                    <div class="restaurant-name">
                        <div class="rating">
                            <%
                                double rating = course.getAverageRating();
                                int fullStars = (int) rating;
                                boolean hasHalfStar = rating % 1 >= 0.5;
                            %>
                            <% for (int i = 0; i < fullStars; i++) { %>
                                <i class="fas fa-star"></i>
                            <% } %>
                            <% if (hasHalfStar) { %>
                                <i class="fas fa-star-half"></i>
                            <% } %>
                            <% for (int i = fullStars + (hasHalfStar ? 1 : 0); i < 5; i++) { %>
                                <i class="far fa-star"></i>
                            <% } %>
                        </div>
                        <p><span>(<%= String.format("%.1f", rating) %>)</span> rating</p>
                    </div>
                    <div class="price">
                        <span>$<%= course.getPrice() %></span>
                    </div>
                </div>
                <a href="#" class="border-btn border-btn2">Find out more</a>
            </div>
        </div>
    </div>
 </div>
<%
    }
}
%>


