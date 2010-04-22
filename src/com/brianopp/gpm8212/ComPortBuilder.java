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

import app.Com;
import app.Parameters;

import com.google.common.base.Preconditions;


/**
 * A utility class for building {@link Com} objects.
 *
 * @author Brian Oppenheim
 */
public class ComPortBuilder {
  /** parameters of the {@link Com} to be created for the {@link GPM8212} */
  private Parameters parameters;
  
  /**
   * Constructs a new @link{ComPortBuilder}.
   *
   * @throws Exception on {@link Exception}s from {@link Parameters}' constructor
   */
  public ComPortBuilder() throws Exception {
    this.parameters = new Parameters();
  }

  /**
   * 
   * @param baudRate
   * @return this {@link Builder}
   */
  public ComPortBuilder withBaudRate(int baudRate) {
    Preconditions.checkArgument(baudRate > 0, "Baud rate must be greater than 0.");
    this.parameters.setBaudRate(Integer.toString(baudRate));
    return this;
  }

  /**
   * 
   * @param byteSize
   * @return this {@link Builder}
   */
  public ComPortBuilder withByteSize(String byteSize) {
    this.parameters.setByteSize(Preconditions.checkNotNull(byteSize));
    return this;
  }

  /**
   * 
   * @param parity
   * @return this {@link Builder}
   */
  public ComPortBuilder withParity(String parity) {
    this.parameters.setParity(Preconditions.checkNotNull(parity));
    return this;
  }

  /**
   * 
   * @param port
   * @return this {@link Builder}
   */
  public ComPortBuilder withPort(String port) {
    this.parameters.setPort(Preconditions.checkNotNull(port));
    return this;
  }

  /**
   * 
   * @param readInterval
   * @return this {@link Builder}
   */
  public ComPortBuilder withReadInterval(int readInterval) {
    this.parameters.setReadInterval(readInterval);
    return this;
  }

  /**
   * 
   * @param readTotalConstant
   * @return this {@link Builder}
   */
  public ComPortBuilder withReadTotalConstant(int readTotalConstant) {
    this.parameters.setReadTotalConstant(readTotalConstant);
    return this;
  }

  /**
   * 
   * @param readTotalMultiplier
   * @return this {@link Builder}
   */
  public ComPortBuilder withReadTotalMultiplier(int readTotalMultiplier) {
    this.parameters.setReadTotalMultiplier(readTotalMultiplier);
    return this;
  }

  /**
   * 
   * @param stopBits
   * @return this {@link Builder}
   */
  public ComPortBuilder withStopBits(String stopBits) {
    this.parameters.setStopBits(Preconditions.checkNotNull(stopBits));
    return this;
  }

  /**
   * 
   * @param writeTotalConstant
   * @return this {@link Builder}
   */
  public ComPortBuilder withWriteTotalConstant(int writeTotalConstant) {
    this.parameters.setWriteTotalConstant(writeTotalConstant);
    return this;
  }

  /**
   * 
   * @param writeTotalMultiplier
   * @return this {@link Builder}
   */
  public ComPortBuilder withWriteTotalMultiplier(int writeTotalMultiplier) {
    this.parameters.setWriteTotalMultiplier(writeTotalMultiplier);
    return this;
  }

  /**
   * 
   * @param parameters
   * @return this {@link Builder}
   */
  @SuppressWarnings("hiding") 
  public ComPortBuilder withComParameters(Parameters parameters) {
    this.parameters = Preconditions.checkNotNull(parameters);
    return this;
  }

  /**
   * Constructs and returns a {@link Com} object based on the state of this {@link ComPortBuilder}.
   *
   * @return a {@link Com} object  constructed based on the state of this {@link ComPortBuilder}
   * @throws Exception on {@link Exception}s from {@link Com}'s constructor
   */
  public Com build() throws Exception {
    return new Com(this.parameters);
  }
}
