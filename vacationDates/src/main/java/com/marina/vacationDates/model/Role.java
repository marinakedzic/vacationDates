package com.marina.vacationDates.model;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {
        @Id @GeneratedValue(strategy=GenerationType.AUTO)
        private Integer id;
        @Column(nullable=false, unique=true)
        private String name;

        @ManyToMany(mappedBy="roles", fetch = FetchType.EAGER)
        private List<User> users;

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public List<User> getUsers() {

        return users;
    }

        public void setUsers(List<User> users) {
        this.users = users;
    }

}
