package uk.co.hd_tech.openstf.client.rest;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddUserDevicePayload {
    private String serial;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer timeout;
}
