package com.project.mallapi.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoImage {

    private String fileName;
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }

}
