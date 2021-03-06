package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "gift_certificates")
public class Certificate extends BaseEntity {
    public static final String TAGS_ATTRIBUTE = "tags";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String DESCRIPTION_ATTRIBUTE = "description";
    public static final String CREATE_DATE_ATTRIBUTE = "createDate";

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @Column(name = "create_date", updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdateDate;

    @Column(name = "is_deleted", columnDefinition = "boolean default false", insertable = false)
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(name = "gift_certificates_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    public Certificate(String name, String description, double price, int duration) {
        super();
        this.name= name;
        this.description = description;
        this.price = BigDecimal.valueOf(price);
        this.duration =duration;
    }

    public Certificate(){
        super();
    }
}
