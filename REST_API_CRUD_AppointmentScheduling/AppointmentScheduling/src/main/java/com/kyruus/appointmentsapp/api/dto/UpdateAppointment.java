package com.kyruus.appointmentsapp.api.dto;

public class UpdateAppointment {
	
	private int id;
	private boolean cancelFlag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(boolean cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

}
