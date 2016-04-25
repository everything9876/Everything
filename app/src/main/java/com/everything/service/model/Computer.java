package com.everything.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Mirek on 2016-04-21.
 */
public class Computer extends BaseResponse {
    @Getter @Setter int id;
    @Getter @Setter String name;

    public Computer(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
