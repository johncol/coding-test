package com.example.twiliotest.missingwords;

public class MissingWordsFinder {

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




