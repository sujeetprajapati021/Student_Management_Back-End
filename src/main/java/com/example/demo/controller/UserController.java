package com.example.demo.controller;


import com.example.demo.dto.AddUserRequest;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(tags = "User Controller")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

	private final IUserService userService;

//	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	@CrossOrigin("*")
	@ApiOperation(value = "CREATE USER API")
	@PostMapping("/addUser")
	public ResponseEntity<BaseResponse<Object>> addUser(
			@Valid @RequestBody AddUserRequest dto) {
		BaseResponse<Object> response = BaseResponse.builder()
				.successMsg("User Added")
				.data(userService.addUser(dto))
				.build();
		return ResponseEntity.ok(response);
	}
}
