package hello.jpa;
import hello.jpa.RoleType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
//@Table(uniqueConstraints = )
public class Member {
    @Id
    private Long id;
    @Column(name = "name",
            insertable = true, updatable = false, // 컬럼 수정시 db 반영 ?
            nullable = false, // not null
            unique = true, // 잘안씀 이름이 random으로 생성되서 -> @Table(uniqueConstraints = )
            length = 10,
            columnDefinition = "varchar(100) default 'EMPTY'"
    )
    private String username;
    private Integer age;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL 절대 사용금지
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate testLocalDate; // date
    private LocalDateTime testLocalDateTime; // timestamp

    @Lob // 속성 X, 매핑하는 필드 타입이 문자면 CLOB, 나머지는 BLOB
    private String description;
    
    @Transient // db와 관련없는 컬럼 (메모리상에서만 사용)
    private int temp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}