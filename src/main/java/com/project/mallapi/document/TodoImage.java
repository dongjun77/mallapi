package com.project.mallapi.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoImage {

    private String fileName;

    @Indexed
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }

}
