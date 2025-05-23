package example.userservice.controller;

import example.userservice.dto.UserDto;
import example.userservice.entity.UserEntity;
import example.userservice.service.UserService;
import example.userservice.vo.Greeting;
import example.userservice.vo.RequestUser;
import example.userservice.vo.ResponseUser;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;


    @Operation(summary = "Health check API", description = "Health check를 위한 API (포트 및 Token Secret 정보 확인 가능)")
    @GetMapping("/health-check")
    @Timed(value="users.status", longTask = true)
    public String status() {
        return String.format("It's Working in User Service"
                    +", port(local.server.port)=" + env.getProperty("local.server.port")
                    +", port(server.port)=" + env.getProperty("server.port")
                    +", token secret=" + env.getProperty("token.secret")
                    +", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @Operation(summary = "환영 메시지 출력 API", description = "Welcome message를 출력하기 위한 API")
    @GetMapping("/welcome")
    @Timed(value="users.welcome", longTask = true)
    public String welcome() {
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @Operation(summary = "사용자 회원 가입을 위한 API", description = "user-service에 회원 가입을 위한 API")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"),
    }
    )
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = modelMapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Operation(summary = "전체 사용자 목록조회 API", description = "현재 회원 가입 된 전체 사용자 목록을 조회하기 위한 API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "401", description = "Unauthorized (인증 실패 오류)"),
        @ApiResponse(responseCode = "403", description = "Forbidden (권한이 없는 페이지에 엑세스)"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"),
    }
    )
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "사용자 정보 상세조회 API", description = "사용자에 대한 상세 정보조회를 위한 API (사용자 정보 + 주문 내역 확인)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "401", description = "Unauthorized (인증 실패 오류)"),
        @ApiResponse(responseCode = "403", description = "Forbidden (권한이 없는 페이지에 엑세스)"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND (회원 정보가 없을 겨우)"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"),
    }
    )
    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);

        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        /*EntityModel entityModel = EntityModel.of(returnValue);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUsers());
        entityModel.add(linkTo.withRel("all-users"));

        try {
            return ResponseEntity.status(HttpStatus.OK).body(entityModel);
        } catch (Exception ex) {
            throw new RuntimeException();
        }*/
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
