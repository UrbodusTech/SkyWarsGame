package com.skywars.generic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Manager<K, V extends Identifiable<K>> implements Initializable {

    private final Map<K, V> entries = generateValue();

    protected Map<K, V> generateValue() {
        return new HashMap<>();
    }

    public Collection<V> getEntries() {
        return entries.values();
    }

    public void register(V entry) {
        if (entries.containsKey(entry.getId())) {
            return;
        }
        entries.put(entry.getId(), entry);
    }

    public V get(K key) {
        return entries.get(key);
    }

    public boolean contains(K key) {
        return entries.containsKey(key);
    }

    public V unregister(K key) {
        return entries.remove(key);
    }

    public void unregisterAll() {
        entries.clear();
    }

}
