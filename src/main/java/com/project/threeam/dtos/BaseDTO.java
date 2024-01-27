package com.project.threeam.dtos;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseDTO {
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
}
