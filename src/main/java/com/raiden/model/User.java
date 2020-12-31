package com.raiden.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 17:36 2020/10/12
 * @Modified By:
 */
@Getter
@Setter
public class User {
    @NotBlank
    private String name;
}
