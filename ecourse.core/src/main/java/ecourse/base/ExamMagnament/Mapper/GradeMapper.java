package ecourse.base.ExamMagnament.Mapper;

import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.domain.ExamGradeDto;

import java.util.ArrayList;
import java.util.List;

public class GradeMapper {
    public static ExamGradeDto toDTO(ExamGrade grade){
        ExamGradeDto dto = new ExamGradeDto();
        dto.setStudent(grade.student().systemUserUsername());
        dto.setGrade(String.valueOf(grade.grade()));
        return dto;
    }

    public static List<ExamGradeDto> toDTO(List<ExamGrade> grades){
        List<ExamGradeDto> listDtos= new ArrayList<>();
        for (ExamGrade grade : grades) {
            listDtos.add(toDTO(grade));
        }
        return listDtos;
    }
}
