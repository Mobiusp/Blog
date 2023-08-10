package com.mobiusp.server.mappers;

public interface FeedbackMapper {
    int newFeedback (String uname, String email, String content, String picOne, String picTwo, String picThree);
}
