package com.example.EVeteranBot.service;
import com.example.EVeteranBot.bd.MongoDBClient;
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
        user.setPhoneNumber(userInfo.get(RegistrationQuestions.PHONE_NUMBER.ordinal()));
        user.setSurname(userInfo.get(RegistrationQuestions.SURNAME.ordinal()));
        user.setName(userInfo.get(RegistrationQuestions.NAME.ordinal()));
        user.setFatherName(userInfo.get(RegistrationQuestions.FATHER_NAME.ordinal()));
        user.setBirthDate(userInfo.get(RegistrationQuestions.BIRTH_DATE.ordinal()));
        if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Krym")){
            user.setRegion("657367f5997f0a6e91d4deba");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Vinnytska")){
            user.setRegion("65736b22997f0a6e91d4decb");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Volynska")){
            user.setRegion("65736b34997f0a6e91d4decc");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Dnipropetrovska")){
            user.setRegion("65736b63997f0a6e91d4decd");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Donetska")){
            user.setRegion("65736b71997f0a6e91d4dece");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Zakarpatska")){
            user.setRegion("65736b94997f0a6e91d4ded0");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Zhytomyrska")){
            user.setRegion("65736b7f997f0a6e91d4decf");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Zaporizka")){
            user.setRegion("65736ba3997f0a6e91d4ded1");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/IvanoFrankivska")){
            user.setRegion("65736bb2997f0a6e91d4ded2");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Kyivska")){
            user.setRegion("65736bbd997f0a6e91d4ded3");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Kirovohradska")){
            user.setRegion("65736bcb997f0a6e91d4ded4");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Luhanska")){
            user.setRegion("65736bd7997f0a6e91d4ded5");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Lvivska")){
            user.setRegion("65736be4997f0a6e91d4ded6");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Mykolaivska")){
            user.setRegion("65736bf0997f0a6e91d4ded7");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Odeska")){
            user.setRegion("65736bfa997f0a6e91d4ded8");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Poltavska")){
            user.setRegion("65736c06997f0a6e91d4ded9");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Rivnenska")){
            user.setRegion("65736c12997f0a6e91d4deda");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Sumska")){
            user.setRegion("65736c23997f0a6e91d4dedb");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Ternopilska")){
            user.setRegion("65736c34997f0a6e91d4dedc");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Kharkivska")){
            user.setRegion("65736c3f997f0a6e91d4dedd");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Khersonska")){
            user.setRegion("65736c4d997f0a6e91d4dede");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Khmelnytska")){
            user.setRegion("65736bcb997f0a6e91d4ded4");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Cherkaska")){
            user.setRegion("65736c6b997f0a6e91d4dee0");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Chernivetska")){
            user.setRegion("65736c79997f0a6e91d4dee1");
        }else if(String.valueOf(userInfo.get(RegistrationQuestions.REGION.ordinal())).equals("/Chernihivska")){
            user.setRegion("65736c85997f0a6e91d4dee2");
        }


        user.setCategoryOfUser(String.valueOf(userInfo.get(RegistrationQuestions.CATEGORY.ordinal()).charAt(1)));
        System.out.println(user);

        MongoDBClient client = new MongoDBClient(user);


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