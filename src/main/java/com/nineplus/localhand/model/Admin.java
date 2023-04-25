package com.nineplus.localhand.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "ADMIN")
@Table(name = "admins")
public class Admin implements Serializable {
    private static final long serialVersionUID = 4653592076301838665L;
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    @Id
    private Long id;

    @Size(max = 500)
    @Column(name = "name", length = 500)
    private String name;

    @Size(max = 500)
    @NotNull
    @Column(name = "email", nullable = false, length = 500)
    private String email;

    @Size(max = 500)
    @NotNull
    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Size(max = 100)
    @Column(name = "remember_token", length = 100)
    private String rememberToken;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}