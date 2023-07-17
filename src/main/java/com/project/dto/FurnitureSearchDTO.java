package com.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FurnitureSearchDTO {

    private String searchBy;
    private String searchQuery = "";
}
