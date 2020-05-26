package jrh.game.structure;

import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.List;

public class Structures extends ForwardingList<Structure> {

    private final List<Structure> structures = new ArrayList<>();

    @Override
    protected List<Structure> delegate() {
        return structures;
    }
}
