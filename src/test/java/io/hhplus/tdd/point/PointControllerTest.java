package io.hhplus.tdd.point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PointControllerTest {

    @Mock
    PointController pointController;
    /*
     * 포인트조회 id 0으로 조회
     * error 생성불가
     * */
    @Test
    void point_id0() throws IllegalAccessError {

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.point(0L)).thenThrow(new IllegalAccessError("생성불가"));

        //then 결과검증
        IllegalAccessError exception = Assertions.assertThrows(IllegalAccessError.class, () -> pointController.point(0L));

        Assertions.assertEquals("생성불가", exception.getMessage());

    }

    /*
     * 포인트조회 id 101으로 조회
     * error 생성불가
     * */
    @Test
    void point_id101() throws IllegalAccessError {

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointController.point(101L)).thenThrow(new IllegalAccessError("생성불가"));

        //then 결과검증
        IllegalAccessError exception = Assertions.assertThrows(IllegalAccessError.class, () -> pointController.point(101L));

        Assertions.assertEquals("생성불가", exception.getMessage());

    }

    /*
     * 포인트조회 id 1로 조회시 알맞은 값 조회되는지
     *
     * */
    @Test
    void point() throws IllegalAccessError {

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
}