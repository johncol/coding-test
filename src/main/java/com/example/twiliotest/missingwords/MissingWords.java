package com.example.twiliotest.missingwords;

import java.util.ArrayList;
import java.util.List;

public class MissingWords {

  private final List<String> words = new ArrayList<>();

  void add(String word) {
    words.add(word);
  }

  String[] asStringArray() {
    return words.toArray(new String[]{});
  }
}
