package com.softage.task.models

import org.hibernate.annotations.Type

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ImageModel implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id

    @Type( type = 'uuid-char' )
    @Column( name = 'uuid', nullable = false, unique = true )
    private UUID uuid

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    UUID getUuid() {
        return uuid
    }

    void setUuid(UUID uuid) {
        this.uuid = uuid
    }
}
