package com.db.utils.sctructures;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Message message;
    private int status;
}
