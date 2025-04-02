package edu.java.helloworld.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import edu.java.helloworld.model.User;

public class UserSpecitificationBuilder {

    public final List<SpecSearchCriteria> param;

    public UserSpecitificationBuilder() {
        param = new ArrayList<>();
    }

    public UserSpecitificationBuilder(List<SpecSearchCriteria> param) {
        this.param = param;
    }

    public UserSpecitificationBuilder with(String key, String operation, Object value, String prefix, String suffix) {
        with(null, key, operation, value, prefix, suffix);
        return this;
    }

    public UserSpecitificationBuilder with(String onPredicate, String key, String operation, Object value,
            String prefix, String suffix) {
        SearchOperation oper = SearchOperation.getSimpleOperation(operation.charAt(0));

        if (oper == SearchOperation.EQUALITY) {
            boolean startWithAsterrisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            boolean endWithAsterrisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

            if (startWithAsterrisk && endWithAsterrisk) {
                oper = SearchOperation.CONTAINS;
            } else if (startWithAsterrisk) {
                oper = SearchOperation.ENDS_WITH;
            } else if (endWithAsterrisk) {
                oper = SearchOperation.STARTS_WITH;
            }
        }

        param.add(new SpecSearchCriteria(onPredicate, key, oper, value));
        return this;

    }

    public Specification<User> build() {
        if (param.isEmpty())
            return null;
        Specification<User> specification = new UserSpecitification(param.get(0));

        for (int i = 1; i < param.size(); i++) {
            specification = param.get(i).getOrPredicate()
                    ? Specification.where(specification).or(new UserSpecitification(param.get(i)))
                    : Specification.where(specification).and(new UserSpecitification(param.get(i)));
        }
        return specification;
    }
}
