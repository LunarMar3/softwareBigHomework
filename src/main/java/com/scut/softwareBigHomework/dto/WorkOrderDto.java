package com.scut.softwareBigHomework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderDto {
    int id;

    String title;

    String description;

    String status;

    int priority;

    String type;

    LocalDateTime deadline;

    int departmentId;

    int assigneeId;

}
