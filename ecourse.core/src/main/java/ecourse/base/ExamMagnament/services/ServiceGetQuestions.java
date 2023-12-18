package ecourse.base.ExamMagnament.services;

import ecourse.base.ExamMagnament.domain.Question;
import ecourse.base.ExamMagnament.domain.QuestionType;
import ecourse.base.ExamMagnament.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServiceGetQuestions {


    public List<Question> getQuestions(QuestionType type, int numberOfQuestionsInSection, QuestionRepository questionRepository) {
        List<Question> list = questionRepository.findbytype(type);

        Random rand = new Random();
        int s = rand.nextInt(list.size());
        List<Question> questions = new ArrayList<>();

        for(int i = 0; i < numberOfQuestionsInSection; i++){
            questions.add(list.get(s));
            list.remove(s);
            if(list.size() != 0){
                s = rand.nextInt(list.size());
            }
        }

        return questions;
    }
}
