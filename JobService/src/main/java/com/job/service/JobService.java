package com.job.service;

import java.util.List;

import com.job.dto.JobCompanyDTO;
import com.job.entity.Job;

public interface JobService {

	List<JobCompanyDTO> getAll();

	Job createJob(Job job);

	Job getJobById(Long id);

	void getDeleteById(Long id);

}
