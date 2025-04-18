package edu.java.helloworld.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Entity(name = "Role")
@Table(name = "tbl_role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends AbstractEntity<Integer> {
    String name;
    String description;

    @OneToMany(mappedBy = "role")
    Set<RoleHasPermission> permissions = new HashSet<>();

    @OneToMany(mappedBy = "role")
    Set<UserHasRole> roles = new HashSet<>();
}
