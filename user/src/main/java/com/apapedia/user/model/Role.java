package com.apapedia.user.model;

// import java.io.Serializable;
// import java.util.List;

// import org.hibernate.annotations.OnDelete;
// import org.hibernate.annotations.OnDeleteAction;
// import java.util.UUID;

// import com.fasterxml.jackson.annotation.JsonIgnore;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import jakarta.validation.constraints.NotNull;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Setter
// @Getter
// @AllArgsConstructor
// @NoArgsConstructor
// @Entity
// @Table(name = "role")
// public class Role implements Serializable {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private UUID id;

//     @NotNull
//     @Column(name = "role", nullable = false)
//     private String role;

//     @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
//     @OnDelete(action = OnDeleteAction.CASCADE)
//     @JsonIgnore
//     private List<UserModel> users;
// }

public enum Role{
    SELLER, CUSTOMER
}

