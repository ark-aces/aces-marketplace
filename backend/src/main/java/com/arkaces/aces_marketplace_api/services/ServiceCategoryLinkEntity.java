package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.service_category.ServiceCategoryEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "service_category_links")
public class ServiceCategoryLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @ManyToOne
    @JoinColumn(name="service_pid", nullable = false)
    private ServiceEntity serviceEntity;
    
    @ManyToOne
    @JoinColumn(name="service_category_pid", nullable = false)
    private ServiceCategoryEntity serviceCategoryEntity;

}
