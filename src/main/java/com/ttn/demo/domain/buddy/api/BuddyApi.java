package com.ttn.demo.domain.buddy.api;

import com.ttn.demo.domain.buddy.application.BuddyService;
import com.ttn.demo.domain.buddy.dto.request.BuddyCreateRequest;
import com.ttn.demo.domain.buddy.dto.request.BuddyLetterRequest;
import com.ttn.demo.domain.buddy.dto.response.BuddyListResponse;
import com.ttn.demo.global.util.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buddy")
public class BuddyApi {

    private final BuddyService buddyService;

    public BuddyApi(BuddyService buddyService) {
        this.buddyService = buddyService;
    }

    @PostMapping
    public ResponseEntity<Void> createBuddy(@RequestBody BuddyCreateRequest buddyCreateRequest) {
        Long senderId = ApiUtils.getUserIdFromAuthentication();
        buddyService.createBuddy(senderId, buddyCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BuddyListResponse>> getBuddyList() {
        Long userId = ApiUtils.getUserIdFromAuthentication();
        List<BuddyListResponse> buddyList = buddyService.getBuddyList(userId);
        return ResponseEntity.ok(buddyList);
    }

    @PostMapping("/letter")
    public ResponseEntity<Void> sendLetter(@RequestBody BuddyLetterRequest buddyLetterRequest) {
        Long userId = ApiUtils.getUserIdFromAuthentication();
        buddyService.sendLetter(buddyLetterRequest, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
