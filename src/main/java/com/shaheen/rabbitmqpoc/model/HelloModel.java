package com.shaheen.rabbitmqpoc.model;

public class HelloModel {
  private Integer messageId;
  private String message;

  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
