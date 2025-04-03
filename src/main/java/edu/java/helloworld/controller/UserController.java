package edu.java.helloworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.java.helloworld.dto.request.UserRequestDTO;
import edu.java.helloworld.dto.response.ResponseData;
import edu.java.helloworld.dto.response.ResponseError;
import edu.java.helloworld.dto.response.ResponseFailed;
import edu.java.helloworld.dto.response.ResponseSuccess;
import edu.java.helloworld.dto.response.Repository.UserDetailResponse;
import edu.java.helloworld.dto.response.page.PageRespones;
import edu.java.helloworld.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j // su dung de hien thi log
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    // @ResponseStatus(HttpStatus.CREATED)
    // tu dong tao response data co swagger
    public ResponseData addUser(@Valid @RequestBody UserRequestDTO user) {
        log.info("Request add user, {} {}", user.getFirstName(), user.getLastName());
        try {
            long userID = userService.saveUser(user);
            return new ResponseData(HttpStatus.CREATED.value(), "Them du lieu thanh cong", userID);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @PutMapping("/{userId}")
    public ResponseSuccess updateUser(@PathVariable @Min(1) int userId, @Valid @RequestBody UserRequestDTO user) {

        try {
            return new ResponseSuccess(HttpStatus.ACCEPTED, "Them du lieu thanh cong");
        } catch (Exception e) {
            return new ResponseFailed(HttpStatus.BAD_REQUEST, "Loi duong truyen");
        }

    }

    @PatchMapping("/{userId}")
    public ResponseSuccess updateStatus(@Min(1) @PathVariable int userId,
            @RequestParam boolean status) {
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Them du lieu thanh cong");
    }

    @DeleteMapping("/{userId}")
    public ResponseSuccess deleteUser(
            @PathVariable @Min(value = 1, message = "userId must be greater than 0") int userId) {
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Them du lieu thanh cong");
    }

    @GetMapping("/{userId}")
    public ResponseSuccess getUser(@PathVariable @Min(1) int userId) {
        userService.getUser(userId);
        return new ResponseSuccess(HttpStatus.OK, "Them du lieu thanh cong", userService.getUser(userId));
    }

    @GetMapping("/list")
    public ResponseSuccess getAllUser(@RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String sortBy) {

        List<UserDetailResponse> list = userService.getAllUser(pageNo, pageSize, sortBy);
        return new ResponseSuccess(HttpStatus.OK, "lay du lieu thanh cong", list);

    }

    @Operation(description = "with multi column", summary = "with multi coloumn")
    @GetMapping("/mutliList")
    public ResponseSuccess getAllUserWithMultiSort(@RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String... sortBy) {

        PageRespones<?> list = userService.getAllUserWithMultiColoum(pageNo, pageSize, sortBy);
        return new ResponseSuccess(HttpStatus.OK, "lay du lieu thanh cong", list);

    }

    @Operation(description = "with column + search", summary = "with column + search")
    @GetMapping("/search")
    public ResponseSuccess getAllUserWithSortSearch(@RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy) {
        PageRespones<?> list = userService.getAllUserWithColoumandSearch(pageSize, pageNo, search, sortBy);
        return new ResponseSuccess(HttpStatus.OK, "lay du lieu thanh cong", list);
    }

    @Operation(description = "advanced-search-criteria", summary = "advanced-search-criteria")
    @GetMapping("/advanced-search")
    public ResponseSuccess advancedSearchCriteria(@RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search) {
        return new ResponseSuccess(HttpStatus.OK, "lay du lieu thanh cong",
                userService.advancedSearch(pageSize, pageNo, sortBy, address, search));
    }

    @Operation(description = "advanced-search-specification", summary = "advanced-search-specification")
    @GetMapping("/advanced-search-with-specification")
    public ResponseSuccess advancedSearchSpecification(Pageable pageable,
            @RequestParam(required = false) String[] address,
            @RequestParam(required = false) String[] user) {
        return new ResponseSuccess(HttpStatus.OK, "lay du lieu thanh cong",
                userService.advancedSearchWithSpecition(pageable, address, user));
    }

    


}
