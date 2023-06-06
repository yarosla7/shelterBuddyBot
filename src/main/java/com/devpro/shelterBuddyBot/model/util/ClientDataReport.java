package com.devpro.shelterBuddyBot.model.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "client_data_report")
//бд для получения отчета с гугл форм, потом переписывается все в обычный отчет
public class ClientDataReport {

    @Id
    private Integer idReport;

    private Integer clientId;

    private String photoLink;

    private String ration;

    private String wellBeing;

    private String behaviorChanges;

    private Boolean processed;
}
