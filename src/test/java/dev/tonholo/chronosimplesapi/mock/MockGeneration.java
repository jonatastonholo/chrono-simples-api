package dev.tonholo.chronosimplesapi.mock;

import dev.tonholo.chronosimplesapi.domain.Project;
import dev.tonholo.chronosimplesapi.service.PeriodService;
import dev.tonholo.chronosimplesapi.service.ProjectService;
import dev.tonholo.chronosimplesapi.service.event.PeriodCreationEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static dev.tonholo.chronosimplesapi.mock.model.ProjectCreationEventMock.getProjectCreationEventMock;

@SpringBootTest
@Disabled("This test class is disabled because it's generate data. It should only be used in the first setup of the project, after running migrations.")
class MockGeneration {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PeriodService periodService;


    @Test
    void generateProjects() {
        List.of(
                getProjectCreationEventMock("Projeto Java", 90.80),
                getProjectCreationEventMock("Projeto Flutter", 75.50),
                getProjectCreationEventMock("Projeto Fullstack", 85.70),
                getProjectCreationEventMock("Projeto .NET", 80.10),
                getProjectCreationEventMock("Projeto Angular", 78.65)
        ).forEach(projectCreationEvent -> {
            projectService.create(projectCreationEvent)
                    .block();
        });
    }

    @Test
    void generatePeriods() {
        final LocalDateTime begin = LocalDateTime.of(2020,01,01,8,0,0);
        final LocalDateTime end = LocalDateTime.now().minusDays(2);


        final List<Project> projects = projectService
                .findAll()
                .collectList()
                .block();

        LocalDateTime day = begin;

        int numberOfProjects = projects.size();
        int projectCnt = 0;

        while (!day.isAfter(end)) {
            var dayBegin = day;
            var dayEnd = dayBegin.plusHours(8);
            var project = projects.get(projectCnt);

            final PeriodCreationEvent periodCreationEvent = PeriodCreationEvent.builder()
                    .projectId(project.getId())
                    .description("Description for day " + day.getDayOfWeek() + "project " + project.getName())
                    .begin(dayBegin)
                    .end(dayEnd)
                    .hourValue(project.getHourValue())
                    .currency(project.getCurrencyCode())
                    .build();

            periodService.create(periodCreationEvent)
                    .block();


            if (DayOfWeek.FRIDAY.compareTo(day.getDayOfWeek()) == 0) {
                day = day.plusDays(3);
            } else {
                day = day.plusDays(1);
            }

            if (++projectCnt == numberOfProjects) {
                projectCnt = 0;
            }
        }
    }
}
