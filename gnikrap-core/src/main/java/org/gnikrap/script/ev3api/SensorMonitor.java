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
package org.gnikrap.script.ev3api;

public class SensorMonitor {

  private final String uid;

  SensorMonitor(String uid) {
    this.uid = uid;
  }

  public boolean isEnabled() {
    return false;
  }

  public void log(float value) {
    if (isEnabled()) {
      // TODO
    }
  }

  public void log(int value) {
    if (isEnabled()) {
      // TODO
    }
  }

  public void log(String mode, float value) {
    if (isEnabled()) {
      // TODO
    }
  }

  public void log(String mode, int value) {
    if (isEnabled()) {
      // TODO
    }
  }
}
