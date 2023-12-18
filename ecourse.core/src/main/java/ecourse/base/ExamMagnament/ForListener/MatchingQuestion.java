package ecourse.base.ExamMagnament.ForListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchingQuestion extends Question{

    private String[] machingWords = new String[2];

    private List<String> wordsToMatch;
    private List<String> wordsToMatchWith;

    private Map<String, String> result;

    public MatchingQuestion() {
        super("");
        wordsToMatch = new ArrayList<>();
        wordsToMatchWith = new ArrayList<>();
        result = new HashMap<>();
    }
    public MatchingQuestion(String question) {
        super(question);
    }

    public void addWordToMatch(String wordToMatch) {
        wordsToMatch.add(wordToMatch);
    }

    public void addWordToMatchWith(String wordToMatchWith) {
        wordsToMatchWith.add(wordToMatchWith);
    }

    public CharSequence getWordToMatch(int i) {
        return wordsToMatch.get(i);
    }

    public String getWordToMatchWith(int j) {
        return wordsToMatchWith.get(j);
    }

    public List<String> getListWordToMatchWith() {
        return wordsToMatchWith;
    }

    public void addToMap(String wordToMatch, String wordToMatchWith) {
        result.put(wordToMatch,  wordToMatchWith);
    }

    public Map<String, String> getMap() {
        return result;
    }
}
