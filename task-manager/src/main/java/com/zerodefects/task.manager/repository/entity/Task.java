package com.zerodefects.task.manager.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;


@Document
@Getter
@Setter
public class Task {

    @Id
    private String id;

    private String title;
    private String description;
    private Integer taskTime;
    private Date createdDate;
    private String createdBy;
}
