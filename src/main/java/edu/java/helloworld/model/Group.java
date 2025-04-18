package edu.java.helloworld.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Group")
@Table(name = "tbl_group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends AbstractEntity<Integer> {
    String name;
    String description;

    @OneToOne
    Role role;

    @OneToMany(mappedBy = "group")
    Set<GroupHasUser> groupHasUsers = new HashSet<>();
}
