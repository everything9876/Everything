package com.everything.service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Mirek on 2016-03-21.
 */
public class User extends BaseResponse {
    @Getter @Setter String login;
    @Getter @Setter String password;

    @Getter @Setter  String fbId;
    @Getter @Setter String fbToken;
    @Getter @Setter String fbUserName;

    @Getter @Setter private String gPlusDisplayName;
}
