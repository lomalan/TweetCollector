package com.lomalan.tweetcollector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TwitterData implements Serializable {

  @JsonProperty("created_at")
  private LocalDateTime createdAt;
  private String text;
  private TwitterEntity entities;
}
