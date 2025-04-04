package edu.java.helloworld.repository.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key;//firstname, lastname,id ,email,....
    private String operation;//= > <
    private Object value;
}
