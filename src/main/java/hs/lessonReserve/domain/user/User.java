package hs.lessonReserve.domain.user;

import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@SuperBuilder
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String role;
    private String profileImageUrl;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<GatherUser> gatherUsers;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<GatherApply> gatherApplies;
    private String phone;
    private String address;
    private String postcode;

    private String provider;
    private String providerId;


    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

    public User(long id) {
        this.id = id;
    }
}
