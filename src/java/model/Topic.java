/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.*;

/**
 *
 * @author sondo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private Long topic_id;
    private String name;
    private String description;
}