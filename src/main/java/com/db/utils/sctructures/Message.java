package com.db.utils.sctructures;

import lombok.*;

import java.util.Date;

/**
 * Class that represents a message. Contains a body of the message and server timestamp
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String body;
    private Date timestamp;
    private String login;
}
