package com.danielandreassi.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    public interface CreateUSer{}
    public interface UpdateUser{}

    public static final String TABLE_NAME = "User";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateUSer.class)
    @NotEmpty(groups = CreateUSer.class)
    @Size(min = 2, max = 100)
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreateUSer.class, UpdateUser.class})
    @NotEmpty(groups = CreateUSer.class)
    @Size(min = 8, max = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>();
}
