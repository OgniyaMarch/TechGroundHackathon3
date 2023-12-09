package com.example.EVeteranBot.service;
import com.example.EVeteranBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    int numberOfQuestion;
    List<String> userInfo = new ArrayList<>();
    String service;
    boolean isRegistering = false;
    boolean isConnectionWithTheOperator = true;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(service);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else if (messageText.equals("/commands")) {
                sendCommands(chatId);
            } else if (messageText.equals("/register")) {
                registerCommandReceived(chatId);
            } else if (messageText.equals("/services")) {
                keepAllServices(chatId);
                isRegistering = false;  // Завершити процес реєстрації
                isConnectionWithTheOperator = true;
            } else if (isRegistering) {
                handleRegistrationAnswer(chatId, messageText);
            }else if(isConnectionWithTheOperator){
                service = messageText;  // Зберегти значення сервісу
                if (!service.equals("/15")){
                    sendMessage(chatId, "Зв'язок з оператором: /here");
                }
                isConnectionWithTheOperator = false;
            }
        }
    }

    private void sendCommands(long chatId) {
        sendMessage(chatId, "Функціонал чат-бота:\n" + BotFunctionality.printFunctionality());
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Добрий день, " + name + ", це чат-бот eVeteran. Для перегляду команд натисніть /commands";
        sendMessage(chatId, answer);
    }

    private void registerCommandReceived(long chatId) {
        numberOfQuestion = 0;
        userInfo.clear();
        sendMessage(chatId, RegistrationQuestions.getQuestionById(numberOfQuestion).getQuestion());
        isRegistering = true;  // Розпочати процес реєстрації
    }

    private void handleRegistrationAnswer(long chatId, String answer) {
        if (numberOfQuestion >= 0 && numberOfQuestion < RegistrationQuestions.values().length) {
            userInfo.add(answer);

            if (numberOfQuestion < RegistrationQuestions.values().length - 1) {
                numberOfQuestion++;
                sendMessage(chatId, RegistrationQuestions.getQuestionById(numberOfQuestion).getQuestion());
            } else {
                // Завершення реєстрації, можна використовувати userInfo
                sendRegistrationResults(chatId);
                isRegistering = false;  // Завершити процес реєстрації
            }
        }
    }

    private void sendRegistrationResults(long chatId) {
        // Логіка для виведення результатів реєстрації
        StringBuilder resultMessage = new StringBuilder("Реєстраційні дані:\n");

        createUser(chatId, userInfo);
        resultMessage.append(RegistrationQuestions.getQuestionById(0).getQuestion()).append(" ").append(userInfo.get(0)).append("\n");
        resultMessage.append(RegistrationQuestions.getQuestionById(1).getQuestion()).append(" ").append(userInfo.get(1)).append("\n");
        resultMessage.append(RegistrationQuestions.getQuestionById(2).getQuestion()).append(" ").append(userInfo.get(2)).append("\n");
        resultMessage.append(RegistrationQuestions.getQuestionById(3).getQuestion()).append(" ").append(userInfo.get(3)).append("\n");
        resultMessage.append(RegistrationQuestions.getQuestionById(4).getQuestion()).append(" ").append(userInfo.get(4)).append("\n");
        String question5 = "Область проживання: ";
        resultMessage.append(question5).append(userInfo.get(5)).append("\n");
        String question6 = "Категорія: ";
        resultMessage.append(question6).append(userInfo.get(6)).append("\n");

        sendMessage(chatId, resultMessage.toString());
    }

    private User createUser(long chatId, List<String> userInfo) {
        User user = new User();
        user.setChatID(chatId);
        user.setPhoneNumber(Integer.parseInt(userInfo.get(RegistrationQuestions.PHONE_NUMBER.ordinal())));
        user.setSurname(userInfo.get(RegistrationQuestions.SURNAME.ordinal()));
        user.setName(userInfo.get(RegistrationQuestions.NAME.ordinal()));
        user.setFatherName(userInfo.get(RegistrationQuestions.FATHER_NAME.ordinal()));
        user.setBirthDate(userInfo.get(RegistrationQuestions.BIRTH_DATE.ordinal()));
        user.setRegion(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal()).charAt(1)));
        user.setCategoryOfUser(userInfo.get(RegistrationQuestions.CATEGORY.ordinal()));

        //     MongoDBClient client = new MongoDBClient(user);

        System.out.println(user);
        return user;
    }




    private void keepAllServices(long chatId) {
        sendMessage(chatId, "Послуги:\n" +
                Services.getGeneralServices()
        );
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}