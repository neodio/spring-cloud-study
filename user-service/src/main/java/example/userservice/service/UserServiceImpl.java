package example.userservice.service;

import example.userservice.client.OrderServiceClient;
import example.userservice.dto.UserDto;
import example.userservice.entity.UserEntity;
import example.userservice.repository.UserRepository;
import example.userservice.vo.ResponseOrder;
import feign.FeignException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;

    private final Environment env;
//    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null)
            throw new UsernameNotFoundException(username + ": not found");

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
            true, true, true, true,
            new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        UserDto returnUserDto = modelMapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        log.info("Before call orders microservice");
        List<ResponseOrder> ordersList = new ArrayList<>();
        /* #1-1 Connect to order-service using a rest template */
        /* @LoadBalanced 로 선언헀으면, apigateway-service로 호출 못함 */
        /* http://ORDER-SERVICE/order-service/1234-45565-34343423432/orders */
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//        String orderUrl = String.format("http://localhost:8000/order-service/%s/orders", userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse =
//                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                                            new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });
//        ordersList = orderListResponse.getBody();
        /* #1-2 Connect to catalog-service using a rest template */
        /* http://CATALOG-SERVICE/catalog-service/catalogs */
//        List<ResponseCatalog> catalogList = new ArrayList<>();
//        String catalogUrl = "http://127.0.0.1:8000/catalog-service/catalogs";
//        ResponseEntity<List<ResponseCatalog>> catalogListResponse =
//                restTemplate.exchange(catalogUrl, HttpMethod.GET, null,
//                                            new ParameterizedTypeReference<List<ResponseCatalog>>() {
//                });
//        catalogList = catalogListResponse.getBody();
//        System.out.println(catalogList);

        /* Using a feign client */
        /* #2 Feign exception handling */
        try {
//            ResponseEntity<List<ResponseOrder>> _ordersList = orderServiceClient.getOrders(userId);
//            ordersList = _ordersList.getBody();
            ordersList = orderServiceClient.getOrders(userId);
        } catch (FeignException ex) {
            log.error(ex.getMessage());
        }

        /* #3-1 ErrorDecoder */
//        ordersList = orderServiceClient.getOrders(userId);
//        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker1");
//        CircuitBreaker circuitBreaker2 = circuitBreakerFactory.create("circuitBreaker2");
//        ordersList = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
//                throwable -> new ArrayList<>());
        /* #3-2 ErrorDecoder for catalog-service */
//        List<ResponseCatalog> catalogList = catalogServiceClient.getCatalogs();

        userDto.setOrders(ordersList);

        log.info("After called orders microservice");

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(userEntity, UserDto.class);
        return userDto;
    }
}
