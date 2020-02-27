package com.uuhnaut69.core.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TodoRequest {

    @NotBlank(message = "Must not leave blank !!!")
    private String name;
}
