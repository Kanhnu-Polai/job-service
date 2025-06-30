package com.skillverify.jobservice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/job")
@Slf4j

public class JobController {

	
	@PostMapping("/create")
	public String createJob() {
		return "Job created successfully";
	}
	
	
	
	@PutMapping("/update")
	public String updateJob() {
		return "Job updated successfully";
	}
	
	
	@DeleteMapping("/delete")
	public String deleteJob() {
		return "Job deleted successfully";
	}
	
	
	@GetMapping("/getjob")
	public String getJobById() {
		return "Job details fetched successfully";
	}
	
	@PutMapping("/updateShortListFieldById")
	public String updateShortListFieldById() {
		return "Shortlist field updated successfully";
	}
}