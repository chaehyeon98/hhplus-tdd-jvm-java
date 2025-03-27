package io.hhplus.tdd.point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @Mock
    private PointService pointService;

    /*
    * 포인트조회 id 0으로 조회
    * error 생성불가
    * */
    @Test
    void serchPoint_id0() {

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointService.serchPoint(0L)).thenThrow(new IllegalArgumentException("생성불가"));

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pointService.serchPoint(0L));

        Assertions.assertEquals("생성불가", exception.getMessage());

    }

    /*
     * 포인트조회 id 101으로 조회
     * error 생성불가
     * */
    @Test
    void serchPoint_id101() {

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(searchPoint) 생성되어야되는 결과
        Mockito.when(pointService.serchPoint(101L)).thenThrow(new IllegalArgumentException("생성불가"));

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> pointService.serchPoint(101L));

        Assertions.assertEquals("생성불가", exception.getMessage());
    }

}