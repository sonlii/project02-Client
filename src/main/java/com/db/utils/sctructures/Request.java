package com.db.utils.sctructures;

import com.db.CommandType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Request {
    private Message message;
    private CommandType commandType;
}
