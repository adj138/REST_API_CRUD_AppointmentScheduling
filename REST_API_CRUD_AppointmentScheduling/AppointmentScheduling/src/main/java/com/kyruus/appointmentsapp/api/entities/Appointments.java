package com.kyruus.appointmentsapp.api.entities;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Appointments extends IDentity{
	
	@OneToOne
	private Doctors doctor;
	
	@OneToOne
	private DoctorSchedule doctorSchedule;
	
	private Integer patientId;
	private String location;
	private Date appointmentDate;
	private Time startTime;
	private Time endTime;
	private Boolean cancelFlag;
	
	public Doctors getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}
	public DoctorSchedule getDoctorSchedule() {
		return doctorSchedule;
	}
	public void setDoctorSchedule(DoctorSchedule doctorSchedule) {
		this.doctorSchedule = doctorSchedule;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public Boolean getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(Boolean cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

}
