package com.ken.shared.constants;

public class RabbitMQKeys {

  public static final String AUTHOR_CREATED_EXCHANGE =
    "message.exchange.fanout.author.created";

  public static final String AUTHOR_UPDATE_EXCHANGE =
    "message.exchange.fanout.author.updated";

  public static final String AUTHOR_DELETED_EXCHANGE =
    "message.exchange.fanout.author.deleted";
}
