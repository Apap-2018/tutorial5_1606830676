package com.apap.tutorial5.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping(value="/dealer/view", method = RequestMethod.GET)
	private String view(@RequestParam(value="dealerId") Long dealerId, Model model) {
		DealerModel archiveDealer = dealerService.getDealerDetailById(dealerId).get();
		
		List<CarModel> archiveListCar = carService.getListCardOrderByPriveAsc(dealerId);
		archiveDealer.setListCar(archiveListCar);
		
		model.addAttribute("dealer", archiveDealer);
		
		return "view-dealer";
	}
	
	@RequestMapping(value="/dealer/viewall", method = RequestMethod.GET)
	private String viewDealer(Model model) {
		List<DealerModel> temp = dealerService.getAllDealer();
		model.addAttribute("dealer", temp);
		return "viewall-dealer";
	}
	
	@RequestMapping(value="/dealer/delete/{id}", method=RequestMethod.GET)
	private String deleteDealer(@PathVariable(value = "id") Long dealerId, Model model) {
		if(dealerService.getDealerDetailById(dealerId).isPresent()) {
			DealerModel temp = dealerService.getDealerDetailById(dealerId).get();
			dealerService.deleteDealer(temp);
			return "delete-dealer";
			}
		return "error";
	}
	
	@RequestMapping(value = "/dealer/update/{id}", method = RequestMethod.GET)
	private String updateDealer(@PathVariable(value = "id") long id, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(id).get();
		model.addAttribute("dealer",dealer);
		return "update-dealer";
	}
	
	@RequestMapping(value = "/dealer/update/{id}", method = RequestMethod.POST)
	private String updateDealerSubmit(@PathVariable (value = "id") long id, @ModelAttribute Optional<DealerModel> dealer) {
		if(dealer.isPresent()) {
			dealerService.updateDealer(id, dealer);
			return "update";
		}
		return "error";
	}
	
	public static Comparator<CarModel> comparePrice = new Comparator<CarModel>() {
		  public int compare(CarModel o1, CarModel o2) {
		   Long price1 = Long.parseLong(o1.getPrice());
		   Long price2 = Long.parseLong(o2.getPrice());
		   
		   return price1.compareTo(price2);
		  }
		 };
}