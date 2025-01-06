package kr.hhplus.be.server.concert.interfaces.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class ConcertAllRequest {

    @NotBlank
    @Min(1)
    private Long memberId;

    private Date startDate;
}
