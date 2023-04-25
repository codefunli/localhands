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
@Entity(name = "COMMONCOUNTRY")
@Table(name = "common_countries")
public class CommonCountry implements Serializable {
    private static final long serialVersionUID = 4216202301412338483L;
    @Id
    @Size(max = 2)
    @Column(name = "ISO", nullable = false, length = 2)
    private String id;

    @Size(max = 80)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 80)
    private String name;

    @Size(max = 80)
    @NotNull
    @Column(name = "PRINTABLE_NAME", nullable = false, length = 80)
    private String printableName;

    @Size(max = 3)
    @Column(name = "ISO3", length = 3)
    private String iso3;

    @Column(name = "NUMCODE")
    private Short numcode;

}