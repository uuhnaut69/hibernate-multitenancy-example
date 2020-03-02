package com.uuhnaut69.demo.request;

import com.uuhnaut69.demo.model.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TodoRequest {

    @NotNull(message = "Must not leave blank !!!")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Must not leave blank !!!")
    private Status status;
}
