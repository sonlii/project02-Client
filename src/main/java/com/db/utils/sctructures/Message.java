package com.db.utils.sctructures;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
/**
 * Class that represents a message. Contains a bofy of the message and server timestamp
 */
public class Message {
    private String body;
    private Date timestamp;
}
