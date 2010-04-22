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

import java.io.IOException;
import java.io.InputStream;

import app.Com;

/**
 * An {@link InputStream} for reading from a {@link Com} port.
 *
 * @author Brian Oppenheim
 */
public class ComInputStream extends InputStream {

  /** {@link Com} port to read from */
  private Com com;

  /**
   * Constructs a new {@link ComInputStream} object for readig fom the given {@link Com} port.
   *
   * @param com the {@link Com} port to read from
   */
  public ComInputStream(Com com) {
    super();
    this.com = com;
  }

  @Override
  public int read() throws IOException {
    try {
      return this.com.receiveSingleChar();
    } catch (Exception e) {
      throw new IOException(e);
    }
  }

  @Override
  public void close() throws IOException {
    try {
      this.com.close();
    } catch (Exception e) {
      throw new IOException(e);
    }
  }
}
