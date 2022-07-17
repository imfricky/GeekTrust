package com.example.geektrust.services;

import com.example.geektrust.constants.CommandType;
import com.example.geektrust.dtos.RenewalDetailsDTO;
import com.example.geektrust.exceptions.InvalidRequestException;
import org.apache.commons.lang3.ObjectUtils;

public class CommandManagerService {

    private SubscriptionService subscriptionService;

    public CommandManagerService() {
        this.subscriptionService = new SubscriptionService();
    }

    public void parseInput(String param) {
        String[] inputParams = param.split(" ");
        String commandTypeString = inputParams[0];
        CommandType commandType = CommandType.getCommandType(commandTypeString);
        try {
            if (ObjectUtils.isEmpty(commandType)) {
                throw new InvalidRequestException("INVALID_COMMAND");
            }
            switch (commandType) {
                case START_SUBSCRIPTION:
                    subscriptionService.startSubscription(inputParams);
                    break;

                case ADD_SUBSCRIPTION:
                    subscriptionService.addSubscription(inputParams);
                    break;

                case ADD_TOPUP:
                    subscriptionService.addTopup(inputParams);
                    break;

                case PRINT_RENEWAL_DETAILS:
                    RenewalDetailsDTO renewalDetailsDTO = subscriptionService.printRenewalDetails();
                    System.out.println(renewalDetailsDTO);
                    break;

                default:
                    break;
            }
        } catch (InvalidRequestException e) {
            System.out.println(e.getMessage());
        }

    }

}
