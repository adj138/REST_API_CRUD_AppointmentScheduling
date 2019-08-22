package com.kyruus.appointmentsapp.api.entities;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DoctorSchedule extends IDentity{
	
	@OneToOne
	private Doctors doctor;
	
	private Integer weekNo;
	private Time startTime;
	private Time endTime;
	private String location;
	
	public Doctors getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}
	public Integer getWeekNo() {
		return weekNo;
	}
	public void setWeekNo(Integer weekNo) {
		this.weekNo = weekNo;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
