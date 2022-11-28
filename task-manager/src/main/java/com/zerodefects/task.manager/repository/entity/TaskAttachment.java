package com.zerodefects.task.manager.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
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
    private String taskId;

}
