package com.example.SprintMaster.Dto;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Field {
	public String worked;
	public String breakTaken;
	public String slaStatus;
	@JsonIgnore
	public long breakVal;
	@JsonIgnore
	public long workVal;

}
