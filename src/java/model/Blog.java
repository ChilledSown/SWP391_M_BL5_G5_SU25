
package model;

import java.sql.Timestamp;


public class Blog {
    private long blogId;         
    private String title;        
    private String content;      
    private String thumbnailUrl;
    private Timestamp createdAt; 
    private Timestamp updatedAt; 
    private String createdBy; 

    public Blog() {
    }

    public Blog(long blogId, String title, String content, String thumbnailUrl, Timestamp createdAt, Timestamp updatedAt, String createdBy) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
    }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Blog{" + "blogId=" + blogId + ", title=" + title + ", content=" + content + ", thumbnailUrl=" + thumbnailUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + '}';
    }

    
    
    
}
