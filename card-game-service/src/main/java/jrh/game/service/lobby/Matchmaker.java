package jrh.game.service.lobby;

import jrh.game.common.User;

public class Matchmaker {

    private final MatchManager matchManager;
    private User queue = new User("Jack");

    public Matchmaker(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    public synchronized void queue(User user) {
        if (queue == null) {
            queue = user;
        } else {
            matchManager.newMatch(queue, user);
            queue = null;
        }
    }

}
