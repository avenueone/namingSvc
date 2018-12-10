package org.avenue1.naming.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.avenue1.naming.domain.enumeration.EntityTypeEnum;

/**
 * A Names.
 */
@Document(collection = "names")
public class Names implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("entity_type")
    private EntityTypeEnum entityType;

    @NotNull
    @Size(min = 4)
    @Field("name")
    private String name;

    @Field("created")
    private LocalDate created;

    @NotNull
    @Field("format")
    private String format;

    @Field("prefix")
    private String prefix;

    @Field("suffix")
    private String suffix;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityTypeEnum getEntityType() {
        return entityType;
    }

    public Names entityType(EntityTypeEnum entityType) {
        this.entityType = entityType;
        return this;
    }

    public void setEntityType(EntityTypeEnum entityType) {
        this.entityType = entityType;
    }

    public String getName() {
        return name;
    }

    public Names name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Names created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getFormat() {
        return format;
    }

    public Names format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPrefix() {
        return prefix;
    }

    public Names prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public Names suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Names names = (Names) o;
        if (names.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), names.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Names{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", format='" + getFormat() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", suffix='" + getSuffix() + "'" +
            "}";
    }
}
