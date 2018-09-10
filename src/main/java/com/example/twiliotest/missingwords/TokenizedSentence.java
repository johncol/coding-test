package com.example.twiliotest.missingwords;

class TokenizedSentence {

  private static final String TOKENS_SEPARATOR = " ";

  private final String[] tokens;

  private TokenizedSentence(String sentence) {
    if (sentence.equals("")) {
      this.tokens = new String[]{};
    } else {
      this.tokens = sentence.split(TOKENS_SEPARATOR);
    }
  }

  static TokenizedSentence of(String original) {
    return new TokenizedSentence(original);
  }

  int size() {
    return tokens.length;
  }

  String wordAt(int index) {
    return tokens[index];
  }

  boolean isEmpty() {
    return size() == 0;
  }

  boolean isLongerThan(TokenizedSentence other) {
    return size() > other.size();
  }
}
