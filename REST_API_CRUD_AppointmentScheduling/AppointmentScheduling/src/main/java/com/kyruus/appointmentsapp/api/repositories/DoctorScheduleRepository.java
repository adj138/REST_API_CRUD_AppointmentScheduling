package com.kyruus.appointmentsapp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kyruus.appointmentsapp.api.entities.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {

}
