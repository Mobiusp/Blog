package com.mobiusp.backstage.mappers;

import com.mobiusp.backstage.pojo.FeedBack;

import java.sql.Timestamp;
import java.util.List;

public interface FeedbackMapper {
    List<FeedBack> getUnhandledFeedback ();
    List<FeedBack> getHandledFeedback ();
    int changeUnhandledFeedback (int id);
    int changeHandledFeedback (int id);
    Timestamp getChangeTime (int id);
}
