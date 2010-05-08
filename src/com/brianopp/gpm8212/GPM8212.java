/*
 * Copyright 2010 - Brian Oppenheim (brianopp.com)
 *
 * This file is part of java-gpm8212.
 *
 * java-gpm8212 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * java-gpm8212 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with java-gpm8212.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.brianopp.gpm8212;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import com.google.common.base.Throwables;

/**
 * A class for communicating with a GWInstek GPM-8212.
 *
 * @author Brian Oppenheim
 */
public class GPM8212 implements Closeable {

  /** Measurement reading modes. */
  public enum MeasurementStatus {MAXIMUM, MINIMUM, NORMAL}

  /** Volt range settings. */
  public enum VoltRange {
    /** 640V */
    V640,
    /** 320V */
    V320,
    /** 160V */
    V160,
    /** 80V */
    V80,
    /** 40V */
    V40,
    /** 20V */
    V20,
    /** 10V */
    V10,
    /** 5V */
    V5,
    /** Autorange */
    VAUTO}

  /** Amp range settings. */
  public enum AmpRange {
    /** 20.48A */
    A20_48,
    /** 10.24A */
    A10_24,
    /** 5.12A */
    A5_12,
    /** 2.56A */
    A2_56,
    /** 1.28A */
    A1_28,
    /** 0.64A */
    A_64,
    /** 0.32A */
    A_32,
    /** 0.16A */
    A_16,
    /** Autorange */
    AAUTO}

  /** the character that terminates outgoing and incoming messages */
  private static final char STOP_CHARACTER = 0x0D;

  /** the {@link InputStream} to send commands on */
  private InputStream inputStream;

  /** the {@link OutputStream} to receive commands on */
  private OutputStream outputStream;

  /**
   * Constructs a new {@link GPM8212} object communicating over the given streams.
   *
   * @param inputStream the {@link InputStream} to send commands on
   * @param outputStream the {@link OutputStream} to receive commands on
   */
  public GPM8212(InputStream inputStream, OutputStream outputStream) {
    this.inputStream = inputStream;
    this.outputStream = outputStream;
  }

  /**
   * Sets whether or not the data hold option is enabled.
   *
   * @param enable should the data hold option be enabled
   * @throws IOException on any exception sending the command
   */
  public void setDataHoldEnabled(boolean enable) throws IOException {
    if (enable) {
      this.sendCommand("F00");
    } else {
      this.sendCommand("F01");
    }
  }

  /**
   * Sets the measurement status option. 
   *
   * @param measurementStatus the type of measurement to be taking
   * @throws IOException on any exception sending the command
   */
  public void setMeasurementStatus(MeasurementStatus measurementStatus) throws IOException {
    switch (measurementStatus) {
      case MAXIMUM:
        this.sendCommand("F02");
        break;
      case MINIMUM:
        this.sendCommand("F03");
        break;
      case NORMAL:
        this.sendCommand("F04");
        break;
      default:
        throw new UnsupportedOperationException("Not sure what to do with measurement status " +
                                                measurementStatus + ".");
    }
  }

  /**
   * Sets the volt range on the meter.
   *
   * @param voltRange the volt range setting to set
   * @throws IOException on any exception sending the command
   */
  public void setVoltRange(VoltRange voltRange) throws IOException {
    switch (voltRange) {
      case V640:
        this.sendCommand("R00");
        break;
      case V320:
        this.sendCommand("R01");
        break;
      case V160:
        this.sendCommand("R02");
        break;
      case V80:
        this.sendCommand("R03");
        break;
      case V40:
        this.sendCommand("R04");
        break;
      case V20:
        this.sendCommand("R05");
        break;
      case V10:
        this.sendCommand("R06");
        break;
      case V5:
        this.sendCommand("R07");
        break;
      case VAUTO:
        this.sendCommand("R16");
        break;
      default:
        throw new UnsupportedOperationException("Not sure what to do with volt range " +
                                                voltRange + ".");
    }
  }

