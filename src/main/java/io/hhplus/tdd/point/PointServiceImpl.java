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

    @Override
    public UserPoint serchPoint(long id) throws IllegalAccessError {

        if(id <= 0 || id > 100){
            throw new IllegalAccessError("생성불가");
        }

        userPointTable.selectById(id);
        return UserPoint.empty(1);
    }
}
