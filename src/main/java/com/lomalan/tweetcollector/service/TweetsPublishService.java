package com.lomalan.tweetcollector.service;

import com.lomalan.tweetcollector.client.TwitterClient;
import com.lomalan.tweetcollector.model.TwitterData;
import com.lomalan.tweetcollector.model.TwitterResponse;
import com.lomalan.tweetcollector.model.UrlData;
import com.lomalan.tweetcollector.producer.Producer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TweetsPublishService {

  private final Producer producer;
  private final TwitterClient twitterClient;

  public TweetsPublishService(Producer producer, TwitterClient twitterClient) {
    this.producer = producer;
    this.twitterClient = twitterClient;
  }

  @Scheduled(initialDelay = 5000, fixedDelay = 280000)
  public void publishTwitterData() {
    log.info("Start to search recent tweets!");
    twitterClient.getRecentTweets()
        .ifPresent(this::retrieveRecentTweetsUrls);
  }

  private void retrieveRecentTweetsUrls(TwitterResponse response) {
    response.getData().stream()
        .filter(this::filterTwitterData)
        .map(this::mapToExtendedUrl)
        .forEach(producer::sendMessage);
  }

  private String mapToExtendedUrl(TwitterData twitterData) {
    log.info("TWITTER DATA: {}", twitterData);
    List<UrlData> urlData = twitterData.getEntities().getUrls();
    if (urlData == null || urlData.isEmpty()) {
      return twitterData.getText();
    }

    if (urlData.size() == 1) {
      return urlData.get(0).getExpandedUrl();
    }

    return urlData.stream()
        .map(UrlData::getExpandedUrl)
        .filter(expandedUrl -> expandedUrl.contains("twitter"))
        .findFirst()
        .orElse(twitterData.getText());
  }

  private boolean filterTwitterData(TwitterData twitterData) {
    return twitterData.getCreatedAt().plusMinutes(6)
        .isAfter(LocalDateTime.now(ZoneOffset.UTC));
  }
}
