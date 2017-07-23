package uk.co.hd_tech.openstf.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Device {

    private String abi;
    private Boolean airplaneMode;
    private Battery battery;
    private Browser browser;
    private String channel;
    private String createdAt;
    private Display display;
    private String manufacturer;
    private String model;
    private Network network;
    private String operator;
    private Owner owner;
    private Phone phone;
    private String platform;
    private String presenceChangedAt;
    private Boolean present;
    private String product;
    private Provider provider;
    private Boolean ready;
    private Boolean remoteConnect;
    private String remoteConnectUrl;
    private List<Object> reverseForwards = null;
    private String sdk;
    private String serial;
    private Integer status;
    private String statusChangedAt;
    private Object usage;
    private String version;
    private Boolean using;


}
