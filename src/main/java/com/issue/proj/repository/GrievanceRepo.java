package com.issue.proj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.issue.proj.entity.Grievance;

public interface GrievanceRepo extends JpaRepository<Grievance, Long>, JpaSpecificationExecutor<Grievance>{
	
	Optional<Grievance> findByRaisedBy(String raisedBy);
	Optional<Grievance> findByAssignedTo(String officerEmail);

}
