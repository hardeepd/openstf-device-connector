package uk.co.hd_tech.openstf.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Browser {

    private List<App> apps;
    private Boolean selected;

}
