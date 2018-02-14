package com.arkaces.aces_marketplace_api.service_category;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "service_categories")
public class ServiceCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private Integer position;
}
