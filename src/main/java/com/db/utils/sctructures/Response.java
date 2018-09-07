package com.db.utils.sctructures;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Message message;
    private int status;
}
