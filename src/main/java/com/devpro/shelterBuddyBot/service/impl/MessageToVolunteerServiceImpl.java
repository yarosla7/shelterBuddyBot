package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.model.enity.MessageToVolunteer;
import com.devpro.shelterBuddyBot.model.enity.StatusMessage;
import com.devpro.shelterBuddyBot.repository.rep.MessageToVolunteerRepository;
import com.devpro.shelterBuddyBot.service.MessageToVolunteerService;

import com.devpro.shelterBuddyBot.service.ValidationService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageToVolunteerServiceImpl implements MessageToVolunteerService {
    private final MessageToVolunteerRepository messageToVolunteerRepository;
    private final ValidationService validationService;

    @Override
    public MessageToVolunteer createMessageToVolunteer(MessageToVolunteer messageToVolunteer) {
        if (!validationService.validate(messageToVolunteer)) {
            throw new ValidationException(messageToVolunteer.toString());
        }
        return messageToVolunteerRepository.save(messageToVolunteer);
    }

    @Override
    public MessageToVolunteer findById(Long id) {
        Optional<MessageToVolunteer> message = messageToVolunteerRepository.findById(id);
        if (message.isPresent()) {
            return message.get();
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public MessageToVolunteer updateById(Long id, MessageToVolunteer messageToVolunteer) {
        if (messageToVolunteerRepository.findById(id).isPresent()) {
            messageToVolunteer.setId(id);
            return messageToVolunteerRepository.save(messageToVolunteer);
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public MessageToVolunteer deleteById(Long id) {
        MessageToVolunteer message = findById(id);
        messageToVolunteerRepository.delete(message);
        return message;
    }

    @Override
    public List<MessageToVolunteer> findAll() {
        return messageToVolunteerRepository.findAll();
    }

    @Override
    public MessageToVolunteer createMessageFromText(String text){
        String[] arr = text.split(" ");
        String sender = arr[0];
        String message = "";
        for (int i = 1; i < arr.length; i++) {
            message = message + " " + arr[i];
        }
        message = sender + " " + message;
        return new MessageToVolunteer(sender, message);
    }

    /**
     * проверяет есть ли непрочитанные уведомления
     * @return
     */
    @Override
    public boolean checker(){
        boolean check = false;
        List<MessageToVolunteer> message = findAll();
        for (MessageToVolunteer messageToVolunteer : message) {
            if (messageToVolunteer.getStatusMessage().equals(StatusMessage.UNREAD)){
                check = true;
            }
        }
        return check;
    }

    @Override
    public List<String> findAllUnread() {
        List<MessageToVolunteer> message = messageToVolunteerRepository.findAll();
        List<String> toSend = message.stream()
                .filter(x -> x.getStatusMessage().equals(StatusMessage.UNREAD))
                .map(x -> x.getText())
                .collect(Collectors.toList());
        for (MessageToVolunteer messageToVolunteer : message) {
            messageToVolunteer.setStatusMessage(StatusMessage.READ);
            createMessageToVolunteer(messageToVolunteer);
        }
        return toSend;
    }
}