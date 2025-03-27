package io.hhplus.tdd.point;

public interface PointService {

    UserPoint serchPoint(long id) throws IllegalArgumentException;

    UserPoint addPoint(UserPoint userPoint, long amount) throws IllegalArgumentException;

    UserPoint subPoint(UserPoint userPoint, long amount) throws IllegalArgumentException;

    void insertHistory(long userId, long amount, TransactionType type) throws IllegalArgumentException;
}
