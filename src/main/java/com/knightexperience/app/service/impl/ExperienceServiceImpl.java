package com.knightexperience.app.service.impl;

import com.knightexperience.app.domain.TodoVO;
import com.knightexperience.app.gateway.UserManagementGateway;
import com.knightexperience.app.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final UserManagementGateway userManagementGateway;

    @Override
    public void calculateExperience(TodoVO todoVO) {

        int scarynessExp = getScarynessExperience(todoVO);
        int hardnessExp = getHardnessExperience(todoVO);

        userManagementGateway.addExperience(scarynessExp + hardnessExp);

    }

    private int getScarynessExperience(TodoVO todoVO) {
        switch (todoVO.getScaryness()) {
            case NOT_SCARY:
                return 1;
            case SCARY:
                return 10;
            case VERY_SCARY:
                return 50;
            default:
                return 0;
        }
    }

    private int getHardnessExperience(TodoVO todoVO) {
        switch (todoVO.getHardness()) {
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
