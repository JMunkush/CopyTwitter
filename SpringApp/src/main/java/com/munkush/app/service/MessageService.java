package com.munkush.app.service;

import com.munkush.app.entity.Message;

public interface MessageService {

    Iterable<Message> getAll();

    Message getOne(int id);

    void save(Message message);

    void delete(Message message);

}
