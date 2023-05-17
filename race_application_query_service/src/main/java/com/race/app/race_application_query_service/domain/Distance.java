package com.race.app.race_application_query_service.domain;

public enum Distance
{
    FIVE_K("5k"), TEN_K("10k"), HALF_MARATHON("HalfMarthon"), MARATHON("Marathon");

    String value;

    Distance(String value)
    {
        this.value = value;
    }
}
