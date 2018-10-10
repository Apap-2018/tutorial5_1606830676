package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.CarModel;

public interface CarService {
	void addCar(CarModel car);
	public void deleteById(Long id);
	void updateCar(long id,CarModel car);
	CarModel getCar(Long id);
	void deleteCar(CarModel car);
}