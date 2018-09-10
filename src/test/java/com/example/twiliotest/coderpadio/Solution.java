package com.example.twiliotest.coderpadio;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.JUnitCore;

public class Solution {

  /* ------------------------------------------------------
   * MISSING WORDS PROBLEM COMPLEXITY: O(n)
   * being n = amount of words in sentence S
   * ---------------------------------------------------- */

  /* ------------------------------------------------------
   * STRING COMPRESSION PROBLEM COMPLEXITY: O(n)
   * being n = length(string)
   * ---------------------------------------------------- */

  /* ------------------------------------------------------
   * MISSING WORDS PROBLEM: unit tests
   * ---------------------------------------------------- */

  private final MissingWordsFinder missingWordsFinder = new MissingWordsFinder();

  @Test
  public void shouldFindMissingWords() {
    String s = "I am using Twilio to power my comms";
    String t = "am Twilio to power";

    String[] missingWords = missingWordsFinder.find(s, t);

    assertEquals(4, missingWords.length);
    assertArrayEquals(new String[]{"I", "using", "my", "comms"}, missingWords);
  }

  @Test
  public void shouldNotFindAnyMissingWordWhenBothSentencesAreTheSame() {
    String s = "I am using Twilio to power my comms";
    String t = "I am using Twilio to power my comms";

    String[] missingWords = missingWordsFinder.find(s, t);

    assertEquals(0, missingWords.length);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowWhen_T_IsEmpty() {
    String s = "I am using Twilio to power my comms";
    String t = "";

    missingWordsFinder.find(s, t);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowWhen_T_IsLongerThan_S() {
    String s = "I am using Twilio to power my comms";
    String t = "I am using Twilio to power my comms and that is cool";

    missingWordsFinder.find(s, t);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowWhen_T_IsNotSubstringOf_S() {
    String s = "I am using Twilio to power my comms";
    String t = "I am using Tinder";

    missingWordsFinder.find(s, t);
  }

  /* ------------------------------------------------------
   * STRING COMPRESSION PROBLEM: unit tests
   * ---------------------------------------------------- */

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

  public static void main(String[] args) {
    JUnitCore.main("Solution");
  }
}

/* ------------------------------------------------------
 * MISSING WORDS PROBLEM: MissingWordsFinder class
 * ---------------------------------------------------- */

class MissingWordsFinder {

  public String[] find(String s, String t) {
    return findMissingWords(s, t).asStringArray();
  }

  public MissingWords findMissingWords(String s, String t) {
    TokenizedSentence sentence = TokenizedSentence.of(s);
    TokenizedSentence substring = TokenizedSentence.of(t);

    inputIsValidOrElseThrow(sentence, substring);

    MissingWords missingWords = new MissingWords();
    int delta = 0;
    int index;
    for (index = 0; index < substring.size(); index++) {
      boolean wordsAreDifferent;
      while (wordsAreDifferent = !sentence.wordAt(index + delta).equals(substring.wordAt(index))) {
        missingWords.add(sentence.wordAt(index + delta));
        delta++;

        boolean substringWordNotFoundInSentence = index + delta == sentence.size();
        if (substringWordNotFoundInSentence) {
          throw new IllegalArgumentException("T must be a substring of S");
        }
      }
    }

    boolean sentenceHasStillMoreWords = index + delta != sentence.size() - 1;
    if (sentenceHasStillMoreWords) {
      addAllMissingWordsStartingFrom(index + delta, sentence, missingWords);
    }

    return missingWords;
  }

  private void inputIsValidOrElseThrow(TokenizedSentence sentence, TokenizedSentence substring) {
    if (substring.isLongerThan(sentence)) {
      throw new IllegalArgumentException("Size of T expected to be smaller than S");
    }
    if (substring.isEmpty()) {
      throw new IllegalArgumentException("T cannot be empty");
    }
  }

  private void addAllMissingWordsStartingFrom(int indexFromWhichAddAll, TokenizedSentence sentence, MissingWords missingWords) {
    for (int i = indexFromWhichAddAll; i < sentence.size(); i++) {
      missingWords.add(sentence.wordAt(i));
    }
  }
}

/* ------------------------------------------------------
 * MISSING WORDS PROBLEM: MissingWords class
 * ---------------------------------------------------- */

class MissingWords {

  private final List<String> words = new ArrayList<>();

  public void add(String word) {
    words.add(word);
  }

  public String[] asStringArray() {
    return words.toArray(new String[]{});
  }
}

/* ------------------------------------------------------
 * MISSING WORDS PROBLEM: TokenizedSentence class
 * ---------------------------------------------------- */

class TokenizedSentence {

  static final String TOKENS_SEPARATOR = " ";

  private final String[] tokens;

  TokenizedSentence(String sentence) {
    if (sentence.equals("")) {
      this.tokens = new String[]{};
    } else {
      this.tokens = sentence.split(TOKENS_SEPARATOR);
    }
  }

  public static TokenizedSentence of(String original) {
    return new TokenizedSentence(original);
  }

  public int size() {
    return tokens.length;
  }

  public String wordAt(int index) {
    return tokens[index];
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean isLongerThan(TokenizedSentence other) {
    return size() > other.size();
  }
}

/* ------------------------------------------------------
 * STRING COMPRESSION PROBLEM: StringCompressor class
 * ---------------------------------------------------- */

class StringCompressor {

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

/* ------------------------------------------------------
 * STRING COMPRESSION PROBLEM: CompressedStringBuilder class
 * ---------------------------------------------------- */

class CompressedStringBuilder {

  private final List<CompressedCharacter> characters = new ArrayList<>();

  public void add(char character, int count) {
    characters.add(CompressedCharacter.of(character, count));
  }

  @Override
  public String toString() {
    return characters.stream()
        .map(CompressedCharacter::toString)
        .collect(Collectors.joining());
  }
}

/* ------------------------------------------------------
 * STRING COMPRESSION PROBLEM: CompressedCharacter class
 * ---------------------------------------------------- */

class CompressedCharacter {

  private final char character;
  private final int count;

  CompressedCharacter(char character, int count) {
    this.character = character;
    this.count = count;
  }

  public static CompressedCharacter of(char character, int count) {
    return new CompressedCharacter(character, count);
  }

  @Override
  public String toString() {
    return count == 1 ?
        String.valueOf(character) :
        String.valueOf(character) + count;
  }
}
