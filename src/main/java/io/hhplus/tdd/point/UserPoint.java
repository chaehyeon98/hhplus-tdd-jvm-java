package io.hhplus.tdd.point;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public long point() {
        return point;
    }

    @Override
    public long updateMillis() {
        return updateMillis;
    }
}
