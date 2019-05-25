package com.vmvare.interview;

import com.vmware.interview.DrawableFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Test;

public class DrawableTest {
  @Test
  public void testDraw() {
    try (ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(output)) {
      System.setOut(out);

      String separator = System.lineSeparator();

      DrawableFactory.newInstance("circle").draw();
      Assert.assertEquals(String.format("Before draw%sDraw circle%sPost draw%s",
          separator, separator, separator), new String(output.toByteArray()));
      output.reset();

      DrawableFactory.newInstance("Square").draw();
      Assert.assertEquals(String.format("Before draw%sDraw square%sPost draw%s",
          separator, separator, separator), new String(output.toByteArray()));
      output.reset();

      DrawableFactory.newInstance("abstract paint").draw();
      Assert.assertEquals(String.format("Before draw%sDraw abstract paint%sPost draw%s",
          separator, separator, separator), new String(output.toByteArray()));
      output.reset();

      DrawableFactory.newInstance("observer").draw();
      Assert.assertEquals(String.format("Before draw%sDraw observer%sPost draw%s",
          separator, separator, separator), new String(output.toByteArray()));
      output.reset();

      DrawableFactory.newInstance(null).draw();
      Assert.assertEquals(String.format("Before draw%sNothing to draw%sPost draw%s",
          separator, separator, separator), new String(output.toByteArray()));
      output.reset();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
