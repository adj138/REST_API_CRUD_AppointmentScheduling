package com.kyruus.appointmentsapp.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kyruus.appointmentsapp.api.entities.Appointments;

public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
	
	@Query("from Appointments where DOCTOR_ID=:docidparam")
	List<Appointments> findApptByDoctor(@Param("docidparam") int docid);
	
} 
