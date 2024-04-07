package com.example.SprintMaster.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperEffDto {

    private int id;
    private String status;
    private String createdDate;
    private String lastUpdatedDate;
    private String author;
    private String sourceBranch;
    private String destinationBranch;

}
