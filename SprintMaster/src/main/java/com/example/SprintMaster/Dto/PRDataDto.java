package com.example.SprintMaster.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PRDataDto {

    private String status;
    private String updatedDate;
    private String createDate;
    private String author;
    private String sourceBranch;
    private String destinationBranch;

}
