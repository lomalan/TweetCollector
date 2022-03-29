package com.lomalan.tweetcollector.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class TwitterResponse implements Serializable {

  private List<TwitterData> data;
}
