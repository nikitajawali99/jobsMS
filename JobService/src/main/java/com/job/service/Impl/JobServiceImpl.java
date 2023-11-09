package com.job.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.job.repository.*;
import com.job.dto.JobDTO;
import com.job.dto.JobDTO;
import com.job.entity.Job;
import com.job.exception.ResourceNotFoundException;
import com.job.external.Company;
import com.job.external.Review;
import com.job.mapper.JobMapper;
import com.job.service.JobService;

import jakarta.transaction.Transactional;

@Service
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;

	@Autowired
	RestTemplate restTemplate;
	
	public JobServiceImpl(JobRepository jobRepository) {
		super();
		this.jobRepository = jobRepository;
	}

	@Override
	public List<JobDTO> getAll() {

		List<Job> jobs = jobRepository.findAll();

		List<JobDTO> jobCompanyDTOList = new ArrayList<>();

		for (Job job : jobs) {

			JobDTO jobCompanyDTO = new JobDTO();
			jobCompanyDTO.setId(job.getId());
			jobCompanyDTO.setTitle(job.getTitle());
			jobCompanyDTO.setLocation(job.getLocation());
			jobCompanyDTO.setDescription(job.getDescription());
			jobCompanyDTO.setMaxSalary(job.getMaxSalary());
			jobCompanyDTO.setMinSalary(job.getMinSalary());
			Company company = restTemplate.getForObject("http://Company-Service:8081/companies/" + job.getCompanyId(),
					Company.class);
			jobCompanyDTO.setCompany(company);
			
			ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
					"http://Review-Service:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Review>>() {
					});
			
			List<Review> reviews=reviewResponse.getBody();
			jobCompanyDTO.setReview(reviews);
			jobCompanyDTOList.add(jobCompanyDTO);

		}

		return jobCompanyDTOList;
	}

	@Override
	@Transactional
	public Job createJob(Job job) {

		if (job.getId() != null) {
			job.setId(job.getId());
		}
		job.setDescription(job.getDescription());
		job.setLocation(job.getLocation());
		job.setMaxSalary(job.getMaxSalary());
		job.setMinSalary(job.getMinSalary());
		job.setTitle(job.getTitle());
		return jobRepository.save(job);
	}

	@Override
	public JobDTO getJobById(Long id) {
		Job job = jobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job with given id is not found on server"));

		Company company = restTemplate.getForObject("http://Company-Service:8081/companies/" + job.getCompanyId(),
				Company.class);
		
		ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
				"http://Review-Service:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Review>>() {
				});
		
		List<Review> reviews=reviewResponse.getBody();
		
		JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company,reviews);
		
		return jobDTO;

	}

	@Override
	public void getDeleteById(Long id) {

		if (id != null) {
			jobRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Job with given id is not found on server"));
		}

		jobRepository.deleteById(id);

	}

}
