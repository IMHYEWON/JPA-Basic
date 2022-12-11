package hellojpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Member {

    @Id
	// 식별자 전략 (1) Identity 전략
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(insertable = true   //등록 가능 여부 (default = true)
//        , updatable = false     //변경 가능 여부
//        , nullable = false      //null값 허용 여부
//        , unique = true       //유니크 제약조건 여부, @Table에서도 설정 가능
//        , length = 100          // 길이 설정
//        // 컬럼 정의 직접할 때
//        , columnDefinition = "varchar(100) default 'EMPTY'"
//        )
    private String name;

    private Integer age;

    // Enum 타입 매핑
    // EnumType.ORDINAL : enum 순서(숫자형)를 DB에 저장 (Default)
    // => 사용 지양!!! (순서가 바뀔 수 있으므로)
    // EnumType.STRING : enum 이름을 DB에 저장
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // DB 시간 타입 (DATE, TIME, TIMESTAMP)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // VARCHAR 이상의 컨텐츠
    @Lob
    private String description;

    // 테이블에 생성하지 않는 컬럼
    @Transient
    private int temp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Member() {
    }

    public Member(Long id) {
        this.id = id;
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
