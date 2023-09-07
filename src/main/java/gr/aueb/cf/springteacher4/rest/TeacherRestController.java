package gr.aueb.cf.springteacher4.rest;

import gr.aueb.cf.springteacher4.dto.TeacherInsertDTO;
import gr.aueb.cf.springteacher4.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.springteacher4.dto.TeacherUpdateDTO;
import gr.aueb.cf.springteacher4.model.Teacher;
import gr.aueb.cf.springteacher4.service.ITeacherService;
import gr.aueb.cf.springteacher4.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.springteacher4.validator.TeacherInsertValidator;
import gr.aueb.cf.springteacher4.validator.TeacherUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TeacherRestController {

    private final ITeacherService teacherService;
    private final TeacherInsertValidator teacherInsertValidator;
    private final TeacherUpdateValidator teacherUpdateValidator;

    @Autowired
    public TeacherRestController(ITeacherService teacherService,
                                 TeacherInsertValidator teacherInsertValidator,
                                 TeacherUpdateValidator teacherUpdateValidator) {
        this.teacherService = teacherService;
        this.teacherInsertValidator = teacherInsertValidator;
        this.teacherUpdateValidator = teacherUpdateValidator;
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherReadOnlyDTO>> getTeachersByLastname(@RequestParam("lastname") String lastname) {
        List<Teacher> teachers;
        try {
            teachers = teacherService.getTeacherByLastname(lastname);
            List<TeacherReadOnlyDTO> teachersDto = new ArrayList<>();
            for (Teacher teacher : teachers) {
                teachersDto.add(convertToReadOnlyDto(teacher));
            }
            return new ResponseEntity<>(teachersDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/teachers/{teacherId}")
    public ResponseEntity<TeacherReadOnlyDTO> getTeacher(@PathVariable("teacherId") Long teacherId) {
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherById(teacherId);
            TeacherReadOnlyDTO dto = convertToReadOnlyDto(teacher);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/teachers")
    public ResponseEntity<TeacherReadOnlyDTO> addTeacher(@RequestBody TeacherInsertDTO insertDTO, BindingResult bindingResult) {
        teacherInsertValidator.validate(insertDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Teacher teacher = teacherService.insertTeacher(insertDTO);
            TeacherReadOnlyDTO dto = convertToReadOnlyDto(teacher);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(dto.getId())
                    .toUri();
            return ResponseEntity.created(location).body(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/teachers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TeacherReadOnlyDTO> deleteTeacher(@PathVariable("id") Long id) {
        try {
            Teacher teacher = teacherService.getTeacherById(id);
            teacherService.deleteTeacher(id);
            TeacherReadOnlyDTO dto = convertToReadOnlyDto(teacher);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/teachers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TeacherReadOnlyDTO> updateTeacher(@PathVariable("id") Long id,
                                                            @Valid @RequestBody TeacherUpdateDTO updateDTO,
                                                            BindingResult bindingResult) {
        if (!Objects.equals(id, updateDTO.getId())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        teacherUpdateValidator.validate(updateDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Teacher teacher = teacherService.updateTeacher(updateDTO);
            TeacherReadOnlyDTO dto = convertToReadOnlyDto(teacher);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private TeacherReadOnlyDTO convertToReadOnlyDto(Teacher teacher) {
        TeacherReadOnlyDTO readOnlyDTO = new TeacherReadOnlyDTO();
        readOnlyDTO.setId(teacher.getId());
        readOnlyDTO.setFirstname(teacher.getFirstname());
        readOnlyDTO.setLastname(teacher.getLastname());
        return readOnlyDTO;
    }
}
