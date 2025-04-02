package edu.java.helloworld.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import edu.java.helloworld.dto.response.page.PageRespones;
import edu.java.helloworld.model.Address;
import edu.java.helloworld.model.User;
import edu.java.helloworld.repository.criteria.SearchCriteria;
import edu.java.helloworld.repository.criteria.UserSearchCriteriaQueryConsumer;
import edu.java.helloworld.repository.specification.*;
import edu.java.helloworld.util.AppConst;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PageRespones<?> getAllUserWithColoumandSearch(int pageSize, int pageNo, String search, String sortBy) {
        // edu.java.helloworld.dto.response.Repository
        StringBuilder sqlBuilder = new StringBuilder(
                "select new edu.java.helloworld.dto.response.Repository.UserDetailResponse(u.firstName,u.lastName,u.phone) from User u where 1=1");
        // StringBuilder sqlBuilder = new StringBuilder("select u from User u where
        // 1=1");

        if (StringUtils.hasLength(search)) {
            sqlBuilder.append(" AND lower(u.firstName) like lower(:firstName)");
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {

                sqlBuilder.append(String.format(" order by u.%s %s", matcher.group(1), matcher.group(3)));

            }

        }

        Query selectQuery = entityManager.createQuery(sqlBuilder.toString());
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("firstName", String.format("%%%s%%", search));
        }
        List user = selectQuery.getResultList();

        // query ra list user
        // query ra record
        StringBuilder sqlCountQuery = new StringBuilder("select count(*) from User u where 1=1");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append("AND lower(u.firstName) like lower(?1)");
        }

        Query selectedCountQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            selectedCountQuery.setParameter(1, String.format("%%%s%%", search));
        }
        Long countQueryResult = (Long) selectedCountQuery.getSingleResult();
        Page<?> page = new PageImpl<Object>(user, PageRequest.of(pageNo, pageSize), countQueryResult);
        return PageRespones.builder().pageNo(pageNo).pageSize(pageSize).totalPage(page.getTotalPages())
                .item(page.stream().toList()).build();
    }

    public PageRespones<?> advancedSearch(int pageSize, int pageNo, String sortBy, String address, String... search) {

        List<SearchCriteria> criteriaList = new ArrayList<>();
        // 1. ;ay danh sach user
        if (search != null) {
            for (String s : search) {

                Pattern pattern = Pattern.compile("(\\w+?)(:|>|<)(.*)");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        List<User> user = getUsers(pageNo, pageSize, criteriaList, sortBy, address);
        Long totalElement = getTotalElement(criteriaList, address);
        return PageRespones.builder().pageNo(pageNo).pageSize(pageSize).totalPage(totalElement.intValue()).item(user)
                .build();
    }

    private Long getTotalElement(List<SearchCriteria> criteriaList, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaQueryConsumer queryConsumer = new UserSearchCriteriaQueryConsumer(criteriaBuilder, predicate,
                root);

        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        if (StringUtils.hasLength(address)) {
            Join<Address, User> addressJoinUser = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.like(addressJoinUser.get("city"), "%" + address + "%");
            query.where(predicate, addressPredicate);
        } else {
            query.where(predicate);
        }
        return entityManager.createQuery(query).getSingleResult();

    }

    private List<User> getUsers(int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy,
            String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaQueryConsumer queryConsumer = new UserSearchCriteriaQueryConsumer(criteriaBuilder, predicate,
                root);
        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate();
        if (StringUtils.hasLength(address)) {
            Join<Address, User> addressJoinUser = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.like(addressJoinUser.get("city"), "%" + address + "%");
            query.where(predicate, addressPredicate);
        } else {
            query.where(predicate);
        }
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("desc")) {
                    query.orderBy(criteriaBuilder.desc(root.get(columnName)));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get(columnName)));
                }

            }
        }
        return entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
    }

    public PageRespones getUsersSpecification(Pageable pageable, String[] address, String[] user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Join<Address, User> userAddressJoin = root.join("addresses");
        // Build query
        List<Predicate> userPre = new ArrayList<>();
        List<Predicate> addressPredicates = new ArrayList<>();
        Pattern pattern = Pattern.compile(AppConst.SEARCH_SPEC_OPERATOR);
        for (String s : user) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                SpecSearchCriteria criteria = new SpecSearchCriteria(matcher.group(1), matcher.group(2),
                        matcher.group(3), matcher.group(4), matcher.group(5));
                Predicate predicate = toPredicateUser(root, criteriaBuilder, criteria);
                userPre.add(predicate);
            }
        }

        for (String a : address) {
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                SpecSearchCriteria criteria = new SpecSearchCriteria(matcher.group(1), matcher.group(2),
                        matcher.group(3), matcher.group(4), matcher.group(5));
                Predicate predicate = toPredicateAddress(userAddressJoin, criteriaBuilder, criteria);
                addressPredicates.add(predicate);
            }
        }
        Predicate userPredicate = criteriaBuilder.and(userPre.toArray(new Predicate[0]));
        Predicate addressPredicate = criteriaBuilder.and(addressPredicates.toArray(new Predicate[0]));
        Predicate finalPredicate = criteriaBuilder.and(userPredicate, addressPredicate);

        query.where(finalPredicate);

        List<User> list = entityManager.createQuery(query).getResultList();
        long count = count(address, user);
        return PageRespones.builder().pageNo(pageable.getPageNumber()).pageSize(pageable.getPageSize()).totalPage(count)
                .item(list).build();
    }

    private long count(String[] address, String[] user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        Join<Address, User> userAddressJoin = root.join("addresses");
        // Build query
        List<Predicate> userPre = new ArrayList<>();
        List<Predicate> addressPredicates = new ArrayList<>();
        Pattern pattern = Pattern.compile(AppConst.SEARCH_SPEC_OPERATOR);
        for (String s : user) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                SpecSearchCriteria criteria = new SpecSearchCriteria(matcher.group(1), matcher.group(2),
                        matcher.group(3), matcher.group(4), matcher.group(5));
                Predicate predicate = toPredicateUser(root, criteriaBuilder, criteria);
                userPre.add(predicate);
            }
        }

        for (String a : address) {
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                SpecSearchCriteria criteria = new SpecSearchCriteria(matcher.group(1), matcher.group(2),
                        matcher.group(3), matcher.group(4), matcher.group(5));
                Predicate predicate = toPredicateAddress(userAddressJoin, criteriaBuilder, criteria);
                addressPredicates.add(predicate);
            }
        }
        Predicate userPredicate = criteriaBuilder.or(userPre.toArray(new Predicate[0]));
        Predicate addressPredicate = criteriaBuilder.or(addressPredicates.toArray(new Predicate[0]));
        Predicate finalPredicate = criteriaBuilder.and(userPredicate, addressPredicate);
        query.select(criteriaBuilder.count(root));

        query.where(finalPredicate);

        return entityManager.createQuery(query).getSingleResult();

    }

    public Predicate toPredicateUser(Root<User> root, CriteriaBuilder builder, SpecSearchCriteria criteria) {

        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN ->
                builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN ->
                builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE ->
                builder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
            case STARTS_WITH -> builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        };

    }

    public Predicate toPredicateAddress(Join<Address, User> root, CriteriaBuilder builder,
            SpecSearchCriteria criteria) {

        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN ->
                builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN ->
                builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE ->
                builder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
            case STARTS_WITH -> builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        };

    }
}
