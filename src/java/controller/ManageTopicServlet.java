package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.TopicDAO;
import dal.CourseDAO;
import dal.LessonDAO;
import dal.QuizDAO;
import model.Topic;
import model.Course;
import model.Lesson;
import model.Quiz;
import java.util.List;

@WebServlet(name="ManageTopicServlet", urlPatterns={"/managetopic"})
public class ManageTopicServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TopicDAO tdao = new TopicDAO();

        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            try {
                long topicId = Long.parseLong(request.getParameter("topicId"));
                Topic topic = tdao.getTopicById(topicId);
                if (topic == null) {
                    request.setAttribute("error", "Topic not found");
                    request.getRequestDispatcher("managetopic.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("topic", topic);
                request.getRequestDispatcher("updatetopic.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
                request.getRequestDispatcher("managetopic.jsp").forward(request, response);
            }
            return;
        } else if ("view".equals(action)) {
            try {
                long tId = Long.parseLong(request.getParameter("topicId"));
                Topic top = tdao.getTopicById(tId);
                if (top == null) {
                    request.setAttribute("error", "Topic not found");
                    request.getRequestDispatcher("managetopic.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("topic", top);
                String courseIdStr = request.getParameter("courseId");
                String lessonIdStr = request.getParameter("lessonId");
                if (lessonIdStr != null) {
                    try {
                        long lessonId = Long.parseLong(lessonIdStr);
                        QuizDAO qdao = new QuizDAO();
                        List<Quiz> quizzes = qdao.getQuizzesByLessonId(lessonId);
                        request.setAttribute("quizzes", quizzes);
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Invalid lesson ID");
                        request.getRequestDispatcher("managetopic.jsp").forward(request, response);
                        return;
                    }
                } else if (courseIdStr != null) {
                    try {
                        long courseId = Long.parseLong(courseIdStr);
                        LessonDAO ldao = new LessonDAO();
                        List<Lesson> lessons = ldao.getLessonsByCourseId(courseId);
                        request.setAttribute("lessons", lessons);
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Invalid course ID");
                        request.getRequestDispatcher("managetopic.jsp").forward(request, response);
                        return;
                    }
                } else {
                    CourseDAO cdao = new CourseDAO();
                    List<Course> courses = cdao.getCoursesByTopicId(tId);
                    request.setAttribute("courses", courses);
                }
                request.getRequestDispatcher("viewtopic.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
                request.getRequestDispatcher("managetopic.jsp").forward(request, response);
            }
            return;
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
            }
        }

        String searchQuery = request.getParameter("query");
        List<Topic> topics;
        int totalTopics;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            topics = tdao.getTopicsWithPagination(searchQuery, page, PAGE_SIZE);
            totalTopics = tdao.getTotalTopics(searchQuery);
        } else {
            topics = tdao.getTopicsWithPagination(null, page, PAGE_SIZE);
            totalTopics = tdao.getTotalTopics(null);
        }
        int totalPages = (int) Math.ceil((double) totalTopics / PAGE_SIZE);

        request.setAttribute("topics", topics);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("managetopic.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TopicDAO tdao = new TopicDAO();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                long topicId = Long.parseLong(request.getParameter("topicId"));
                Topic topic = tdao.getTopicById(topicId);
                if (topic == null) {
                    request.setAttribute("error", "Topic not found");
                } else {
                    boolean success = tdao.deleteTopic(topicId);
                    if (success) {
                        request.getSession().setAttribute("message", "Topic deleted successfully");
                    } else {
                        request.setAttribute("error", "Failed to delete topic due to database error");
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
            }
            doGet(request, response);
        } else if ("update".equals(action)) {
            String topicIdStr = request.getParameter("topicId");
            String name = request.getParameter("name");
            String thumbnailUrl = request.getParameter("thumbnailUrl");
            String description = request.getParameter("description");

            String error = null;
            try {
                long topicId = Long.parseLong(topicIdStr);
                if (name == null || name.trim().isEmpty()) {
                    error = "Topic name is required.";
                } else {
                    Topic existingTopic = tdao.getTopicByName(name);
                    if (existingTopic != null && existingTopic.getTopic_id() != topicId) {
                        error = "Topic name already exists.";
                    }
                }

                if (error != null) {
                    Topic topic = new Topic();
                    topic.setTopic_id(topicId);
                    topic.setName(name);
                    topic.setThumbnail_url(thumbnailUrl);
                    topic.setDescription(description);
                    request.setAttribute("topic", topic);
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("updatetopic.jsp").forward(request, response);
                    return;
                }

                Topic topic = new Topic();
                topic.setTopic_id(topicId);
                topic.setName(name);
                topic.setThumbnail_url(thumbnailUrl);
                topic.setDescription(description);

                boolean success = tdao.updateTopic(topic);
                request.getSession().setAttribute("message", success ? "Topic updated successfully" : "Failed to update topic");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
                request.getRequestDispatcher("managetopic.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("managetopic");
        }
    }
}