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

import java.util.Date;

import com.google.common.io.Closeables;

/**
 * A simple driver class for getting {@link GPM8212} readings in CSV format on regular intervals.
 *
 * @author Brian Oppenheim
 */
public class PowerReaderToCsv {
  /** frequency to issue reports */
  private static final int REPORT_INTERVAL = 1000;

  public static void main(String[] args) throws Exception {
    final GPM8212 reader = GPM8212.builder()
        .withBaudRate(9600)
        .withPort("COM1")
        .withStopBits("1")
        .withByteSize("8")
        .build();

    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        Closeables.closeQuietly(reader);
      }
    }));

    while (true) {
      System.out.println(getTimeToExcelPDT(new Date().getTime()) + ", " + reader.getCurrent());
      Thread.sleep(REPORT_INTERVAL);
    }
  }

  /**
   * Converts the given timestamp from milleseconds since 1/1/1970 0:00 GMT to Excel format
   * timestamp in PDT.
   *
   * @param time milleseconds since 1/1/1970 0:00 GMT
   * @return Excel format timestamp in PDT
   */
  private static double getTimeToExcelPDT(long time) {
    return (time / 86400000.0) + 25568.708333;
  }
}
