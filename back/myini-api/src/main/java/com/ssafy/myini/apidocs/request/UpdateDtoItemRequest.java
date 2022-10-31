package com.ssafy.myini.apidocs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDtoItemRequest {
    private String dtoItemName;
    private Long dtoClassType;
    private Long dtoPrimitiveType;
    private String dtoIsList;
}
