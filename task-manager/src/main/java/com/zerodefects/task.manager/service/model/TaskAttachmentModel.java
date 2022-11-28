package com.zerodefects.task.manager.service.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

import javax.persistence.Lob;


@Getter
@Setter
public class TaskAttachmentModel {
    private String id;
    private String name;

    private String type;

    @Lob
    private byte[] data;
    private String taskId;
}
