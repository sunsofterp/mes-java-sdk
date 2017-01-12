package com.mes.sdk.test;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class MesTest {

  private final static Logger LOG = Logger.getLogger(MesTest.class.getName());
  
  public MesTest() {
    try {
      LogManager.getLogManager().readConfiguration(new FileInputStream("log.properties"));
    } catch (Exception e) {
      LOG.log(Level.WARNING, "Unable to load log.properties file");
    }
  }
  
  public abstract void run();
  
}
