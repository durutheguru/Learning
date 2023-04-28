package com.julianduru.learning.raft;

import io.microraft.RaftEndpoint;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.requireNonNull;

/**
 * created by Julian Duru on 28/04/2023
 */
public class LocalRaftEndpoint implements RaftEndpoint {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    public static LocalRaftEndpoint newEndpoint() {
        return new LocalRaftEndpoint("node" + ID_GENERATOR.incrementAndGet());
    }


    private final String id;

    private LocalRaftEndpoint(String id) {
        this.id = requireNonNull(id);
    }


    @Override
    public Object getId() {
        return id;
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalRaftEndpoint that = (LocalRaftEndpoint) o;

        return id.equals(that.id);
    }


    @Override
    public String toString() {
        return "LocalRaftEndpoint{" + "id=" + id + '}';
    }


}
