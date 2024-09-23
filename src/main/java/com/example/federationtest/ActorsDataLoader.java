package com.example.federationtest;

import com.example.netflixdgs.codegen.types.Actor;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.BatchLoader;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// BatchLoader<the id of the parent entity, what it is supposed to return for every parent entity ID>
// BatchLoader<ParentIdType, ValueTypeForEachParent>
@DgsDataLoader(name = "actors")
public class ActorsDataLoader implements BatchLoader<String, List<Actor>> {

    // invoked lazy
    @Override
    public CompletionStage<List<List<Actor>>> load(List<String> showIds) {
        Map<String, List<Actor>> actorsByShowId = DummyData.getAllActorsForShows(showIds);
        return CompletableFuture.completedFuture(showIds.stream()
                .map(actorsByShowId::get)
                .toList());
    }
}
