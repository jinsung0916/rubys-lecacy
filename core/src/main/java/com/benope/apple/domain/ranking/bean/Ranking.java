package com.benope.apple.domain.ranking.bean;

import com.benope.apple.config.domain.AbstractDomain;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "ranking")
@Where(clause = NOT_DELETED_CLAUSE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ranking_type_cd")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Ranking extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankingNo;
    private Long categoryNo;
    @Enumerated(EnumType.STRING)
    @Column(name = "ranking_type_cd", insertable = false, updatable = false)
    private RankingTypeCd rankingTypeCd;
    @Column(name = "object_no", insertable = false, updatable = false)
    private Long objectNo;
    private LocalDate rankDate;
    private @Setter Long rankNum;

    @Override
    protected void beforeCreate() {
        validate();
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(rankDate);
        Assert.notNull(rankNum);
    }

}
