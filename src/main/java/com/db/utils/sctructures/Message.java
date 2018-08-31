package com.db.utils.sctructures;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Message {
    private String body;
    private Date timestamp;
}
