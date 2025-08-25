package com.minhnb.cinema_management.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResRoomDTO {
    private long id;
    private String name;
    private Type type;
    private boolean active;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Type{
        private long id;
        private String name;
    }
}
