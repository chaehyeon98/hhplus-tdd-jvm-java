package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    PointHistoryTable pointHistoryTable;

    @Autowired
    UserPointTable userPointTable;

    public PointServiceImpl(PointHistoryTable pointHistoryTable, UserPointTable userPointTable){
        this.pointHistoryTable = pointHistoryTable;
        this.userPointTable = userPointTable;
    }

    @Override
    public UserPoint serchPoint(long userId) throws IllegalArgumentException {

        if(userId <= 0 || userId > 100){
            throw new IllegalArgumentException("생성불가");
        }

        return userPointTable.selectById(userId);
    }

    @Override
    public UserPoint addPoint(UserPoint userPoint, long amount) throws IllegalArgumentException {

        if(amount <= 0 || amount > 100) {
            throw new IllegalArgumentException("충전불가");
        }

        long sum = userPoint.point() + amount;
        if(sum > 1000){
            throw new IllegalArgumentException("1000포인트이상 충전불가");
        }

        return userPointTable.insertOrUpdate(userPoint.id(), sum);
    }

    @Override
    public UserPoint subPoint(UserPoint userPoint, long amount) throws IllegalArgumentException {

        if(amount <= 0) {
            throw new IllegalArgumentException("사용불가");
        }

        long sub = userPoint.point() - amount;
        if(sub < 0){
            throw new IllegalArgumentException("잔액부족");
        }

        return userPointTable.insertOrUpdate(userPoint.id(), sub);
    }

    @Override
    public void insertHistory(long userId, long amount, TransactionType type) throws IllegalArgumentException {
        pointHistoryTable.insert(userId, amount, type, System.currentTimeMillis());
    }

    @Override
    public List<PointHistory> getHistories(long userId) throws IllegalArgumentException{

        if(userId <= 0 || userId > 100){
            throw new IllegalArgumentException("생성불가");
        }

        List<PointHistory> pointHistories = pointHistoryTable.selectAllByUserId(userId);

        if(pointHistories.isEmpty()){
            throw new IllegalArgumentException("포인트 내역 없음");
        }

        return pointHistories;
    }
}
