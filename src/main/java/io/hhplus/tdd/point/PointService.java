package io.hhplus.tdd.point;

import java.util.List;

public interface PointService {

    UserPoint serchPoint(long userId) throws IllegalArgumentException;

    UserPoint addPoint(UserPoint userPoint, long amount) throws IllegalArgumentException;

    UserPoint subPoint(UserPoint userPoint, long amount) throws IllegalArgumentException;

    void insertHistory(long userId, long amount, TransactionType type) throws IllegalArgumentException;

    List<PointHistory> getHistories(long userId) throws IllegalArgumentException;
}
