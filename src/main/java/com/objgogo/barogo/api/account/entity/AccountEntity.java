package com.objgogo.barogo.api.account.entity;

import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.common.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "account")
@Table(name = "account")
@Getter
@Setter
@ToString
public class AccountEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : this.roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @OneToMany(mappedBy = "account", targetEntity = OrderEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private List<OrderEntity> orderEntityList;

    @OneToMany(mappedBy = "account", targetEntity = DeliveryEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private List<DeliveryEntity> delivery;
}
