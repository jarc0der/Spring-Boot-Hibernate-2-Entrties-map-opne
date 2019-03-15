package com.softage.task.models

import javax.persistence.*

@Entity
@Table( name = 'article_image' )
class ArticleImageModel implements Comparable<ArticleImageModel>{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = 'native' )
    private Long id

    @OneToOne( cascade = [CascadeType.ALL] )
    private ImageModel image

    @ManyToOne( optional = true )
    @JoinColumn( name = 'parent_id', nullable = true )
    private ArticleModel parent

    ArticleModel getParent() {
        return parent
    }

    void setParent(ArticleModel parent) {
        this.parent = parent
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        ArticleImageModel that = (ArticleImageModel) o

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

    @Override
    int compareTo(ArticleImageModel o) {
        new Random().nextBoolean() ? 1 : 0
    }
}
