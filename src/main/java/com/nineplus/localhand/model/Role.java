package com.nineplus.localhand.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "ROLE")
@Table(name = "roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 3145254234350532564L;
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "ROLE_NAME", length = 20)
    private String roleName;

    @Size(max = 2000)
    @Column(name = "ROLE_DESCRIPTION", length = 2000)
    private String roleDescription;

    @Column(name = "CREATED_DATE")
    private Instant createdDate;

    @Size(max = 20)
    @Column(name = "CREATED_USER", length = 20)
    private String createdUser;

}