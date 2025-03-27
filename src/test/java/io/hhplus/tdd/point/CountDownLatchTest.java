package io.hhplus.tdd.point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@ExtendWith(MockitoExtension.class)
class CountDownLatchTest {

    @Mock
    PointController pointController;

    /*
     * 4가지 기능 통합테스트
     * */
    @Test
    void allAboutTest() {

        //given
        List<PointHistory> mockHistory = new ArrayList<>();
        mockHistory.add(new PointHistory(1L, 1L, 100L, TransactionType.CHARGE, System.currentTimeMillis()));
        mockHistory.add(new PointHistory(2L, 1L, 90L, TransactionType.USE, System.currentTimeMillis()));

        UserPoint mockChargedPoint = new UserPoint(1L, 100L, 0);
        UserPoint mockUseUser = new UserPoint(1L, 10L, 0);
        UserPoint mockPointUser = new UserPoint(1L, 10L, 0);

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(charge to use) 생성되어야되는 결과
        Mockito.when(pointController.charge(1L, 100L)).thenReturn(mockChargedPoint);
        Mockito.when(pointController.use(1L, 90L)).thenReturn(mockUseUser);
        Mockito.when(pointController.history(1L)).thenReturn(mockHistory);
        Mockito.when(pointController.point(1L)).thenReturn(mockPointUser);

        UserPoint chargeUser = pointController.charge(1L, 100L);
        UserPoint useUser = pointController.use(1L, 90L);
        List<PointHistory> history = pointController.history(1L);
        UserPoint pointUser = pointController.point(1L);

        //then
        //충전 검증
        Assertions.assertEquals(chargeUser.id(), mockChargedPoint.id());
        Assertions.assertEquals(chargeUser.point(), mockChargedPoint.point());

        //사용검증
        Assertions.assertEquals(useUser.id(), mockUseUser.id());
        Assertions.assertEquals(useUser.point(), mockUseUser.point());

        //조회 검증
        Assertions.assertEquals(pointUser.id(), mockPointUser.id());
        Assertions.assertEquals(pointUser.point(), mockPointUser.point());

        //포인트 내역 검증
        Assertions.assertEquals(history.get(0).id(), mockHistory.get(0).id());
        Assertions.assertEquals(history.get(0).userId(), mockHistory.get(0).userId());
        Assertions.assertEquals(history.get(0).amount(), mockHistory.get(0).amount());
        Assertions.assertEquals(history.get(0).type(), mockHistory.get(0).type());

        Assertions.assertEquals(history.get(1).id(), mockHistory.get(1).id());
        Assertions.assertEquals(history.get(1).userId(), mockHistory.get(1).userId());
        Assertions.assertEquals(history.get(1).amount(), mockHistory.get(1).amount());
        Assertions.assertEquals(history.get(1).type(), mockHistory.get(1).type());

    }

    /*
     * 포인트 100 충전 후 포인트 101 사용
     * exception "잔액부족"
     * */
    @Test
    void AddToSub() {

        //when 동작 검증
        //가상의객체 PointService에서 동작했을시(charge to use) 생성되어야되는 결과
        Mockito.when(pointController.charge(1L, 100L)).thenReturn(new UserPoint(1L, 100L, 0));
        Mockito.when(pointController.use(1L, 101L)).thenThrow(new IllegalArgumentException("잔액부족"));

        //then 결과검증
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    pointController.charge(1L, 100L);
                    pointController.use(1L, 101L);
                });

        Assertions.assertEquals("잔액부족", exception.getMessage());

    }

    /*
     * 포인트 100 충전 * 11
     * exception "포인트 최대 충전"
     * */
    @Test
    void addMAX() throws InterruptedException {

        //given
        AtomicInteger counter = new AtomicInteger(0);

        //when
        // Mock 동작 설정 - 10번째 충전 시 예외 발생
        Mockito.doAnswer(invocation -> {
            int value = counter.incrementAndGet();
            if (value < 10) {
                return new UserPoint(1L, 100L * value, 0);
            }
            throw new IllegalArgumentException("포인트 최대 충전");
        }).when(pointController).charge(1L, 100L);

        //then
        //쓰레드로 9개 한꺼번에 돌아가도록 설정
        int threadCount = 9;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(pointController.charge(1L, 100L));
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }

            });
        }

        //9개 실행이 완료 될때까지 대기
        latch.await();
        //쓰레드 종료
        executorService.shutdown();

        //마지막 한개 예외 발생
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> System.out.println(pointController.charge(1L, 100L))
        );

        Assertions.assertEquals("포인트 최대 충전", exception.getMessage());
    }

    /*
     * (포인트 100 충전, 포인트 100 사용, 포인트 내역 조회) * 5
     * 1. 순서대로 userpoint 반환
     * 2. 내역조회시 충전/사용 번갈아 출력
     *
     * CountDownLatch를 사용하여 (포인트 100 충전, 포인트 100 사용,
     * 포인트 내역 조회)이 돌기까지 대기.
     * */
    @Test
    void countDownLatchTest() throws InterruptedException {

        List<PointHistory> mockHistory = new ArrayList<>();

        long id = 1L;
        long amount = 100L;
        long minAmount = 0L;

        //given + when 동작 검증
        //가상의객체 PointService에서 동작했을시(charge to use) 생성되어야되는 결과
        for(int i=1; i<=5; i++){

            Mockito.when(pointController.charge(id, amount)).thenReturn(new UserPoint(id, amount, System.currentTimeMillis()));
            mockHistory.add(new PointHistory(i+(i-1), id, amount, TransactionType.CHARGE, System.currentTimeMillis()));
            Mockito.when(pointController.use(id, amount)).thenReturn(new UserPoint(id, minAmount, System.currentTimeMillis()));
            mockHistory.add(new PointHistory(i+i, id, amount, TransactionType.USE, System.currentTimeMillis()));
            Mockito.when(pointController.history(id)).thenReturn(mockHistory);
        }

        //then 결과검증
        for(int i=1; i<=5; i++){
            //then
            CountDownLatch latch = new CountDownLatch(1);

            UserPoint userPoint1 = pointController.charge(id, amount);

            Assertions.assertEquals(id, userPoint1.id());
            Assertions.assertEquals(amount, userPoint1.point());

            UserPoint userPoint2 = pointController.use(id, amount);

            Assertions.assertEquals(id, userPoint2.id());
            Assertions.assertEquals(minAmount, userPoint2.point());

            List<PointHistory> history = pointController.history(id);


            for (int j = 0; j < history.size(); j++) {

                Assertions.assertEquals(mockHistory.get(j).id(), history.get(j).id());
                Assertions.assertEquals(mockHistory.get(j).userId(), history.get(j).userId());
                Assertions.assertEquals(mockHistory.get(j).amount(), history.get(j).amount());
                Assertions.assertEquals(mockHistory.get(j).type(), history.get(j).type());

            }
            latch.countDown();

            //1개 실행이 완료 될때까지 대기
            latch.await();
        }

    }


}