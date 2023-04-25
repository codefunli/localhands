package com.nineplus.localhand.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "COMMONSTATE")
@Table(name = "common_states")
public class CommonState implements Serializable {
    private static final long serialVersionUID = 2469740459588097996L;
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 40)
    private String name;

    @Size(max = 2)
    @NotNull
    @Column(name = "ABBREV", nullable = false, length = 2)
    private String abbrev;

}