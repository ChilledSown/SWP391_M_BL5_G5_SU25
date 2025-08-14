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
public class Review {
    private Long review_id;
    private Long course_id;
    private int rating;
    private String comment;
    private Date created_at;
    private Long user_id;
}
