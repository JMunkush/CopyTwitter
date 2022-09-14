package com.munkush.app.service;

import com.munkush.app.entity.Message;
import com.munkush.app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAll() {
        return (List<Message>) messageRepository.findAll();
    }

    @Override
    public Message getOne(int id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {

    }

    public List<Message> findByTag(String s){
       return messageRepository.findByTag(s);
    }

}
