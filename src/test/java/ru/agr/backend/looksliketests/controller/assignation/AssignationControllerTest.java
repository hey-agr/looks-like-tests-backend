package ru.agr.backend.looksliketests.controller.assignation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTeacherAssignation;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTestAssignation;
import ru.agr.backend.looksliketests.controller.assignation.mapper.StudentToTeacherResourceMapper;
import ru.agr.backend.looksliketests.controller.assignation.mapper.StudentToTestResourceMapper;
import ru.agr.backend.looksliketests.controller.resources.StudentToTeacherAssignationResource;
import ru.agr.backend.looksliketests.controller.resources.StudentToTestAssignationResource;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTeacherAssignation;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.service.AssignationService;
import ru.agr.backend.looksliketests.service.TestService;
import ru.agr.backend.looksliketests.service.auth.UserService;
import ru.agr.backend.looksliketests.utils.SpringBootControllerTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Arslan Rabadanov
 */
@SpringBootControllerTest(AssignationController.class)
class AssignationControllerTest {
    private static final Long STUDENT_ID = 94737L;
    private static final Long TEACHER_ID = 27373L;
    private static final Long ASSIGNATION_ID = 303L;
    private static final Long TEST_ID = 10L;

    @MockBean
    private AssignationService assignationService;

    @MockBean
    private StudentToTeacherResourceMapper studentToTeacherResourceMapper;

    @MockBean
    private StudentToTestResourceMapper studentToTestResourceMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private TestService testService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @BeforeEach
    void initTest() {
        lenient().when(userService.checkIfUsersAuthorityExists(STUDENT_ID, UserAuthority.AuthorityName.STUDENT))
                .thenReturn(true);
        lenient().when(userService.checkIfUsersAuthorityExists(TEACHER_ID, UserAuthority.AuthorityName.TEACHER))
                .thenReturn(true);
        lenient().when(testService.findById(TEST_ID))
                .thenReturn(Optional.of(TestEntity.builder()
                        .id(TEST_ID)
                        .build()));
    }

    @Test
    @WithMockUser()
    void createStudentToTeacherAssign() throws Exception {
        var givenCreateStudentToTeacherAssignation = CreateStudentToTeacherAssignation.builder()
                .studentId(STUDENT_ID)
                .teacherId(TEACHER_ID)
                .build();

        var givenStudentToTeacherAssignation = StudentToTeacherAssignation.builder()
                .id(ASSIGNATION_ID)
                .studentId(STUDENT_ID)
                .teacherId(TEACHER_ID)
                .dateCreated(LocalDateTime.now())
                .build();

        when(studentToTeacherResourceMapper.toEntity(givenCreateStudentToTeacherAssignation))
                .thenReturn(givenStudentToTeacherAssignation);
        when(assignationService.save(givenStudentToTeacherAssignation))
                .thenReturn(givenStudentToTeacherAssignation);

        var expectedStudentToTeacherAssignationResource = StudentToTeacherAssignationResource.builder()
                .id(ASSIGNATION_ID)
                .studentId(STUDENT_ID)
                .teacherId(TEACHER_ID)
                .build();

        when(studentToTeacherResourceMapper.toResource(givenStudentToTeacherAssignation))
                .thenReturn(expectedStudentToTeacherAssignationResource);

        mockMvc.perform(post("/api/v1/assignations/student-to-teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(givenCreateStudentToTeacherAssignation)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedStudentToTeacherAssignationResource)))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"TEACHER"})
    void createStudentToTestAssign() throws Exception {
        var givenCreateStudentToTestAssignation = CreateStudentToTestAssignation.builder()
                .studentId(STUDENT_ID)
                .testId(TEST_ID)
                .build();

        var givenStudentToTestAssignation = StudentToTestAssignation.builder()
                .id(ASSIGNATION_ID)
                .studentId(STUDENT_ID)
                .testId(TEST_ID)
                .dateCreated(LocalDateTime.now())
                .build();

        when(studentToTestResourceMapper.toEntity(givenCreateStudentToTestAssignation))
                .thenReturn(givenStudentToTestAssignation);
        when(assignationService.save(givenStudentToTestAssignation))
                .thenReturn(givenStudentToTestAssignation);

        var expectedStudentToTestAssignationResource = StudentToTestAssignationResource.builder()
                .id(ASSIGNATION_ID)
                .studentId(STUDENT_ID)
                .testId(TEST_ID)
                .build();

        when(studentToTestResourceMapper.toResource(givenStudentToTestAssignation))
                .thenReturn(expectedStudentToTestAssignationResource);

        mockMvc.perform(post("/api/v1/assignations/student-to-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(givenCreateStudentToTestAssignation)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedStudentToTestAssignationResource)))
                .andReturn();
    }
}