package com.job.service;

import java.util.List;

import com.job.dto.JobDTO;
import com.job.entity.Job;

public interface JobService {

	List<JobDTO> getAll();

	Job createJob(Job job);

	JobDTO getJobById(Long id);

	void getDeleteById(Long id);

}
