package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.model.request.FreeBoardReq;
import com.example.dopamines.domain.board.community.free.model.request.UpdateFreeBoardReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardRes;
import com.example.dopamines.domain.board.community.free.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<FreeBoardRes> create(@RequestBody FreeBoardReq req){
        FreeBoardRes response = freeBoardService.create(req);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<FreeBoardReadRes> read(Long idx){
        FreeBoardReadRes response = freeBoardService.read(idx);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read-all")
    public ResponseEntity<List<FreeBoardRes>> readAll(Integer page, Integer size){
        List<FreeBoardRes> response = freeBoardService.readAll(page,size);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<FreeBoardRes> update(@RequestBody UpdateFreeBoardReq req){
        FreeBoardRes response = freeBoardService.update(req);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<String> delete(Long idx){
        freeBoardService.delete(idx);
        return ResponseEntity.ok(idx+"번의 게시글이 삭제되었습니다.");
    }
}
