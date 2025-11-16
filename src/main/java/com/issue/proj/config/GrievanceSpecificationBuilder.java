package com.issue.proj.config;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.issue.proj.entity.Grievance;

import jakarta.persistence.criteria.Predicate;

public class GrievanceSpecificationBuilder {
	
	public static Specification<Grievance> filter(
			
			String category,
			String status,
			String raisedBy,
			String assignedTo,
			LocalDateTime fromDate,
			LocalDateTime toDate
			){
		
		 return (root, query, cb) -> {
	            Predicate predicate = cb.conjunction();

	            if (category != null && !category.isEmpty()) {
	                predicate = cb.and(predicate, cb.equal(root.get("category"), category));
	            }

	            if (status != null && !status.isEmpty()) {
	                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
	            }

	            if (raisedBy != null && !raisedBy.isEmpty()) {
	                predicate = cb.and(predicate, cb.equal(root.get("raisedBy"), raisedBy));
	            }

	            if (assignedTo != null && !assignedTo.isEmpty()) {
	                predicate = cb.and(predicate, cb.equal(root.get("assignedTo"), assignedTo));
	            }

	            if (fromDate != null) {
	                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
	            }

	            if (toDate != null) {
	                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
	            }

	            return predicate;
	        };
		
	}

}
