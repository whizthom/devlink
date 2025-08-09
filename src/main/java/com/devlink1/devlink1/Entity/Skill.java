package com.devlink1.devlink1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "skills", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @ManyToMany(mappedBy = "skills")
        private Set<User> users = new HashSet<>();

        // Prevent equals/hashCode from touching 'users' (lazy-loaded collection)
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Skill)) return false;
                Skill skill = (Skill) o;
                return id != null && id.equals(skill.id);
        }

        @Override
        public int hashCode() {
                return Objects.hashCode(id);
        }
}
