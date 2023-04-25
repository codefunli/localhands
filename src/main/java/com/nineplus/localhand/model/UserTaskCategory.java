package com.nineplus.localhand.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "USER_TASK_CATEGORY")
@Table(name = "user_task_category")
public class UserTaskCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 6666666943624672386L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

}
