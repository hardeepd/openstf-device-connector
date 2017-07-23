package uk.co.hd_tech.openstf.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phone {

    private String iccid;
    private String imei;
    private String imsi;
    private String network;
    private String phoneNumber;

}
