package io.medhanie.akrweb.be.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Setter
@Getter
public class User {
    private String userName;
    private String password;
    private boolean enabled;

}
