package gr.aueb.cf.springteacher4.service;

import gr.aueb.cf.springteacher4.dto.TeacherInsertDTO;
import gr.aueb.cf.springteacher4.dto.TeacherUpdateDTO;
import gr.aueb.cf.springteacher4.model.Teacher;
import gr.aueb.cf.springteacher4.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface ITeacherService {
    Teacher insertTeacher(TeacherInsertDTO dto) throws Exception;
    Teacher updateTeacher(TeacherUpdateDTO dto) throws EntityNotFoundException;
    Teacher deleteTeacher(Long id) throws EntityNotFoundException;
    List<Teacher> getTeacherByLastname(String lastname) throws EntityNotFoundException;
    Teacher getTeacherById(Long id) throws EntityNotFoundException;
}
