package com.zerodefects.taskmanager.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExceptionResponse {
    private Date timestamp;
    private String fingerPrint;
    private String type;
    private String detail;
    private String instance;
    private String title;
    private int status;
    private List<ErrorField> errorFields;

}
