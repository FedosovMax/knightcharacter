package com.knightexperience.app.service.impl;

import com.knightexperience.app.gateway.UserManagementGateway;
import com.knightexperience.app.rest.request.TodoRequest;
import com.knightexperience.app.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final UserManagementGateway userManagementGateway;

    @Override
    public void calculateExperience(TodoRequest todoRequest) {

        int scarynessExp = getScarynessExperience(todoRequest);
        int hardnessExp = getHardnessExperience(todoRequest);

        userManagementGateway.addExperience(scarynessExp + hardnessExp);

    }

    private int getScarynessExperience(TodoRequest todoRequest) {
        switch (todoRequest.getScaryness()){
            case NOT_SCARY: return 1;
            case SCARY: return 10;
            case VERY_SCARY: return 50;
            default: return 0;
        }
    }

    private int getHardnessExperience(TodoRequest todoRequest) {
        switch (todoRequest.getHardness()) {
            case NOT_HARD:
                return 1;
            case HARD:
                return 3;
            case VERY_HARD:
                return 10;
            case EXTRAORDINARY:
                return 30;
            case IMPOSSIBLE:
                return 100;
            default:
                return 0;
        }
    }
}
