package com.everything.service.model;

import java.util.ArrayList;

import lombok.Getter;

/**
 * Created by Mirek on 2016-04-22.
 */
public class ComputersContainer extends BaseResponse {
    @Getter ArrayList<Computer> computers;
}
