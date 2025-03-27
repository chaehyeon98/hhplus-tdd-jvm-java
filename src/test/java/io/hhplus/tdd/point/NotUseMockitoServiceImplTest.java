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

        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 100L, System.currentTimeMillis());
        userPoints.put(1L, userPoint);

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        Exception e = null;

        try{
            pointService.subPoint(userPoint, 0L);
        }catch (Exception exception) {
            e= exception;
        }

        assert e!= null;
        assert e.getMessage().equals("사용불가");
        assert e instanceof IllegalArgumentException;
    }

    /*
     * 포인트사용 point 99, amount 100일 경우
     * error 잔액부족
     * */
    @Test
    void subPoint_amount100() {
        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 99L, System.currentTimeMillis());
        userPoints.put(1L, userPoint);

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        Exception e = null;

        try{
            pointService.subPoint(userPoint, 100L);
        }catch (Exception exception) {
            e= exception;
        }

        assert e!= null;
        assert e.getMessage().equals("잔액부족");
        assert e instanceof IllegalArgumentException;

    }

    /*
     * 포인트사용 point 100, amount 100일 경우
     * UserPoint 0L반환
     * */
    @Test
    void subPoint_amountMAX_True() {
        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 100L, 0);
        userPoints.put(1L, userPoint);

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        UserPoint user = pointService.subPoint(userPoint, 100L);

        assert user.id() == 1L;
        assert user.point() == 0L;
    }

    /*
     * 포인트 내역 조회
     * 포인트 내역이 없을때
     * error 포인트 내역 없음
     * */
    @Test
    void getHistories_empty() {

        List<PointHistory> table = new ArrayList<>();
        Map<Long, UserPoint> userPoints = new HashMap<>();

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints));

        Exception e = null;

        try{
            pointService.getHistories(1L);
        }catch (Exception exception) {
            e= exception;
        }

        assert e!= null;
        assert e.getMessage().equals("포인트 내역 없음");
        assert e instanceof IllegalArgumentException;
    }

    /*
     * 포인트 내역 조회
     * id 1로 저장된 내역 조회되는지
     * */
    @Test
    void getHistories() {
        List<PointHistory> table = new ArrayList<>();
        table.add(new PointHistory(1, 1,100,TransactionType.CHARGE, System.currentTimeMillis()));
        table.add(new PointHistory(2, 1,100,TransactionType.USE, System.currentTimeMillis()));
        table.add(new PointHistory(3, 2,100,TransactionType.CHARGE, System.currentTimeMillis()));

        Map<Long, UserPoint> userPoints = new HashMap<>();
        userPoints.put(1L, new UserPoint(1L, 0L, System.currentTimeMillis()));
        userPoints.put(2L, new UserPoint(2L, 100L, System.currentTimeMillis()));

        PointService pointService = new PointServiceImpl(new PointHistoryTable(table, 4), new UserPointTable(userPoints));

        List<PointHistory> pointHistories = pointService.getHistories(1L);

        assert pointHistories.size() == 2;

        assert pointHistories.get(0).id() == 1L;
        assert pointHistories.get(0).userId() == 1L;
        assert pointHistories.get(0).amount() == 100L;
        assert pointHistories.get(0).type() == TransactionType.CHARGE;

        assert pointHistories.get(1).id() == 2L;
        assert pointHistories.get(1).userId() == 1L;
        assert pointHistories.get(1).amount() == 100L;
        assert pointHistories.get(1).type() == TransactionType.USE;
    }
}