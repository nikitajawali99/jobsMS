package com.job.dto;

import com.job.entity.Job;
import com.job.external.Company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobCompanyDTO {

	private Job job;
	private Company company;

}
