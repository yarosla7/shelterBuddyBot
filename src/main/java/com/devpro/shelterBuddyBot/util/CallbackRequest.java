package com.devpro.shelterBuddyBot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// просто енамка, нужна для выдачи информации пользователю, вставляються в кнопки
public enum CallbackRequest {

    DOGS("DOG", "Приют для собак \uD83D\uDC36"),
    CATS("CAT", "Приют для кошек \uD83D\uDC31"),

    SHELTER_INFO("SHELTER_INFO", "Информация о приюте"),
    GET_ANIMAL("GET_ANIMAL", "Как взять животное из приюта"),
    REPORT_ANIMAL("REPORT_ANIMAL", "Прислать отчет о питомце"),

    HELP("HELP", "Позвать волонтера"),

    GET_SHELTER_INFO("GET_SHELTER_INFO", "Рассказать о приюте"),
    SHELTER_CONTACTS("SHELTER_CONTACTS", "Расписание работы, адрес, схема проезда"),
    PHONE_SECURITY("PHONE_SECURITY", "Контактные данные охраны для пропуска"),
    SAFETY_PRECAUTIONS("SAFETY_PRECAUTIONS", "Рекомендации о технике безопасности на территории приюта"),
    PUT_MY_PHONE("PUT_MY_PHONE", "Записать контактные данные");

    private final String code;
    private final String name;

    public static CallbackRequest getValueByCode(String code) {
        for (CallbackRequest s : CallbackRequest.values()) {
            if (s.getCode().equals(code)) return s;
        }
        throw new IllegalArgumentException("Неизвестный тип CallbackRequest:" + code);
    }
}
