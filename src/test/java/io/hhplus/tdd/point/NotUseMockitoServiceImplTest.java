package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class NotUseMockitoServiceImplTest {

    /*
     * 포인트충전 amount 0일 경우
     * error 충전불가
     * */
    @Test
    void addPoint_amount0() {

        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 100L, System.currentTimeMillis());
        userPoints.put(1L, userPoint);

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        Exception e = null;

        try{
            pointService.addPoint(userPoint, 0L);
        }catch (Exception exception) {
            e= exception;
        }

        assert e!= null;
        assert e.getMessage().equals("충전불가");
        assert e instanceof IllegalArgumentException;
    }

    /*
     * 포인트충전 amount 101일 경우
     * error 충전불가
     * */
    @Test
    void addPoint_amount101() {
        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 100L, System.currentTimeMillis());
        userPoints.put(1L, userPoint);

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        Exception e = null;

        try{
            pointService.addPoint(userPoint, 101L);
        }catch (Exception exception) {
            e= exception;
        }

        assert e!= null;
        assert e.getMessage().equals("충전불가");
        assert e instanceof IllegalArgumentException;
    }

    /*
     * 포인트충전 amount 1000이상 충전시
     * 충전불가
     * */
    @Test
    void addPoint_amountMax() {
        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 999L, System.currentTimeMillis());
        userPoints.put(1L, userPoint);

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        Exception e = null;

        try{
            pointService.addPoint(userPoint, 2L);
        }catch (Exception exception) {
            e= exception;
        }

        assert e!= null;
        assert e.getMessage().equals("1000포인트이상 충전불가");
        assert e instanceof IllegalArgumentException;
    }

    /*
     * 포인트충전 amount 1000이상에 맞출시
     * UserPoint 1000L반환
     * */
    @Test
    void addPoint_amountMax_True() {
        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 999L, 0);
        userPoints.put(1L, userPoint);

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        UserPoint user = pointService.addPoint(userPoint, 1L);

        assert user.id() == 1L;
        assert user.point() == 1000L;
    }

    /*
     * 포인트사용 amount 0일 경우
     * error 사용불가
     * */
    @Test
    void subPoint_amount0() {

    }

    /*
     * 포인트사용 amount 100일 경우
     * error 잔액부족
     * */
    @Test
    void subPoint_amount100() {

    }


}