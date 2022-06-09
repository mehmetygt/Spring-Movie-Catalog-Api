package com.example.bookstoreapi.adapter.jpa.matching;

import com.example.bookstoreapi.adapter.jpa.actor.ActorEntity;
import com.example.bookstoreapi.adapter.jpa.common.BaseEntity;
import com.example.bookstoreapi.adapter.jpa.movie.MovieEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "matching")
@Table(name = "matching")
public class MatchingEntity extends BaseEntity {

    @ManyToOne
    private MovieEntity movie;

    @ManyToOne
    private ActorEntity actor;
}

