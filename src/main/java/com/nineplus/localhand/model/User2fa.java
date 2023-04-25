package com.nineplus.localhand.model;

import lombok.*;

import javax.persistence.*;
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
@Entity(name = "USER2FA")
@Table(name = "user_2fa")
public class User2fa implements Serializable {
    private static final long serialVersionUID = -2600812655975287651L;
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Size(max = 20)
    @NotNull
    @Column(name = "PIN", nullable = false, length = 20)
    private String pin;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    private Boolean status = false;

    @NotNull
    @Lob
    @Column(name = "RESPONSE", nullable = false)
    private String response;

    @Lob
    @Column(name = "RECEIPT")
    private String receipt;

    @NotNull
    @Column(name = "DATE_CREATED", nullable = false)
    private Instant dateCreated;

}