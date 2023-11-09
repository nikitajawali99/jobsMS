package com.job.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job.dto.JobDTO;
import com.job.entity.Job;
import com.job.service.JobService;


@RestController
@RequestMapping("/jobs")
public class JobController {

	private final JobService jobService;

	public JobController(JobService jobService) {
		super();
		this.jobService = jobService;
	}

	@GetMapping
	public ResponseEntity<List<JobDTO>> findAll() {

		return ResponseEntity.ok(jobService.getAll());

	}
	
	@PostMapping("/createJob")
	public ResponseEntity<Job> createJob(@RequestBody Job job) {

		return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(job));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable("id") Long id) {
		
		return ResponseEntity.ok(jobService.getJobById(id));
	}
	
	@DeleteMapping("/deleteById/{id}")
	public String getDeleteById(@PathVariable("id") Long id) {

		jobService.getDeleteById(id);
		return "Job Deleted successfully";
	}

}
