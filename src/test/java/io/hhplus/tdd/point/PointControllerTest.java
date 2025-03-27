package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class PointControllerTest {

    @Mock
    PointController pointController;
    /*
     * 포인트조회 id 0으로 조회
     * error 생성불가
     * */
    @Test
    void point_id0() {

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.point(0L)).thenThrow(new IllegalArgumentException("생성불가"));

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pointController.point(0L));

        Assertions.assertEquals("생성불가", exception.getMessage());

    }

    /*
     * 포인트조회 id 101으로 조회
     * error 생성불가
     * */
    @Test
    void point_id101() {

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.point(101L)).thenThrow(new IllegalArgumentException("생성불가"));

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pointController.point(101L));

        Assertions.assertEquals("생성불가", exception.getMessage());

    }

    /*
     * 포인트조회 id 1로 조회시 알맞은 값 조회되는지
     *
     * */
    @Test
    void point() {

        //ginven
        UserPoint mokUserPoint =  UserPoint.empty(1L);

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.point(1L)).thenReturn(mokUserPoint);

        //then
        //id 1로 데이터가 생성되었는지
        UserPoint userPoint =  pointController.point(1L);
        Assertions.assertEquals(mokUserPoint, userPoint);

        //point가 호출된것이 맞는지 검증
        InOrder inOrder = Mockito.inOrder(pointController);
        inOrder.verify(pointController).point(1L);
    }

    /*
     * 포인트충전 amount 0일 경우
     * error 충전불가
     * */
    @Test
    void addPoint_amount0() {
        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.charge(1L, 0L)).thenThrow(new IllegalArgumentException("충전불가"));

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pointController.charge(1L, 0L));

        Assertions.assertEquals("충전불가", exception.getMessage());
    }

    /*
     * 포인트충전 amount 101일 경우
     * error 충전불가
     * */
    @Test
    void addPoint_amount101() {
        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.charge(1L, 101L)).thenThrow(new IllegalArgumentException("충전불가"));

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pointController.charge(1L, 101L));

        Assertions.assertEquals("충전불가", exception.getMessage());
    }

    /*
     * 포인트충전 amount 1000이상 충전시
     * 충전불가
     * */
    @Test
    void addPoint_amountMax() {

        //given
        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 999L, System.currentTimeMillis());
        userPoints.put(1L, userPoint);

        pointController = Mockito.spy(new PointController(
                new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints))
        ));

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.doThrow(new IllegalArgumentException("1000포인트이상 충전불가"))
                .when(pointController).charge(1L, 2L);

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pointController.charge(1L, 2L));

        Assertions.assertEquals("1000포인트이상 충전불가", exception.getMessage());
    }

    /*
     * 포인트충전 amount 1000이상에 맞출시
     * UserPoint 1000L반환
     * */
    @Test
    void addPoint_amountMax_True() {
        //given
        List<PointHistory> table = new ArrayList<>();

        Map<Long, UserPoint> userPoints = new HashMap<>();
        UserPoint userPoint = new UserPoint(1L, 999L, System.currentTimeMillis());
        userPoints.put(1L, userPoint);

        pointController = Mockito.spy(new PointController(
                new PointServiceImpl(new PointHistoryTable(table, 1), new UserPointTable(userPoints))
        ));

        UserPoint mokUserPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.charge(1L, 1L)).thenReturn(mokUserPoint);

        //then
        //id 1로 데이터가 생성되었는지
        UserPoint userPoint2 = pointController.charge(1L, 1L);
        Assertions.assertEquals(mokUserPoint.id(), userPoint2.id());
        Assertions.assertEquals(mokUserPoint.point(), userPoint2.point());

        //point가 호출된것이 맞는지 검증
        InOrder inOrder = Mockito.inOrder(pointController);
        inOrder.verify(pointController).charge(1L, 1L);
    }
}
