package com.uysalk.metal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by uysal.kara on 12.01.2017.
 */
public class MetalRegistry {

    Map<String, Metal> registry = new HashMap<String, Metal>();

    public void register(Metal m) {
        registry.put(m.name, m);
    }

    public Metal get(String name) {
        return registry.get(name);
    }
}
