package com.toy.project.config;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamUtils {

    public static <T> Stream<T> getCollectionStream(Collection<T> collection) {
        return Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
    }
}
