package uk.co.hd_tech.openstf.client.rest;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoteConnectUserDeviceResponse {

    private Boolean success;
    private String remoteConnectUrl;
    private String serial;

}
