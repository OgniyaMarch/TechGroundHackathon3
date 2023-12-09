package com.example.EVeteranBot.service;
public enum Services {

    CONSULTATION_EMPLOYMENT("Консультації з питань працевлаштування"),
    CONSULTATION_TRAININGS("Консультації з питань навчання"),
    CONSULTATION_SOCIAL_PROTECTION("Консультації з питань соціального захисту"),
    CONSULTATION_IMPROVEMENT_OF_PHYSICAL_HEALTH("Консультації з питань покращення стану фізичного здоров’я"),
    CONSULTATION_RECEIVING_PSYCHOLOGICAL_SERVICES("Консультації з питань отримання психологічних послуг, зокрема з питань оформлення документів для отримання таких послуг"),
    SUPPORT("Супровід у реалізації гарантій, пільг, прав, отримання послуг"),
    HELP_WITH_THE_SEARCH_PROFESSIONAL_DEVELOPMENT_PROGRAMMES("Допомога з пошуком програм підвищення кваліфікації"),
    HELP_WITH_THE_SEARCH_TRAINING_PROGRAMMES("Допомога з пошуком програм підвищення кваліфікації навчальних програм"),
    HELP_WITH_THE_SEARCH_RE_PROFILING("Допомога з пошуком програм перепрофілювання"),
    MEDIATION_IN_INTERACTION("Посередництво у взаємодії ветерана з органами влади"),
    PROVIDING_ASSISTANCE_IN_PAPERWORK("Надання допомоги в оформленні документів, поданні заяв, підготовці документів"),
    ASSISTANCE_IN_FINDING_ACCOMMODATION("Надання допомоги під час переїзду"),
    ASSISTANCE_IN_FINDING_HOUSING("Надання допомоги у пошуку житла"),
    SUPPORT_PROGRAMMES("Інформування про наявні в державі та громаді програми підтримки ветеранів"),
    EXIT("Вихід")
    ;
    String nameService;


    Services(String nameService) {
        this.nameService = nameService;
    }

    @Override
    public String toString() {
        return nameService;
    }

    public static String getGeneralServices() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (Services service: values()) {
            stringBuilder.append("/").append(i).append(". ").append(service).append(".\n");
            i++;
        }
        return String.valueOf(stringBuilder);
    }

}