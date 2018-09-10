package com.example.twiliotest.missingwords;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MissingWordsFinderFinderTest {

  private final MissingWordsFinder missingWordsFinder = new MissingWordsFinder();

  @Test
  public void shouldFindMissingWords() {
    String s = "I am using Twilio to power my comms";
    String t = "am Twilio to power";

    String[] missingWords = missingWordsFinder.find(s, t);

    assertEquals(missingWords.length, 4);
    assertArrayEquals(missingWords, new String[]{"I", "using", "my", "comms"});
  }

  @Test
  public void shouldNotFindAnyMissingWordWhenBothSentencesAreTheSame() {
    String s = "I am using Twilio to power my comms";
    String t = "I am using Twilio to power my comms";

    String[] missingWords = missingWordsFinder.find(s, t);

    assertEquals(missingWords.length, 0);
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

}
