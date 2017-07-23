package uk.co.hd_tech.openstf.client.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private Boolean success;
    private String description;

}
