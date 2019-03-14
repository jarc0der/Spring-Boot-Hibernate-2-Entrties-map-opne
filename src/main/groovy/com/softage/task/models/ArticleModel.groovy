package com.softage.task.models

import javax.persistence.*

@Entity
@Table( name = 'article' )
class ArticleModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = 'native' )
    private Long id

    @OneToOne( cascade = [CascadeType.ALL], orphanRemoval = true )
//    @JoinTable( name = 'article_image', joinColumns = @JoinColumn ( name = 'article_id' ),
//    inverseJoinColumns = @JoinColumn( name = 'image_uuid', referencedColumnName = 'uuid', nullable = true ) )
    private ImageModel image


    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        ArticleModel that = (ArticleModel) o

        if (id != that.id) return false
        if (image != that.image) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (image != null ? image.hashCode() : 0)
        return result
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    ImageModel getImage() {
        return image
    }

    void setImage(ImageModel image) {
        this.image = image
    }
}
