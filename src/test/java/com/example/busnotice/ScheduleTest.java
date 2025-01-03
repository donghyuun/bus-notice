package com.example.busnotice;

import com.example.busnotice.domain.bus.Bus;
import com.example.busnotice.domain.bus.BusRepository;
import com.example.busnotice.domain.bus.BusService;
import com.example.busnotice.domain.busStop.BusStop;
import com.example.busnotice.domain.busStop.BusStopRepository;
import com.example.busnotice.domain.busStop.BusStopService;
import com.example.busnotice.domain.schedule.Schedule;
import com.example.busnotice.domain.schedule.ScheduleRepository;
import com.example.busnotice.domain.schedule.ScheduleService;
import com.example.busnotice.domain.schedule.req.CreateScheduleRequest;
import com.example.busnotice.domain.user.User;
import com.example.busnotice.domain.user.UserRepository;
import com.example.busnotice.domain.user.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ScheduleTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusStopService busStopService;
    @Autowired
    private BusStopRepository busStopRepository;
    @Autowired
    private BusService busService;
    @Autowired
    private BusRepository busRepository;
    // 객체 생성 예시
    CreateScheduleRequest createScheduleRequest = new CreateScheduleRequest(
        "Morning Schedule", // 스케줄 이름
        LocalDateTime.of(2025, 1, 4, 7, 0, 0, 0), // 시작 시간
        LocalDateTime.of(2025, 1, 4, 9, 0, 0, 0), // 마치는 시간
        12L, // 도시 코드
        "Main Bus Stop", // 버스 정류장 이름
        List.of("Bus A", "Bus B", "Bus C") // 버스 종류
    );
    @Test
    public void createSchedule() {
        User user = userRepository.save(new User("donghyun", "1234"));

        // 스케줄상의 버스 정류장 생성
        BusStop busStop = BusStop.toEntity(createScheduleRequest.name());
        busStopRepository.save(busStop);
        // 해당 스케줄상의 버스 정류장에서 조회하고픈 버스 목록 등록
        List<String> busNames = createScheduleRequest.busList();
        List<Bus> busList = new ArrayList<>();
        for (String busName : busNames) {
            busList.add(Bus.toEntity(busStop, busName));
        }
        busRepository.saveAll(busList);
        // 스케줄 생성 후 생성한 버스 정류장 등록
        Schedule schedule = Schedule.toEntity(user, createScheduleRequest.name(), createScheduleRequest.startTime(), createScheduleRequest.endTime(), busStop);
        scheduleRepository.save(schedule);
        System.out.println("schedule.toString() = " + schedule);
    }
}