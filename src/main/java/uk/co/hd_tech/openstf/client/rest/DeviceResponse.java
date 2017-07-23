package uk.co.hd_tech.openstf.client.rest;

import lombok.Getter;
import lombok.Setter;
import uk.co.hd_tech.openstf.client.model.Device;

@Getter
@Setter
public class DeviceResponse {

    private Boolean success;
    private Device device;

}
