package com.db.utils.sctructures;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Class that represents a message. Contains a bofy of the message and server timestamp
 */
public class Message {
    private String body;
    private Date timestamp;
}
