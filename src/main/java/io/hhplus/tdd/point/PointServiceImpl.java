package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserPoint serchPoint(long id) throws IllegalArgumentException {

        if(id <= 0 || id > 100){
            throw new IllegalArgumentException("생성불가");
        }

        return userPointTable.selectById(id);
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

        pointHistoryTable.insert(userPoint.id(), amount, TransactionType.CHARGE, System.currentTimeMillis());

        return userPointTable.insertOrUpdate(userPoint.id(), sum);
    }

    @Override
    public void insertHistory(long userId, long amount, TransactionType type) throws IllegalArgumentException {
        pointHistoryTable.insert(userId, amount, type, System.currentTimeMillis());
    }
}
