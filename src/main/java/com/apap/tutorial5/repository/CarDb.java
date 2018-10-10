package com.apap.tutorial5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tutorial5.model.CarModel;

public interface CarDb extends JpaRepository<CarModel, Long> {
	CarModel findByType(String type);
}