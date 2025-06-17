package com.scut.softwareBigHomework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderDto {
    Integer id;

    String title;

    String description;

    String status;

    Integer priority;

    String type;

    LocalDateTime deadline;

    Integer departmentId;

    Integer assigneeId;

    String solution;

    String comment;

    Integer operatorId;

}
