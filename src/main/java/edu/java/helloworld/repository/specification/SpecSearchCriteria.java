package edu.java.helloworld.repository.specification;
import lombok.Getter;
@Getter

public class SpecSearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private Boolean orPredicate;
    
    public SpecSearchCriteria(String key,SearchOperation searchOperation,Object value){
        super();
        this.key=key;
        this.operation=searchOperation;
        this.value= value;
    }
    public SpecSearchCriteria(String onPredicate,String key,SearchOperation searchOperation,Object value){
        super();
        this.key=key;
        this.operation=searchOperation;
        this.value= value;
        this.orPredicate=onPredicate !=null && onPredicate.equals("'");
    }
    public SpecSearchCriteria(String key,String operation,Object value,String prefix, String suffix){
         SearchOperation oper =SearchOperation.getSimpleOperation(operation.charAt(0));

         if(oper ==SearchOperation.EQUALITY){
            boolean startWithAsterrisk =prefix !=null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            boolean endWithAsterrisk =suffix !=null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            
            if(startWithAsterrisk && endWithAsterrisk){
                oper = SearchOperation.CONTAINS;
            }else if (startWithAsterrisk) {
                oper =SearchOperation.ENDS_WITH;
            }else if (endWithAsterrisk) {
                oper =SearchOperation.STARTS_WITH;
            }


         }
         this.key =key;
         this.operation= oper;
         this.value= value;
    }
}
