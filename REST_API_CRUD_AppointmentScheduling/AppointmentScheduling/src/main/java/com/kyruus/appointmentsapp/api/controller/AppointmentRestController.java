package com.kyruus.appointmentsapp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kyruus.appointmentsapp.api.dto.CreateAppointment;
import com.kyruus.appointmentsapp.api.dto.CreateDoctorSchedule;
import com.kyruus.appointmentsapp.api.dto.UpdateAppointment;
import com.kyruus.appointmentsapp.api.entities.Appointments;
import com.kyruus.appointmentsapp.api.entities.DoctorSchedule;
import com.kyruus.appointmentsapp.api.entities.Doctors;
import com.kyruus.appointmentsapp.api.repositories.AppointmentsRepository;
import com.kyruus.appointmentsapp.api.repositories.DoctorScheduleRepository;
import com.kyruus.appointmentsapp.api.repositories.DoctorsRepository;

@RestController
@CrossOrigin
public class AppointmentRestController {
	
	@Autowired
	DoctorsRepository doctorRepo;
	
	@Autowired
	DoctorScheduleRepository docScheduleRepo;
	
	@Autowired
	AppointmentsRepository apptRepo;
	
	@RequestMapping(value = "/doctors", method = RequestMethod.GET)
	public List<Doctors> listAllDoctors() {
		return doctorRepo.findAll();
	}
	
	@RequestMapping(value = "/CreateDoctureSchedule", method = RequestMethod.POST)
	@Transactional
	public DoctorSchedule saveDoctorSchedule(@RequestBody CreateDoctorSchedule request) {
		Doctors doc = doctorRepo.findById(request.getDoctorId()).get();
		
		DoctorSchedule docsch = new DoctorSchedule();
		docsch.setDoctor(doc);
		docsch.setWeekNo(request.getWeekNo());
		docsch.setStartTime(request.getStartTime());
		docsch.setEndTime(request.getEndTime());
		docsch.setLocation(request.getLocation());
		
		return docScheduleRepo.save(docsch);
		
	}
	
	@RequestMapping(value = "/CreateAppointment", method = RequestMethod.POST)
	@Transactional
	public Appointments saveAppointment(@RequestBody CreateAppointment request) {
		//System.out.println("Save Appointment" + request.getAppointmentId());
		Doctors doc = doctorRepo.findById(request.getDoctorId()).get();
		DoctorSchedule docSchedule = docScheduleRepo.findById(request.getDoctorScheduleId()).get();
		
		Appointments appt = new Appointments();
		appt.setDoctor(doc);
		appt.setDoctorSchedule(docSchedule);
		appt.setPatientId(request.getPatientId());
		appt.setLocation(docSchedule.getLocation());
		appt.setAppointmentDate(request.getAppointmentDate());
		appt.setStartTime(request.getStartTime());
		appt.setEndTime(request.getEndTime());
		
		return apptRepo.save(appt);
		
	}
	
	
	@RequestMapping(value = "/UpdateAppointmentByID", method = RequestMethod.PUT)
	public Appointments updateAppointment(@RequestBody UpdateAppointment request) {
		
		Appointments appt = apptRepo.findById(request.getId()).get();
		appt.setCancelFlag(request.isCancelFlag());
		
		return apptRepo.save(appt);
	}
	
	
	@RequestMapping(value = "/AppointmentsByDoctor", method = RequestMethod.GET)
	public List<Appointments> ApptByDoctor(@RequestParam("doctorIdParam") int docid) {
		return apptRepo.findApptByDoctor(docid);
	}
	
}
