package com.job.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.job.repository.*;
import com.job.dto.JobCompanyDTO;
import com.job.entity.Job;
import com.job.exception.ResourceNotFoundException;
import com.job.external.Company;
import com.job.service.JobService;

import jakarta.transaction.Transactional;

@Service
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;

	public JobServiceImpl(JobRepository jobRepository) {
		super();
		this.jobRepository = jobRepository;
	}

	@Override
	public List<JobCompanyDTO> getAll() {

		List<Job> jobs = jobRepository.findAll();

		List<JobCompanyDTO> jobCompanyDTOList = new ArrayList<>();

		RestTemplate restTemplate = new RestTemplate();

		for (Job job : jobs) {

			JobCompanyDTO jobCompanyDTO = new JobCompanyDTO();
			jobCompanyDTO.setJob(job);
			Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(),
					Company.class);
			jobCompanyDTO.setCompany(company);
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
	public Job getJobById(Long id) {
		return jobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job with given id is not found on server"));
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
