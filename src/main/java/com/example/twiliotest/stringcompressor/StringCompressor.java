package com.example.twiliotest.stringcompressor;

public class StringCompressor {

  public String compress(String string) {
    if (string.length() == 0 || string.length() == 1) {
      return string;
    }

    CompressedStringBuilder builder = new CompressedStringBuilder();

    int characterCount = 1;
    for (int i = 1; i < string.length(); i++) {
      boolean characterIsEqualToLastOne = string.charAt(i) == string.charAt(i - 1);
      if (characterIsEqualToLastOne) {
        characterCount++;
      } else {
        builder.add(string.charAt(i - 1), characterCount);
        characterCount = 1;
      }
    }

    char lastCharacter = string.charAt(string.length() - 1);
    builder.add(lastCharacter, characterCount);

    return builder.toString();
  }
}
