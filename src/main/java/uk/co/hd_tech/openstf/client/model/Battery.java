package uk.co.hd_tech.openstf.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Battery {

    private String health;
    private Integer level;
    private Integer scale;
    private String source;
    private String status;
    private Double temp;
    private Double voltage;

}
