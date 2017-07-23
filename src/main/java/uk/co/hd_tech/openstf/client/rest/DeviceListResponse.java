package uk.co.hd_tech.openstf.client.rest;

import lombok.Getter;
import lombok.Setter;
import uk.co.hd_tech.openstf.client.model.Device;

import java.util.List;

@Getter
@Setter
public class DeviceListResponse {

    private Boolean success;
    private List<Device> devices = null;

}
