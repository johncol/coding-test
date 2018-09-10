package com.example.twiliotest.stringcompressor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompressedStringBuilder {

  private final List<CompressedCharacter> characters = new ArrayList<>();

  void add(char character, int count) {
    characters.add(CompressedCharacter.of(character, count));
  }

  @Override
  public String toString() {
    return characters.stream()
        .map(CompressedCharacter::toString)
        .collect(Collectors.joining());
  }
}