/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import lombok.*;

/**
 *
 * @author sondo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    private Long course_id;
    private String title;
    private String description;
    private int price;
    private String thumbnail_url;
    private Date created_at;
    private Date updated_at;
    private Long topic_id;
    private Double averageRating;
}
