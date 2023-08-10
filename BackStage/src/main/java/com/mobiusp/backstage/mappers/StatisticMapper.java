package com.mobiusp.backstage.mappers;

public interface StatisticMapper {
    int updateSum (int id, int views);
    int newDay (int id, int views);
}
