package com.example.dopamines.domain.board.project.service;

import com.example.dopamines.domain.board.project.model.entity.ProjectBoard;
import com.example.dopamines.domain.board.project.model.request.ProjectBoardReq;
import com.example.dopamines.domain.board.project.model.request.UpdateProjectBoardReq;
import com.example.dopamines.domain.board.project.model.response.ProjectBoardRes;
import com.example.dopamines.domain.board.project.model.response.ReadProjectBoardRes;
import com.example.dopamines.domain.board.project.repository.ProjectBoardRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectBoardService {

    private final ProjectBoardRepository projectBoardRepository;
    private final TeamRepository teamRepository;

    public ProjectBoardRes create(ProjectBoardReq req, String savedFileName) {

        ProjectBoard projectBoard = ProjectBoard.builder()
                .title(req.getTitle())
                .contents(req.getContents())
                .courseNum(req.getCourseNum())
                .sourceUrl(savedFileName)
                .team(teamRepository.findById(req.getTeamIdx()).get())
                .build();

        projectBoard = projectBoardRepository.save(projectBoard);

        List<String> students = new ArrayList<>();
        for(User student : projectBoard.getTeam().getStudents()) {
            students.add(student.getName());
        }

        return ProjectBoardRes.builder()
                .idx(projectBoard.getIdx())
                .title(projectBoard.getTitle())
                .contents(projectBoard.getContents())
                .courseNum(projectBoard.getCourseNum())
                .sourceUrl(projectBoard.getSourceUrl())
                .teamName(projectBoard.getTeam().getTeamName())
                .students(students)
                .build();
    }

    public ReadProjectBoardRes read(Long idx) {

        Optional<ProjectBoard> result = projectBoardRepository.findById(idx);

        if(result.isPresent()) {
            ProjectBoard projectBoard = result.get();

            List<String> students = new ArrayList<>();
            for(User student : projectBoard.getTeam().getStudents()) {
                students.add(student.getName());
            }

            return ReadProjectBoardRes.builder()
                    .idx(projectBoard.getIdx())
                    .title(projectBoard.getTitle())
                    .contents(projectBoard.getContents())
                    .courseNum(projectBoard.getCourseNum())
                    .sourceUrl(projectBoard.getSourceUrl())
                    .teamName(projectBoard.getTeam().getTeamName())
                    .students(students)
                    .build();
        } else {
            return null;
        }
    }

    public List<ReadProjectBoardRes> readByCourseNum(Long courseNum) {

        List<ProjectBoard> projectList = projectBoardRepository.findByCourseNum(courseNum);

        List<ReadProjectBoardRes> res = new ArrayList<>();

        for(ProjectBoard projectBoard : projectList) {
            List<String> students = new ArrayList<>();
            for(User student : projectBoard.getTeam().getStudents()) {
                students.add(student.getName());
            }

            res.add(ReadProjectBoardRes.builder()
                    .idx(projectBoard.getIdx())
                    .title(projectBoard.getTitle())
                    .contents(projectBoard.getContents())
                    .courseNum(projectBoard.getCourseNum())
                    .teamName(projectBoard.getTeam().getTeamName())
                    .students(students)
                    .build());
        }

        return res;
    }

    public List<ReadProjectBoardRes> readAll() {

        List<ProjectBoard> projectList = projectBoardRepository.findAll();

        List<ReadProjectBoardRes> res = new ArrayList<>();

        for(ProjectBoard projectBoard : projectList) {
            List<String> students = new ArrayList<>();
            for(User student : projectBoard.getTeam().getStudents()) {
                students.add(student.getName());
            }

            res.add(ReadProjectBoardRes.builder()
                    .idx(projectBoard.getIdx())
                    .title(projectBoard.getTitle())
                    .contents(projectBoard.getContents())
                    .courseNum(projectBoard.getCourseNum())
                    .teamName(projectBoard.getTeam().getTeamName())
                    .students(students)
                    .build());
        }

        return res;
    }

    public ReadProjectBoardRes update(UpdateProjectBoardReq req, String savedFileName) {
        ProjectBoard projectBoard = ProjectBoard.builder()
                .idx(req.getIdx())
                .title(req.getTitle())
                .contents(req.getContents())
                .courseNum(req.getCourseNum())
                .sourceUrl(savedFileName)
                .team(teamRepository.findById(req.getTeamIdx()).get())
                .build();

        projectBoard = projectBoardRepository.save(projectBoard);

        List<String> students = new ArrayList<>();
        for(User student : projectBoard.getTeam().getStudents()) {
            students.add(student.getName());
        }

        return ReadProjectBoardRes.builder()
                .idx(projectBoard.getIdx())
                .title(projectBoard.getTitle())
                .contents(projectBoard.getContents())
                .courseNum(projectBoard.getCourseNum())
                .sourceUrl(projectBoard.getSourceUrl())
                .teamName(projectBoard.getTeam().getTeamName())
                .students(students)
                .build();
    }

    public void delete(Long idx) {
        projectBoardRepository.deleteById(idx);
    }

}
