package com.softage.task.models

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.SortNatural

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

import static org.hibernate.annotations.OnDeleteAction.*

@Entity
@Table( name = 'article' )
class ArticleModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    Long id

    @OneToMany( mappedBy = 'parent', cascade = CascadeType.ALL )
    @SortNatural
    public SortedSet<ArticleImageModel> articleImages = new TreeSet<>()

    ArticleModel addArticleImageModel( final ArticleImageModel model ) {

        model.setParent( this )
        if ( !articleImages.contains( model ) ) {
            articleImages.add( model )
        }

        return this
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    SortedSet<ArticleImageModel> getArticleImages() {
        return articleImages
    }

    void setArticleImages(SortedSet<ArticleImageModel> articleImages) {
        this.articleImages = articleImages
    }
}
