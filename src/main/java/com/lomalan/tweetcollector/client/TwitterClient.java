package com.lomalan.tweetcollector.client;

import com.lomalan.tweetcollector.model.TwitterResponse;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class TwitterClient {

  @Value("${twitter.api.url}")
  private String twitterUrl;
  @Value("${twitter.token}")
  private String twitterToken;
  private final RestTemplate restTemplate;
  private static final String TWEETS_ENDPOINT = "tweets/search/recent";

  public TwitterClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Optional<TwitterResponse> getRecentTweets() {
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromHttpUrl(twitterUrl.concat(TWEETS_ENDPOINT))
        .queryParam("query", "from:F1")
        .queryParam("tweet.fields", "created_at,entities");
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(twitterToken);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<TwitterResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
        TwitterResponse.class);
    if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
      return Optional.empty();
    }
    return Optional.ofNullable((response.getBody()));
  }
}
