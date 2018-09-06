package com.db.utils.sctructures;

import com.db.CommandType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private Message message;
    private CommandType commandType;
}
