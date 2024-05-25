package sehmus.school_management_system.models.concretes;

import jakarta.persistence.*;
import lombok.*;
import sehmus.school_management_system.models.enums.RoleType;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String roleName;
}
