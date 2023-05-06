package com.devpro.shelterBuddyBot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// просто енамка, нужна для выдачи информации пользователю, вставляються в кнопки
public enum CallbackRequest {

    DOGS("DOG", "Приют для собак \uD83D\uDC36"),
    CATS("CAT", "Приют для кошек \uD83D\uDC31"),

    SHELTER_INFO("SHELTER_INFO", "Информация о приюте ℹ️"),
    GET_ANIMAL("GET_ANIMAL", "Как взять животное из приюта ❓"),
    REPORT_ANIMAL("REPORT_ANIMAL", "Прислать отчет о питомце \uD83D\uDCF7"),

    STEP_BACK_CHOOSING_SHELTER("STEP_BACK_CHOOSING_SHELTER", "\uD83D\uDD19"),
    STEP_BACK_INFO_SHELTER("STEP_BACK_CHOOSING_SHELTER", "\uD83D\uDD19"),
    HELP("HELP", "Позвать волонтера \uD83D\uDDE3"),

    GET_SHELTER_INFO("GET_SHELTER_INFO", "Рассказать о приюте \uD83D\uDDE3"),
    SHELTER_CONTACTS("SHELTER_CONTACTS", "Расписание работы, адрес, схема проезда \uD83D\uDD53"),
    PHONE_SECURITY("PHONE_SECURITY", "Контактные данные охраны для пропуска \uD83D\uDD10"),
    SAFETY_PRECAUTIONS("SAFETY_PRECAUTIONS", "Техника безопасности на территории приюта 👮‍♂"),
    PUT_MY_PHONE("PUT_MY_PHONE", "Записать контактные данные \uD83D\uDCF1"),

    INTRODUCTION_RULES("INTRODUCTION_RULES", "Правила знакомства с животным \uD83D\uDCD5"),
    NECESSARY_DOCUMENTS("NECESSARY_DOCUMENTS", "Документы для усыновления животного \uD83D\uDCD2"),
    TRANSPORTATION_RECOMMENDATIONS("TRANSPORTATION_RECOMMENDATIONS", "Рекомендации по транспортировке \uD83D\uDCD3"),
    ADULT_HOME_ARRANGEMENT("ADULT_HOME_ARRANGEMENT", "Обустройство дома для взрослого животного \uD83C\uDFE1"),
    HOME_ARRANGEMENT_WITH_LIMITED_ABILITY("HOME_ARRANGEMENT_WITH_LIMITED_ABILITY", "Обустройство дома для животного инвалида \uD83E\uDDAE"),
    REASONS_FOR_REFUSING_ANIMAL_TRANSFER("REASONS_FOR_REFUSING_ANIMAL_TRANSFER", "Почему могут отказать в усыновлении? \uD83D\uDEAB"),

    KITTEN_HOME_ARRANGEMENT("KITTEN_HOME_ARRANGEMENT", "Обустройство дома для котенка \uD83D\uDC31"),
    PUPPY_HOME_ARRANGEMENT("PUPPY_HOME_ARRANGEMENT", "Обустройство дома для щенка \uD83D\uDC36"),
    INITIAL_COMMUNICATION_WITH_DOG_ADVICE("INITIAL_COMMUNICATION_WITH_DOG_ADVICE", "Советы кинолога ❓"),
    BEST_DOG_HANDLERS("BEST_DOG_HANDLERS", "Лучшие кинологи \uD83D\uDC4D");


    private final String code;
    private final String name;

    public static CallbackRequest getValueByCode(String code) {
        for (CallbackRequest s : CallbackRequest.values()) {
            if (s.getCode().equals(code)) return s;
        }
        throw new IllegalArgumentException("Неизвестный тип CallbackRequest:" + code);
    }
}
