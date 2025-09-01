package com.minhnb.cinema_management.domain.request;

import com.minhnb.cinema_management.domain.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqSeatDTO {
    private int row;
    private int col;
    private long typeId;
    private long roomId;
}
