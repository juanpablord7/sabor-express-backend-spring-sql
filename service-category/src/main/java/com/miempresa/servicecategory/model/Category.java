package com.miempresa.servicecategory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name can't be empty")
    @Size(min = 2, message = "The name must have at least 2 letters")
    @Column(nullable = false, name = "name")
    private String name;

    @NotBlank(message = "The name can't be empty")
    @Size(min = 4, message = "The imagen must have at least 4 letters")
    @Column(nullable = false, name = "image")
    private String image;
}