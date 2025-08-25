package com.minhnb.cinema_management.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqRoomDTO {
    private long id;
    private String name;
    private long typeId;
}
