package uk.co.hd_tech.openstf.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Network {

    private Boolean connected;
    private Boolean failover;
    private Boolean roaming;
    private String subtype;
    private String type;

}
