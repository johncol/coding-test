package com.example.twiliotest.stringcompressor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringCompressorTest {

  private final StringCompressor compressor = new StringCompressor();

  @Test
  public void shouldCompressNonEmptyString() {
    String string = "abaabbbc";

    String compressedString = compressor.compress(string);

    assertEquals("aba2b3c", compressedString);
  }

  @Test
  public void shouldCompressEmptyStringAsEmptyString() {
    String emptyString = "";

    String compressedString = compressor.compress(emptyString);

    assertEquals(emptyString, compressedString);
  }

  @Test
  public void shouldCompressNonCompressibleStringAsTheSameString() {
    String string = "abcdefghijklmnopqrstuvqwxyz";

    String compressedString = compressor.compress(string);

    assertEquals(string, compressedString);
  }

}
