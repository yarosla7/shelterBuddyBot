package com.devpro.shelterBuddyBot.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// позже перенести в бд
public enum ShelterType {
    DOGS("Приют для собак"),
    CATS("Приют для кошек");

    private String name;

    // сравниваем то что пришло  с нашими енамками   ShelterType.values() позволяем получить массив всех енамак
    public static ShelterType getByName(String s) {
        for (ShelterType myVar : ShelterType.values()) {
            if (s.equals(myVar.name)) {
                return myVar;
            }
        }
        return null;
    }
}
