package com.example.bookstoreapi.domain.port;


import com.example.bookstoreapi.domain.actor.Actor;

import java.util.List;

public interface ActorPersistencePort {

    Long create(Actor actor);

    List<Actor> create(List<Actor> actors);

    List<Actor> retrieve(List<Long> actorIds);
}
