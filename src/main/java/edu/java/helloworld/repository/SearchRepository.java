package edu.java.helloworld.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgresql.translation.messages_tr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import edu.java.helloworld.dto.response.page.PageRespones;
import edu.java.helloworld.model.Address;
import edu.java.helloworld.model.User;
import edu.java.helloworld.repository.criteria.SearchCriteria;
import edu.java.helloworld.repository.criteria.UserSearchCriteriaQueryConsumer;
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

     public PageRespones<?> getAllUserWithColoumandSearch(int pageSize, int pageNo,String search, String sortBy){
        //edu.java.helloworld.dto.response.Repository
        StringBuilder sqlBuilder = new StringBuilder("select new edu.java.helloworld.dto.response.Repository.UserDetailResponse(u.firstName,u.lastName,u.phone) from User u where 1=1");
    //  StringBuilder sqlBuilder = new StringBuilder("select u from User u where 1=1");

        if(StringUtils.hasLength(search)){
            sqlBuilder.append(" AND lower(u.firstName) like lower(:firstName)");
        }

          if(StringUtils.hasLength(sortBy)){
          Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
          Matcher matcher = pattern.matcher(sortBy);
          if(matcher.find()){
              
                sqlBuilder.append(String.format(" order by u.%s %s", matcher.group(1),matcher.group(3)));
              
          }
        
      }
System.out.println(sqlBuilder);

        Query selectQuery = entityManager.createQuery(sqlBuilder.toString());
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        if(StringUtils.hasLength(search)){
            selectQuery.setParameter("firstName",String.format("%%%s%%", search) );
        }
        System.out.println(sqlBuilder.toString());
        List user = selectQuery.getResultList();


        //query ra list user 
        //query ra record
            StringBuilder sqlCountQuery =   new StringBuilder("select count(*) from User u where 1=1");
            if(StringUtils.hasLength(search)){
                sqlCountQuery.append("AND lower(u.firstName) like lower(?1)");
            }

           
            Query selectedCountQuery = entityManager.createQuery(sqlCountQuery.toString());
            if(StringUtils.hasLength(search)){
                selectedCountQuery.setParameter(1,String.format("%%%s%%", search) );
            }
            Long countQueryResult = (Long) selectedCountQuery.getSingleResult();
System.out.println(countQueryResult);
Page<?> page = new PageImpl<Object>(user,PageRequest.of(pageNo, pageSize),countQueryResult);
        return PageRespones.builder().pageNo(pageNo).pageSize(pageSize).totalPage(page.getTotalPages()).item(page.stream().toList()).build();
     }

public PageRespones<?> advancedSearch(int pageSize,int pageNo, String sortBy,String address, String ...search){

    List<SearchCriteria> criteriaList = new ArrayList<>();
    //1. ;ay danh sach user 
    if(search !=null){
        for(String s: search){
          
            Pattern pattern= Pattern.compile("(\\w+?)(:|>|<)(.*)");
            Matcher matcher =pattern.matcher(s);
            if(matcher.find()){
criteriaList.add(new SearchCriteria(matcher.group(1),matcher.group(2),matcher.group(3)));
            }
        }
    }

    List<User> user = getUsers(pageNo,pageSize,criteriaList,sortBy,address);
   
    return PageRespones.builder().pageNo(pageNo).pageSize(pageSize).totalPage(0).item(user).build();
}
private List<User> getUsers(int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy,String address) {
   CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
   CriteriaQuery<User> query =criteriaBuilder.createQuery(User.class);
   Root<User> root = query.from(User.class);
    
   Predicate predicate = criteriaBuilder.conjunction();
     UserSearchCriteriaQueryConsumer  queryConsumer = new UserSearchCriteriaQueryConsumer(criteriaBuilder,predicate,root);
   System.out.println(address+"1212441241");
   criteriaList.forEach(queryConsumer);
   predicate= queryConsumer.getPredicate();
     if(StringUtils.hasLength(address)){
        Join<Address,User> addressJoinUser = root.join("addresses");
        Predicate addressPredicate =criteriaBuilder.like(addressJoinUser.get("city"), "%"+address+"%");
        query.where(predicate,addressPredicate);    
    }else{
     
      
        query.where(predicate);
    }
  

     if(StringUtils.hasLength(sortBy)){
        Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
        Matcher matcher= pattern.matcher(sortBy);
        if(matcher.find()){
            String columnName =matcher.group(1);
            if(matcher.group(3).equalsIgnoreCase("desc")){
                query.orderBy(criteriaBuilder.desc(root.get(columnName)));
            }else{
                query.orderBy(criteriaBuilder.asc(root.get(columnName)));
            }
         
       
        }
     }
      return entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList(); 
}
}
