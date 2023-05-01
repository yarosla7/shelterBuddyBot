package com.devpro.shelterBuddyBot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// просто енамка, нужна для выдачи информации пользователю, вставляються в кнопки, позже перенести в бд
public enum CallbackRequest {

    DOGS("Приют для собак"),
    CATS("Приют для кошек"),

    SHELTER_INFO("Узнать информацию о приюте"),
    GET_ANIMAL("Какие документы нужны чтобы забрать животное"),
    REPORT_ANIMAL("Прислать отчет о питомце"),

    HELP("Позвать волонтера"),

    GET_SHELTER_INFO("рассказать о приюте"),
    SHELTER_CONTACTS("расписание работы приюта и адрес, схему проезда"),
    PHONE_SECURITY("контактные данные охраны для оформления пропуска на машину"),
    SAFETY_PRECAUTIONS("общие рекомендации о технике безопасности на территории приюта"),
    PUT_MY_PHONE("записать контактные данные для связи");

    private String name;
}
