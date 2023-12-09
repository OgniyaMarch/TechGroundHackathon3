package com.example.EVeteranBot.service;

public enum RegistrationQuestions {
    PHONE_NUMBER(0,"Ваш контактний номер телефону: "),
    SURNAME(1,"Ваше прізвище: "),
    NAME(2,"Ваше ім'я: "),
    FATHER_NAME(3,"Ваше по-батькові: "),
    BIRTH_DATE(4,"Дата народження: "),
    REGION(5,"Область проживання: \n" +
            "/Krym\n" +
            "/Vinnytska \n" +
            "/Volynska \n" +
            "/Dnipropetrovska \n" +
            "/Donetska \n" +
            "/Zakarpatska \n" +
            "/Zhytomyrska \n" +
            "/Zaporizka \n" +
            "/IvanoFrankivska \n" +
            "/Kyivska \n" +
            "/Kirovohradska \n" +
            "/Luhanska\n" +
            "/Lvivska\n" +
            "/Mykolaivska\n" +
            "/Odeska\n" +
            "/Poltavska\n" +
            "/Rivnenska\n" +
            "/Sumska\n" +
            "/Ternopilska\n" +
            "/Kharkivska\n" +
            "/Khersonska\n" +
            "/Khmelnytska\n" +
            "/Cherkaska\n" +
            "/Chernivetska\n" +
            "/Chernihivska\n"),
    CATEGORY(6,"Категорія: \n" +
            "/1. - Ветерани війни, які мають особливі заслуги перед Батьківщиною\n" +
            "/2. - Члени сімей ветеранів війни та осіб, які мають особливі заслуги перед Батьківщиною\n" +
            "/3. - Члени сімей загиблих(померлих) ветеранів війни, Захисників і Захісниць України\n" +
            "/4. - Військовослужбовці, які брали безпосередню участь у заходах, необхідних для забезпечення оборони України, захисту безпеки населення та інтересів держави, та були звільнені з військової служби, зокрема демобілізовані у визначеному законом порядку\n");
    int ID;
    String question;

    public static RegistrationQuestions getQuestionById(int id) {
        for (RegistrationQuestions question : values()) {
            if (question.getID() == id) {
                return question;
            }
        }
        return null;
    }
    RegistrationQuestions(int ID, String question) {
        this.ID = ID;
        this.question = question;
    }

    @Override
    public String toString() {
        return  question;
    }

    public String getQuestion() {
        return question;
    }

    public int getID() {
        return ID;
    }
}
