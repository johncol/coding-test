package com.example.twiliotest.stringcompressor;

public class CompressedCharacter {

  private final char character;
  private final int count;

  private CompressedCharacter(char character, int count) {
    this.character = character;
    this.count = count;
  }

  static CompressedCharacter of(char character, int count) {
    return new CompressedCharacter(character, count);
  }

  @Override
  public String toString() {
    return count == 1 ?
        String.valueOf(character) :
        String.valueOf(character) + count;
  }
}
