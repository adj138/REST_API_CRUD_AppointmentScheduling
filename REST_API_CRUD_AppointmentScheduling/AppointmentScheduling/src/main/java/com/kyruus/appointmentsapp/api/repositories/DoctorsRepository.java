package com.kyruus.appointmentsapp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kyruus.appointmentsapp.api.entities.Doctors;

public interface DoctorsRepository extends JpaRepository<Doctors, Integer> {

}
