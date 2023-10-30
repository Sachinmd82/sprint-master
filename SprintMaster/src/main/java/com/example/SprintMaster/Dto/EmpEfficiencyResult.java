package com.example.SprintMaster.Dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class EmpEfficiencyResult {
	Map<Integer,Field> EmpIdToVal = new HashMap<>();
	Map<Integer, String> empToName = new HashMap<Integer, String>();

}
