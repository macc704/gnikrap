/*
 * Gnikrap is a simple scripting environment for the Lego Mindstrom EV3
 * Copyright (C) 2014-2015 Jean BENECH
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gnikrap.script.EV3ScriptContext;
import org.gnikrap.utils.MapBuilder;
import org.gnikrap.utils.ScriptApi;

import lejos.hardware.BrickFinder;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

/**
 * This class act as a factory and is the main entry point to access to the EV3 devices.<br/>
 */
public class SimpleEV3Brick {
  private final Map<String, EV3Device> devices = new HashMap<String, EV3Device>();
  private EV3ScriptContext sc;

  public SimpleEV3Brick() {
    BrickFinder.getLocal();
  }

  public void setScriptContext(EV3ScriptContext sc) {
    this.sc = sc;
  }

  // Actions
  @ScriptApi
  public SimpleEV3MediumMotor getMediumMotor(String port) throws EV3ScriptException {
    if (port == null) {
      return null;
    }

    EV3Device d = devices.get(port);
    if (d != null) {
      if (d instanceof SimpleEV3MediumMotor) {
        return (SimpleEV3MediumMotor) d;
      }
      d.release();
      devices.remove(port);
    }
    devices.put(port, new SimpleEV3MediumMotor(getMotorPort(port)));
    return getMediumMotor(port);
  }

  @ScriptApi
  public SimpleEV3LargeMotor getLargeMotor(String port) throws EV3ScriptException {
    EV3Device d = devices.get(port);
    if (d != null) {
      if (d instanceof SimpleEV3LargeMotor) {
        return (SimpleEV3LargeMotor) d;
      }
      d.release();
      devices.remove(port);
    }
    devices.put(port, new SimpleEV3LargeMotor(getMotorPort(port)));
    return getLargeMotor(port);
  }

  @ScriptApi
  public SimpleEV3Screen getScreen() {
    EV3Device d = devices.get(EV3Constants.SCREEN_KEY);
    if (d != null) {
      return (SimpleEV3Screen) d;
    }
    devices.put(EV3Constants.SCREEN_KEY, new SimpleEV3Screen());
    return getScreen();
  }

  @ScriptApi
  public SimpleEV3Sound getSound() {
    EV3Device d = devices.get(EV3Constants.SOUND_KEY);
    if (d != null) {
      return (SimpleEV3Sound) d;
    }
    devices.put(EV3Constants.SOUND_KEY, new SimpleEV3Sound());
    return getSound();
  }

  // Both sensor and action
  @ScriptApi
  public SimpleEV3Keyboard getKeyboard() {
    EV3Device d = devices.get(EV3Constants.KEYBOARD_KEY);
    if (d != null) {
      return (SimpleEV3Keyboard) d;
    }
    devices.put(EV3Constants.KEYBOARD_KEY, new SimpleEV3Keyboard(sc));
    return getKeyboard();
  }

  @ScriptApi
  public SimpleEV3Led getLed() {
    return getKeyboard().getLed();
  }

  // Sensors
  @ScriptApi
  public SimpleEV3Battery getBattery() {
    EV3Device d = devices.get(EV3Constants.BATTERY_KEY);
    if (d != null) {
      return (SimpleEV3Battery) d;
    }
    devices.put(EV3Constants.BATTERY_KEY, new SimpleEV3Battery());
    return getBattery();
  }

  @ScriptApi
  public SimpleEV3ColorSensor getColorSensor(String port) throws EV3ScriptException {
    EV3Device d = devices.get(port);
    if (d != null) {
      if (d instanceof SimpleEV3ColorSensor) {
        return (SimpleEV3ColorSensor) d;
      }
      d.release();
      devices.remove(port);
    }
    devices.put(port, new SimpleEV3ColorSensor(getSensorPort(port)));
    return getColorSensor(port);
  }

  @ScriptApi
  public SimpleEV3IRSensor getIRSensor(String port) throws EV3ScriptException {
    EV3Device d = devices.get(port);
    if (d != null) {
      if (d instanceof SimpleEV3IRSensor) {
        return (SimpleEV3IRSensor) d;
      }
      d.release();
      devices.remove(port);
    }
    devices.put(port, new SimpleEV3IRSensor(getSensorPort(port)));
    return getIRSensor(port);
  }

  @ScriptApi
  public SimpleEV3TouchSensor getTouchSensor(String port) throws EV3ScriptException {
    EV3Device d = devices.get(port);
    if (d != null) {
      if (d instanceof SimpleEV3TouchSensor) {
        return (SimpleEV3TouchSensor) d;
      }
      d.release();
      devices.remove(port);
    }
    devices.put(port, new SimpleEV3TouchSensor(getSensorPort(port)));
    return getTouchSensor(port);
  }

  @ScriptApi(isIncubating = true, versionAdded = "0.5.0")
  public SimpleNXTSoundSensor getNXTSoundSensor(String port) throws EV3ScriptException {
    EV3Device d = devices.get(port);
    if (d != null) {
      if (d instanceof SimpleNXTSoundSensor) {
        return (SimpleNXTSoundSensor) d;
      }
      d.release();
      devices.remove(port);
    }
    devices.put(port, new SimpleNXTSoundSensor(getSensorPort(port)));
    return getNXTSoundSensor(port);
  }

  @ScriptApi(isIncubating = true, versionAdded = "0.5.0")
  public SimpleEV3UltrasonicSensor getUltrasonicSensor(String port) throws EV3ScriptException {
    EV3Device d = devices.get(port);
    if (d != null) {
      if (d instanceof SimpleEV3UltrasonicSensor) {
        return (SimpleEV3UltrasonicSensor) d;
      }
      d.release();
      devices.remove(port);
    }
    devices.put(port, new SimpleEV3UltrasonicSensor(getSensorPort(port)));
    return getUltrasonicSensor(port);
  }

  public void releaseResources() {
    List<EV3Device> temp = new ArrayList<EV3Device>(devices.values());
    devices.clear();
    for (EV3Device d : temp) {
      d.release();
    }
  }

  // Utility methods
  private static Port getSensorPort(String p) throws EV3ScriptException {
    switch (p) {
    case "1":
    case EV3Constants.S1:
      return SensorPort.S1;
    case "2":
    case EV3Constants.S2:
      return SensorPort.S2;
    case "3":
    case EV3Constants.S3:
      return SensorPort.S3;
    case "4":
    case EV3Constants.S4:
      return SensorPort.S4;
    }
    throw new EV3ScriptException(EV3ScriptException.INVALID_SENSOR_PORT, MapBuilder.buildHashMap("port", p).build());
  }

  private static Port getMotorPort(String p) throws EV3ScriptException {
    if (p != null) {
      switch (p) {
      case EV3Constants.A:
        return MotorPort.A;
      case EV3Constants.B:
        return MotorPort.B;
      case EV3Constants.C:
        return MotorPort.C;
      case EV3Constants.D:
        return MotorPort.D;
      }
    }
    throw new EV3ScriptException(EV3ScriptException.INVALID_MOTOR_PORT, MapBuilder.buildHashMap("port", p).build());
  }
}
