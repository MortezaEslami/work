package com.zerodefects.taskmanager.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Lob;


@Document
@Getter
@Setter
public class TaskAttachment {

    @Id
    private String id;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    @DBRef
    private String task;

}
