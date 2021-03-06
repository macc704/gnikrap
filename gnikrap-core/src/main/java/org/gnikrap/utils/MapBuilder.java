/*
 * Gnikrap is a simple scripting environment for the Lego Mindstrom EV3
 * Copyright (C) 2014 Jean BENECH
 * 
 * Gnikrap is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Gnikrap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Gnikrap.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gnikrap.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * An helper class in order to populate newly created maps.
 */
public final class MapBuilder<K, V> {
  private final Map<K, V> map;

  public MapBuilder(Map<K, V> map) {
    this.map = map;
  }

  public MapBuilder<K, V> put(K k, V v) {
    map.put(k, v);
    return this;
  }

  public Map<K, V> build() {
    return map;
  }

  public static final <K, V> MapBuilder<K, V> buildHashMap(K k, V v) {
    return new MapBuilder<K, V>(new HashMap<K, V>()).put(k, v);
  }

  public static final <K, V> MapBuilder<K, V> buildTreeMapBuilder() {
    return new MapBuilder<K, V>(new TreeMap<K, V>());
  }
}
