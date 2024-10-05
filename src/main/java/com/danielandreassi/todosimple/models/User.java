package com.danielandreassi.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME)

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
    private List<Task> tasks = new ArrayList<Task>();


    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(groups = CreateUSer.class) @NotEmpty(groups = CreateUSer.class) @Size(min = 2, max = 100) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(groups = CreateUSer.class) @NotEmpty(groups = CreateUSer.class) @Size(min = 2, max = 100) String username) {
        this.username = username;
    }

    public @NotNull(groups = {CreateUSer.class, UpdateUser.class}) @NotEmpty(groups = CreateUSer.class) @Size(min = 8, max = 60) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(groups = {CreateUSer.class, UpdateUser.class}) @NotEmpty(groups = CreateUSer.class) @Size(min = 8, max = 60) String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj==null) return false;
        if (!(obj instanceof User)) return false;

        User other = (User) obj;
        if(this.id == null)
            if(other.id != null) return false;
            else if(!this.id.equals(other.id)) return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username) && Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
