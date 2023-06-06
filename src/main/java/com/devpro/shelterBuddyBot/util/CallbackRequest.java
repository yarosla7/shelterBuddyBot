package com.devpro.shelterBuddyBot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// просто енамка, нужна для выдачи информации пользователю, вставляються в кнопки
public enum CallbackRequest {

    DOGS("DOG", "\uD83D\uDC36Приют для собак"),
    CATS("CAT", "\uD83D\uDC31Приют для кошек"),

    SHELTER_INFO("SHELTER_INFO", "ℹИнформация о приюте"),
    GET_ANIMAL("GET_ANIMAL", "❓Как взять животное из приюта"),
    REPORT_ANIMAL("REPORT_ANIMAL", "\uD83D\uDCF7Прислать отчет о питомце"),


    STEP_BACK_CHOOSING_SHELTER("STEP_BACK_CHOOSING_SHELTER", "\uD83D\uDD19"),
    STEP_BACK_INFO_SHELTER("STEP_BACK_CHOOSING_SHELTER", "\uD83D\uDD19"),
    HELP("HELP", "\uD83D\uDCACПозвать волонтера"),

    GET_SHELTER_INFO("GET_SHELTER_INFO", "\uD83D\uDDE3Рассказать о приюте"),
    SHELTER_CONTACTS("SHELTER_CONTACTS", "\uD83D\uDD53Расписание работы, адрес, схема проезда"),
    PHONE_SECURITY("PHONE_SECURITY", "\uD83D\uDD10Контактные данные охраны для пропуска"),
    SAFETY_PRECAUTIONS("SAFETY_PRECAUTIONS", "👮‍♂Техника безопасности на территории приюта"),
    PUT_MY_PHONE("PUT_MY_PHONE", "\uD83D\uDCF1Записать контактные данные"),

    INTRODUCTION_RULES("INTRODUCTION_RULES", "\uD83D\uDCD5Правила знакомства с животным"),
    NECESSARY_DOCUMENTS("NECESSARY_DOCUMENTS", "\uD83D\uDCD2Документы для усыновления животного"),
    TRANSPORTATION_RECOMMENDATIONS("TRANSPORTATION_RECOMMENDATIONS", "\uD83D\uDCD3Рекомендации по транспортировке"),
    ADULT_HOME_ARRANGEMENT("ADULT_HOME_ARRANGEMENT", " \uD83C\uDFE1Обустройство дома для взрослого животного"),
    HOME_ARRANGEMENT_WITH_LIMITED_ABILITY("HOME_ARRANGEMENT_WITH_LIMITED_ABILITY", "\uD83E\uDDAEОбустройство дома для животного инвалида"),
    REASONS_FOR_REFUSING_ANIMAL_TRANSFER("REASONS_FOR_REFUSING_ANIMAL_TRANSFER", "\uD83D\uDEABПочему могут отказать в усыновлении?"),

    KITTEN_HOME_ARRANGEMENT("KITTEN_HOME_ARRANGEMENT", "\uD83D\uDC31Обустройство дома для котенка "),
    PUPPY_HOME_ARRANGEMENT("PUPPY_HOME_ARRANGEMENT", "\uD83D\uDC36Обустройство дома для щенка"),
    INITIAL_COMMUNICATION_WITH_DOG_ADVICE("INITIAL_COMMUNICATION_WITH_DOG_ADVICE", "❓Советы кинолога"),
    BEST_DOG_HANDLERS("BEST_DOG_HANDLERS", "\uD83D\uDC4DЛучшие кинологи"),

    SHOW_REPORTS("SHOW_REPORTS", "\uD83D\uDCD3Посмотреть отчеты"),
    COUNT_REPORTS("COUNT_REPORTS", "Колличество отчетов"),
    REPORT_OK("REPORT_OK", "✅Отчет впорядке"),
    REPORT_NOT_OK("REPORT_NOT_OK", "❌Проблемы с отчетом"),

    SHOW_ANIMALS("SHOW_ANIMALS", "\uD83D\uDC64\uD83E\uDDAEПретенденты на усыновление"),
    COUNT_ANIMALS("COUNT_ANIMALS", "Колличество претендентов"),
    ANIMAL_ADOPTED("ANIMAL_ADOPTED", "✅Подтвердить усыновление"),
    ANIMAL_NOT_ADOPTED("ANIMAL_NOT_ADOPTED", "❌Отказать в усыновлении"),
    ANIMAL_EXTEND_PERIOD_14("ANIMAL_EXTEND_PERIOD_14", "⏳Продлить испытательный срок на 14 дней"),
    ANIMAL_EXTEND_PERIOD_30("ANIMAL_EXTEND_PERIOD_30", "⏳Продлить испытательный срок на 30 дней"),

    MESSAGE_REPORT_OK("MESSAGE_REPORT_OK","✅Ваш отчет проверили, все отлично!"),
    MESSAGE_REPORT_NOT_OK("MESSAGE_REPORT_NOT_OK","Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пришлите отчет за сегодня еще раз. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного");


    private final String code;
    private final String name;


    public static CallbackRequest getValueByCode(String code) {
        for (CallbackRequest s : CallbackRequest.values()) {
            if (s.getCode().equals(code)) return s;
        }
        throw new IllegalArgumentException("Неизвестный тип CallbackRequest:" + code);
    }
}
