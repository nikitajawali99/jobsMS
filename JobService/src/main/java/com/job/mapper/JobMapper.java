package com.job.mapper;

import java.util.List;

import com.job.dto.JobDTO;
import com.job.entity.Job;
import com.job.external.Company;
import com.job.external.Review;

public class JobMapper {
	
	
	public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews) {

		JobDTO jobCompanyDTO = new JobDTO();
		jobCompanyDTO.setId(job.getId());
		jobCompanyDTO.setTitle(job.getTitle());
		jobCompanyDTO.setLocation(job.getLocation());
		jobCompanyDTO.setDescription(job.getDescription());
		jobCompanyDTO.setMaxSalary(job.getMaxSalary());
		jobCompanyDTO.setMinSalary(job.getMinSalary());
		jobCompanyDTO.setCompany(company);
		jobCompanyDTO.setReview(reviews);
		return jobCompanyDTO;

	}

}
