package com.job.dto;


import java.util.List;

import com.job.external.Company;
import com.job.external.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobDTO {

	private Long id;
	private String title;
	private String description;
	private String minSalary;
	private String maxSalary;
	private String location;
	private Long companyId;
	private Company company;
	private List<Review> review;

}
