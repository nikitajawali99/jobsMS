package com.job.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.job.repository.*;
import com.job.client.CompanyClient;
import com.job.client.ReviewClient;
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
	private final CompanyClient companyClient;
	private final ReviewClient reviewClient;

	@Autowired
	RestTemplate restTemplate;
	
	public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
		super();
		this.jobRepository = jobRepository;
		this.companyClient=companyClient;
		this.reviewClient=reviewClient;
	}

	@Override
	public List<JobDTO> getAll() {

		List<Job> jobs = jobRepository.findAll();

		//List<JobDTO> jobCompanyDTOList = new ArrayList<>();
		return jobs.stream().map(this::convertToDto).collect(Collectors.toList());

//		for (Job job : jobs) {
//
//			JobDTO jobCompanyDTO = new JobDTO();
//			jobCompanyDTO.setId(job.getId());
//			jobCompanyDTO.setTitle(job.getTitle());
//			jobCompanyDTO.setLocation(job.getLocation());
//			jobCompanyDTO.setDescription(job.getDescription());
//			jobCompanyDTO.setMaxSalary(job.getMaxSalary());
//			jobCompanyDTO.setMinSalary(job.getMinSalary());
//			Company company = companyClient.getCompany(job.getCompanyId());
//			jobCompanyDTO.setCompany(company);
//			
////			ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
////					"http://Review-Service:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
////					new ParameterizedTypeReference<List<Review>>() {
////					});
//			
//			List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
//			
//			//List<Review> reviews=reviewResponse.getBody();
//			jobCompanyDTO.setReview(reviews);
//			jobCompanyDTOList.add(jobCompanyDTO);

//		}

		
	}
	
	
	public JobDTO convertToDto(Job job) {
		
		Company company = companyClient.getCompany(job.getCompanyId());
		List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
		JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company,reviews);
		
		return jobDTO;
		
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

//		Company company = restTemplate.getForObject("http://Company-Service:8081/companies/" + job.getCompanyId(),
//				Company.class);
//		
//		ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//				"http://Review-Service:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
//				new ParameterizedTypeReference<List<Review>>() {
//				});
//		
//		List<Review> reviews=reviewResponse.getBody();
		
		//JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company,reviews);
		
		return convertToDto(job);

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
