package io.hhplus.tdd.point;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public UserPoint point(
            @PathVariable long id
    ) {
        return pointService.serchPoint(id);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistory> history(
            @PathVariable long id
    ) {
        return pointService.getHistories(id);
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPoint charge(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        UserPoint userPoint = pointService.serchPoint(id);  //포인트 조회
        UserPoint returnUser = pointService.addPoint(userPoint, amount);    //포인트충전
        pointService.insertHistory(id, amount, TransactionType.CHARGE);     //충전 기록

        return returnUser;
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPoint use(
            @PathVariable long id,
            @RequestBody long amount
    ) {

        UserPoint userPoint = pointService.serchPoint(id);  //포인트 조회
        UserPoint returnUser = pointService.subPoint(userPoint, amount);    //포인트사용
        pointService.insertHistory(id, amount, TransactionType.CHARGE);     //사용 기록

        return returnUser;
    }
}
