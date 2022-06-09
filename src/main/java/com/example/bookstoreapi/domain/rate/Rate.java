package com.example.bookstoreapi.domain.rate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class Rate {

    private LocalDateTime createdDate;
    private Long memberId;
    private Long movieId;
    private Integer point;

    //reusability'e katkı sağlar
    //test etmeyi kolaylaştırır
    //kural değişirse daha kolay refactor edebiliriz
    //iş kurallarıyla ilgili metotları domain modellerde, teknoloji spesifik konuları adapter modeller üzerinde tutuyoruz
    // https://martinfowler.com/bliki/TellDontAsk.html
    // https://dev.to/crovitz/have-you-anemic-or-rich-domain-model-2ala
    public boolean isStillValid() {
        return createdDate.isAfter(LocalDateTime.of(2022,1,1,0,0,0));
    }
}
