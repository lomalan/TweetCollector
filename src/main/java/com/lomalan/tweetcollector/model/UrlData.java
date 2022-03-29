package com.lomalan.tweetcollector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UrlData implements Serializable {

  @JsonProperty("expanded_url")
  private String expandedUrl;

}
