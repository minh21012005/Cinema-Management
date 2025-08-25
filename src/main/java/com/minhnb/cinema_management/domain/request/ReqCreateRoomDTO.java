package com.minhnb.cinema_management.domain.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqCreateRoomDTO {
    private String name;
    private Long cinemaId;
    private Long roomTypeId;
    private int rows;
    private int cols;
}
