package com.example.dopamines.domain.board.project.service;

import com.example.dopamines.domain.board.project.model.entity.ProjectPost;
import com.example.dopamines.domain.board.project.model.request.ProjectPostReq;
import com.example.dopamines.domain.board.project.model.request.ProjectPostUpdateReq;
import com.example.dopamines.domain.board.project.model.response.ProjectPostRes;
import com.example.dopamines.domain.board.project.model.response.ProjectPostReadRes;
import com.example.dopamines.domain.board.project.repository.ProjectPostRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.TeamRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectPostService {

    private final ProjectPostRepository projectBoardRepository;
    private final TeamRepository teamRepository;

    public BaseResponse<ProjectPostRes> create(ProjectPostReq req, String savedFileName) {
        try{
            ProjectPost projectBoard = ProjectPost.builder()
                    .title(req.getTitle())
                    .contents(req.getContent())
                    .courseNum(req.getCourseNum())
                    .gitUrl(req.getGitUrl())
                    .sourceUrl(savedFileName)
                    .team(teamRepository.findById(req.getTeamIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.TEAM_NOT_FOUND)))
                    .build();

            projectBoard = projectBoardRepository.save(projectBoard);

            List<String> students = new ArrayList<>();
            for(User student : projectBoard.getTeam().getStudents()) {
                students.add(student.getName());
            }

            return new BaseResponse<>(ProjectPostRes.builder()
                    .idx(projectBoard.getIdx())
                    .title(projectBoard.getTitle())
                    .contents(projectBoard.getContents())
                    .courseNum(projectBoard.getCourseNum())
                    .gitUrl(projectBoard.getGitUrl())
                    .sourceUrl(projectBoard.getSourceUrl())
                    .teamName(projectBoard.getTeam().getTeamName())
                    .students(students)
                    .build());
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseStatus.PROJECT_SAVE_FAILED);
        }
    }

    public BaseResponse<ProjectPostReadRes> read(Long idx) {
        ProjectPost result = projectBoardRepository.findById(idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PROJECT_NOT_FOUND));

        List<String> students = new ArrayList<>();
        for(User student : result.getTeam().getStudents()) {
            students.add(student.getName());
        }
        return new BaseResponse<>(ProjectPostReadRes.builder()
                .idx(result.getIdx())
                .title(result.getTitle())
                .contents(result.getContents())
                .gitUrl(result.getGitUrl())
                .courseNum(result.getCourseNum())
                .sourceUrl(result.getSourceUrl())
                .teamName(result.getTeam().getTeamName())
                .students(students)
                .build());
    }

    public BaseResponse<List<ProjectPostReadRes>> readByCourseNum(Integer courseNum) {

        List<ProjectPost> projectList = projectBoardRepository.findByCourseNum(courseNum)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PROJECT_NOT_FOUND));

        List<ProjectPostReadRes> res = new ArrayList<>();

        for(ProjectPost projectBoard : projectList) {
            List<String> students = new ArrayList<>();
            for(User student : projectBoard.getTeam().getStudents()) {
                students.add(student.getName());
            }

            res.add(ProjectPostReadRes.builder()
                    .idx(projectBoard.getIdx())
                    .title(projectBoard.getTitle())
                    .contents(projectBoard.getContents())
                    .courseNum(projectBoard.getCourseNum())
                    .teamName(projectBoard.getTeam().getTeamName())
                    .students(students)
                    .build());
        }

        return new BaseResponse<>(res);
    }

    public BaseResponse<List<ProjectPostReadRes>> readAll() {

        List<ProjectPost> projectList = projectBoardRepository.findAll();

        List<ProjectPostReadRes> res = new ArrayList<>();

        for(ProjectPost projectBoard : projectList) {
            List<String> students = new ArrayList<>();
            for(User student : projectBoard.getTeam().getStudents()) {
                students.add(student.getName());
            }

            res.add(ProjectPostReadRes.builder()
                    .idx(projectBoard.getIdx())
                    .title(projectBoard.getTitle())
                    .contents(projectBoard.getContents())
                    .courseNum(projectBoard.getCourseNum())
                    .teamName(projectBoard.getTeam().getTeamName())
                    .students(students)
                    .build());
        }

        return new BaseResponse<>(res);
    }

    public ProjectPostReadRes update(ProjectPostUpdateReq req, String savedFileName) {
        try{
            System.out.println(req.getGitUrl());
            ProjectPost projectBoard = ProjectPost.builder()
                    .idx(req.getIdx())
                    .title(req.getTitle())
                    .contents(req.getContents())
                    .gitUrl(req.getGitUrl())
                    .courseNum(req.getCourseNum())
                    .sourceUrl(savedFileName)
                    .team(teamRepository.findById(req.getTeamIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.TEAM_NOT_FOUND)))
                    .build();


            projectBoard = projectBoardRepository.save(projectBoard);

            List<String> students = new ArrayList<>();
            for(User student : projectBoard.getTeam().getStudents()) {
                students.add(student.getName());
            }

            return ProjectPostReadRes.builder()
                    .idx(projectBoard.getIdx())
                    .title(projectBoard.getTitle())
                    .contents(projectBoard.getContents())
                    .courseNum(projectBoard.getCourseNum())
                    .gitUrl(projectBoard.getGitUrl())
                    .sourceUrl(projectBoard.getSourceUrl())
                    .teamName(projectBoard.getTeam().getTeamName())
                    .students(students)
                    .build();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.PROJECT_UPDATE_FAILED);
        }
    }

    public void delete(Long idx) {
        try {
            projectBoardRepository.deleteById(idx);
        } catch (EntityNotFoundException e) {
            throw new BaseException(BaseResponseStatus.PROJECT_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.PROJECT_DELETE_FAILED);
        }
    }

}