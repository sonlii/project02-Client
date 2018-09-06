package com.db.utils.sctructures;

import com.db.CommandType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private Message message;
    private CommandType commandType;
}
