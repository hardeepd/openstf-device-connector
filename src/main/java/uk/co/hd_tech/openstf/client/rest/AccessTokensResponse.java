package uk.co.hd_tech.openstf.client.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccessTokensResponse {

    private List<String> tokens;

}
