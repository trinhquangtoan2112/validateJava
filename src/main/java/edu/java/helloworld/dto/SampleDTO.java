package edu.java.helloworld.dto;

import java.io.Serializable;

import lombok.*;


//@Data   =@Settter + @Getter + @EqualsAndHashCode + @ToString

@Getter 
@ToString
@Builder

public class SampleDTO implements Serializable {
    private int id;
   // loai bo name
   @ToString.Exclude()
    private String name;

   /* @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SampleDTO other = (SampleDTO) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }*/
   
}

//Builder
// public class User {
//     private final String name;
//     private final int age;
//     private final String email;
//     private final String phone;
//     private final String address;

//     private User(UserBuilder builder) {
//         this.name = builder.name;
//         this.age = builder.age;
//         this.email = builder.email;
//         this.phone = builder.phone;
//         this.address = builder.address;
//     }

//     // Getter (Không có setter để đảm bảo immutable)
//     public String getName() { return name; }
//     public int getAge() { return age; }
//     public String getEmail() { return email; }
//     public String getPhone() { return phone; }
//     public String getAddress() { return address; }

//     // ✅ Class Builder
//     public static class UserBuilder {
//         private String name;
//         private int age;
//         private String email;
//         private String phone;
//         private String address;

//         public UserBuilder setName(String name) {
//             this.name = name;
//             return this;
//         }

//         public UserBuilder setAge(int age) {
//             this.age = age;
//             return this;
//         }

//         public UserBuilder setEmail(String email) {
//             this.email = email;
//             return this;
//         }

//         public UserBuilder setPhone(String phone) {
//             this.phone = phone;
//             return this;
//         }

//         public UserBuilder setAddress(String address) {
//             this.address = address;
//             return this;
//         }

//         // ✅ Phương thức build() để tạo đối tượng User
//         public User build() {
//             return new User(this);
//         }
//     }
// }