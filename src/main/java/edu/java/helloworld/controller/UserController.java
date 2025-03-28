package edu.java.helloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.java.helloworld.dto.request.UserRequestDTO;
import edu.java.helloworld.dto.response.ResponseData;
import edu.java.helloworld.dto.response.ResponseError;
import edu.java.helloworld.dto.response.ResponseFailed;
import edu.java.helloworld.dto.response.ResponseSuccess;
import edu.java.helloworld.exception.ResourceNotFoundExecptionExecption;
import edu.java.helloworld.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    // @Operation(summary = "sumary", description = "them moi", responses = {
    //     @ApiResponse(responseCode = "201", description = "User add successfully ",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "ex name", summary = "ex sumary",
    //         value = "{ \"status\": 201, \"message\": \"User added successfully\", \"data\": 1 }"
    //     )
    //             ))
    // })
    // mo ta cho back end
      @PostMapping("/")
    //   @ResponseStatus(HttpStatus.CREATED)
      // tu dong tao response data co swagger
    public ResponseData addUser(@Valid @RequestBody UserRequestDTO user) {
       try {
        userService.addUser(user);
        return new ResponseData(HttpStatus.CREATED.value(), "Them du lieu thanh cong",1);
       }
       catch (ResourceNotFoundExecptionExecption e) {
        System.out.println(e.getMessage());
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
       }
    //    } catch (Exception e) {
    //     System.out.println(e.getMessage());
    //     return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Loi them nguoi dung");
    //    }
     
       
    }

    @PutMapping("/{userId}")
    public ResponseSuccess updateUser(@PathVariable @Min(1) int userId, @Valid @RequestBody UserRequestDTO user) {
       
        try {
            return new ResponseSuccess(HttpStatus.ACCEPTED, "Them du lieu thanh cong");
        } catch (Exception e) {
         return new ResponseFailed(HttpStatus.BAD_REQUEST,"Loi duong truyen");
        }
       
    }

    @PatchMapping("/{userId}")
    public ResponseSuccess updateStatus(@Min(1) @PathVariable int userId,
                               @RequestParam boolean status) {
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Them du lieu thanh cong");
    }

    @DeleteMapping("/{userId}")
    public ResponseSuccess deleteUser(@PathVariable @Min(value = 1, message = "userId must be greater than 0") int userId) {
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Them du lieu thanh cong");
    }

    @GetMapping("/{userId}")
    public ResponseSuccess getUser(@PathVariable @Min(1) int userId) {
        return new ResponseSuccess(HttpStatus.OK, "Them du lieu thanh cong",new UserRequestDTO("Leo", "Messi", "leomessi@email.com", "0123456456"));
    }
    @GetMapping("/list")
    public ResponseSuccess getAllUser(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                           @Min(10) @Max(20) @RequestParam(defaultValue = "20", required = false) int pageSize) {
        return new ResponseSuccess(HttpStatus.OK, "Them du lieu thanh cong", List.of(new UserRequestDTO("Tay", "Java", "admin@tayjava.vn", "0123456789"),
        new UserRequestDTO("Leo", "Messi", "leomessi@email.com", "0123456456")));
        
       
    }
    
}
