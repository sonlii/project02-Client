package com.db;

/**
 * Enum representing available types of commands
 * Currently available commands:
 * SND: send message,
 * HIST: get histroy,
 * QUIT: quit form chat
 * CHID: change current user login
 */
public  enum CommandType {
    SND,
    HIST,
    QUIT,
    CHID
}
