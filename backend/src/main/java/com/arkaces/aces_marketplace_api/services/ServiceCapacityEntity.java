package com.arkaces.aces_marketplace_api.services;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "service_capacities")
public class ServiceCapacityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    private String unit;
    
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name="service_id", nullable = false)
    private ServiceEntity serviceEntity;
}