  /**
   * Sets the amp range on the meter.
   *
   * @param ampRange the amp range setting to set
   * @throws IOException on any exception sending the command
   */
  public void setAmpRange(AmpRange ampRange) throws IOException {
    switch (ampRange) {
      case A20_48:
        this.sendCommand("R08");
        break;
      case A10_24:
        this.sendCommand("R09");
        break;
      case A5_12:
        this.sendCommand("R10");
        break;
      case A2_56:
        this.sendCommand("R11");
        break;
      case A1_28:
        this.sendCommand("R12");
        break;
      case A_64:
        this.sendCommand("R13");
        break;
      case A_32:
        this.sendCommand("R14");
        break;
      case A_16:
        this.sendCommand("R15");
        break;
      case AAUTO:
        this.sendCommand("R17");
        break;
      default:
        throw new UnsupportedOperationException("Not sure what to do with amp range " +
                                                ampRange + ".");
    }
  }

  /**
   * Gets the meter's voltage reading.
   *
   * @return the meter's voltage reading
   * @throws IOException on any exception sending the command or receiving its response
   */
  public BigDecimal getVoltage() throws IOException {
    return new BigDecimal(this.sendCommandAndGetResults("V00"));
  }

  /**
   * Gets the meter's current reading.
   *
   * @return the meter's current reading
   * @throws IOException on any exception sending the command or receiving its response
   */
  public BigDecimal getCurrent() throws IOException {
    return new BigDecimal(this.sendCommandAndGetResults("V01"));
  }

  /**
   * Gets the meter's watt reading.
   *
   * @return the meter's watt reading
   * @throws IOException on any exception sending the command or receiving its response
   */
  public BigDecimal getWatt() throws IOException {
    return new BigDecimal(this.sendCommandAndGetResults("V02"));
  }

  /**
   * Gets the meter's power factor (PF) reading.
   *
   * @return the meter's PF reading
   * @throws IOException on any exception sending the command or receiving its response
   */
  public BigDecimal getPf() throws IOException {
    return new BigDecimal(this.sendCommandAndGetResults("V03"));
  }

  /**
   * Gets the meter's frequency (Hz) reading.
   *
   * @return the meter's Hz reading
   * @throws IOException on any exception sending the command or receiving its response
   */
  public BigDecimal getHz() throws IOException {
    return new BigDecimal(this.sendCommandAndGetResults("V04"));
  }

  /**
   * Sends the given command to the meter.
   *
   * @param command the command to send
   * @throws IOException on any exception sending the command
   */
  private void sendCommand(String command) throws IOException {
    for (byte c : command.getBytes()) {
      this.outputStream.write(c);
    }

    this.outputStream.write(STOP_CHARACTER);
  }
  

  /**
   * Sends the given command to the meter and receives its response.
   *
   * @param command the command to send
   * @return the meter's response to the given command
   * @throws IOException on any exception sending the command or receiving its response
   */
  private String sendCommandAndGetResults(String command) throws IOException {
    this.sendCommand(command);

    StringBuilder builder = new StringBuilder();

    char next;

    while ((next = (char) this.inputStream.read()) != STOP_CHARACTER) {
      builder.append(next);
    }

    return builder.toString();
  }

  @Override
  public void close() throws IOException {
    IOException inputStreamCloseException = null;

    try {
      this.inputStream.close();
    } catch (IOException e) {
      inputStreamCloseException = e;
    }

    try {
      this.outputStream.close();
    } catch (IOException e) {
      if (inputStreamCloseException == null) {
        throw new IOException(e);
      }

      throw new IOException("2 IOExceptions caused by close().\n\n" +
                            "From closing the InputStream:\n" +
                            Throwables.getStackTraceAsString(inputStreamCloseException) +
                            "\n\n" +
                            "From closing the OutputStream:\n" +
                            Throwables.getStackTraceAsString(e));
    }
  }
}

