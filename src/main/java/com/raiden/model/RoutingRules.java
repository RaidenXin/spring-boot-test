package com.raiden.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class RoutingRules {
    final Set<String> matches = new HashSet<String>();
    final Set<String> mismatches = new HashSet<String>();

    @Override
    public String toString() {
        return "RoutingRules{" +
                "matches=" + matches +
                ", mismatches=" + mismatches +
                '}';
    }
}
