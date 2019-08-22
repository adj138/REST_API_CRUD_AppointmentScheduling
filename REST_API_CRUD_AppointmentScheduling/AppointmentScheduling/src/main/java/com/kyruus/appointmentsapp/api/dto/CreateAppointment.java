package com.kyruus.appointmentsapp.api.dto;

import java.sql.Date;
import java.sql.Time;

public class CreateAppointment {
	
	private int doctorId;
	private int doctorScheduleId;
	private int patientId;
	private Date appointmentDate;
	private Time startTime;
	private Time endTime;
	private Boolean cancelFlag;
	
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public int getDoctorScheduleId() {
		return doctorScheduleId;
	}
	public void setDoctorScheduleId(int doctorScheduleId) {
		this.doctorScheduleId = doctorScheduleId;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
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
