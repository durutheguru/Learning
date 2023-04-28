package com.julianduru.learning.raft;


import io.microraft.RaftEndpoint;
import io.microraft.RaftNode;
import io.microraft.RaftNodeStatus;
import io.microraft.report.RaftTerm;
import io.microraft.statemachine.StateMachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;


public class LeaderElectionTest {

    private List<RaftEndpoint> initialMembers = Arrays.asList(
        LocalRaftEndpoint.newEndpoint(),
        LocalRaftEndpoint.newEndpoint(),
        LocalRaftEndpoint.newEndpoint()
    );
    private List<LocalTransport> transports = new ArrayList<>();
    private List<RaftNode> raftNodes = new ArrayList<>();

    @Before
    public void startRaftGroup() {
        for (RaftEndpoint endpoint : initialMembers) {
            RaftNode raftNode = createRaftNode(endpoint);
            raftNode.start();
        }
    }

    @After
    public void terminateRaftGroup() {
        raftNodes.forEach(RaftNode::terminate);
    }

    @Test
    public void testLeaderElection() {
        RaftNode leader = waitUntilLeaderElected();

        assertThat(leader).isNotNull();

        System.out.println(leader.getLocalEndpoint().getId() + " is the leader!");
    }

    private RaftNode createRaftNode(RaftEndpoint endpoint) {
        LocalTransport transport = new LocalTransport(endpoint);
        StateMachine stateMachine = new AtomicRegister();
        RaftNode raftNode = RaftNode.newBuilder()
            .setGroupId("default")
            .setLocalEndpoint(endpoint)
            .setInitialGroupMembers(initialMembers)
            .setTransport(transport)
            .setStateMachine(stateMachine)
            .build();

        raftNodes.add(raftNode);
        transports.add(transport);

        enableDiscovery(raftNode, transport);

        return raftNode;
    }

    private void enableDiscovery(RaftNode raftNode, LocalTransport transport) {
        for (int i = 0; i < raftNodes.size(); i++) {
            RaftNode otherNode = raftNodes.get(i);
            if (otherNode != raftNode) {
                transports.get(i).discoverNode(raftNode);
                transport.discoverNode(otherNode);
            }
        }
    }

    private RaftNode waitUntilLeaderElected() {
        long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60);
        while (System.currentTimeMillis() < deadline) {
            RaftEndpoint leaderEndpoint = getLeaderEndpoint();
            if (leaderEndpoint != null) {
                return raftNodes.stream().filter(node -> node.getLocalEndpoint().equals(leaderEndpoint)).findFirst()
                    .orElseThrow(IllegalStateException::new);
            }

            try {
                MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        throw new AssertionError("Could not elect a leader on time!");
    }

    private RaftEndpoint getLeaderEndpoint() {
        RaftEndpoint leaderEndpoint = null;
        int leaderTerm = 0;
        for (RaftNode raftNode : raftNodes) {
            if (raftNode.getStatus() == RaftNodeStatus.TERMINATED) {
                continue;
            }

            RaftTerm term = raftNode.getTerm();
            if (term.getLeaderEndpoint() != null) {
                if (leaderEndpoint == null) {
                    leaderEndpoint = term.getLeaderEndpoint();
                    leaderTerm = term.getTerm();
                } else if (!(leaderEndpoint.equals(term.getLeaderEndpoint()) && leaderTerm == term.getTerm())) {
                    leaderEndpoint = null;
                    break;
                }
            } else {
                leaderEndpoint = null;
                break;
            }
        }

        return leaderEndpoint;
    }

}