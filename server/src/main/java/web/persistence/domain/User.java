package web.persistence.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import common.enums.Role;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

// @author: Titov Mykhaylo (titov) 20.06.13 17:22
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint( name = "name", columnNames = "name"),
        @UniqueConstraint( name = "activationToken", columnNames = "activationToken")
})
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(exclude = {"authorities", "clients"})
@Setter
@Getter
@SuppressFBWarnings({"EQ_DOESNT_OVERRIDE_EQUALS", "UCPM_USE_CHARACTER_PARAMETERIZED_METHOD"})
public class User implements Serializable {

    private static final long serialVersionUID = -8771610125938886166L;

    @Id
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "users_id_seq_gen")
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @NonNull
    private String name;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "primaryEmailAddressId")
    private EmailAddress primaryEmailAddress;

    @OneToMany(mappedBy = "user", cascade = ALL)
    @NonNull
    private List<EmailAddress> emailAddresses = new ArrayList<>();

    @NonNull
    @NotBlank
    @RestResource(exported = false)
    private String token;

    @NonNull
    private String lastSignInIpAddress;

    @NonNull
    private LocalDateTime lastSignInDateTime;

    @NonNull
    private String activationToken;

    private boolean activated;

    private boolean blocked;

    @OneToMany(mappedBy = "user")
    @NonNull
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = ALL)
    @NonNull
    private List<Authority> authorities = Lists.newArrayList(new Authority(this, Role.USER));

    @Setter(AccessLevel.PRIVATE)
    @Version
    private long version;

    public User(@NonNull String name, @NonNull String token){
        Preconditions.checkArgument(StringUtils.hasText(name));
        Preconditions.checkArgument(StringUtils.hasText(token));
        this.name = name;
        this.token = token;
    }

    public User setName(@NonNull String name){
        Preconditions.checkArgument(StringUtils.hasText(name));
        this.name = name;
        return this;
    }

    public User setToken(@NonNull String token){
        Preconditions.checkArgument(StringUtils.hasText(token));
        this.token = token;
        return this;
    }

    public List<Client> getClients(){
        return Collections.unmodifiableList(clients);
    }

    public User add(@NonNull Client client){
        this.clients.add(client);
        return this;
    }

    public List<Authority> getAuthorities(){
        return Collections.unmodifiableList(authorities);
    }

    public User add(@NonNull Authority authority){
        this.authorities.add(authority);
        return this;
    }
}
