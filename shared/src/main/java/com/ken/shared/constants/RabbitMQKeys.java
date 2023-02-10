package com.ken.shared.constants;

public class RabbitMQKeys {

  // author
  public static final String AUTHOR_CREATED_EXCHANGE =
    "message.exchange.fanout.author.created";

  public static final String AUTHOR_UPDATE_EXCHANGE =
    "message.exchange.fanout.author.updated";

  public static final String AUTHOR_DELETED_EXCHANGE =
    "message.exchange.fanout.author.deleted";

  // book
  public static final String BOOK_CREATED_EXCHANGE =
    "message.exchange.fanout.book.created";
  public static final String BOOK_DELETED_EXCHANGE =
    "message.exchange.fanout.book.deleted";
  public static final String BOOK_UPDATED_EXCHANGE =
    "message.exchange.fanout.book.updated";
}
