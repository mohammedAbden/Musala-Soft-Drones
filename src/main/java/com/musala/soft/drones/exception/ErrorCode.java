package com.musala.soft.drones.exception;

public class ErrorCode {

    public static final String WEIGHT_LIMIT_NOT_PROVIDED = "DRN$0001";

    public static final String MODEL_NOT_PROVIDED = "DRN$0002";

    public static final String SERIAL_NUMBER_NOT_PROVIDED = "DRN$0003";

    public static final String SERIAL_NUMBER_MAX_100 = "DRN$0004";

    public static final String WEIGHT_LIMIT_SHOULD_BE_0_TO_500 = "DRN$0005";

    public static final String DRONE_SERIAL_NUMBER_EXIST = "DRN$0006";

    public static final String DRONE_NOT_EXIST = "DRN$0007";

    public static final String DRONE_NOT_IN_VALID_STATE_TO_LOAD = "DRN$0008";

    public static final String DRONE_BATTERY_LEVEL_LESS_THAN_THRESHOLD = "DRN$0009";

    public static final String MEDICATION_NAME_NOT_PROVIDED = "MED$0001";

    public static final String MEDICATION_CODE_NOT_PROVIDED = "MED$0002";

    public static final String MEDICATION_WEIGHT_NOT_PROVIDED = "MED$0003";

    public static final String MEDICATION_IMAGE_NOT_PROVIDED = "MED$0004";

    public static final String MEDICATION_NAME_NOT_MATCH_CRITERIA = "MED$0005";

    public static final String MEDICATION_CODE_NOT_MATCH_CRITERIA = "MED$0006";

    public static final String MEDICATION_LOADED_BEFORE_OR_NOT_EXIST ="MED$0007";

    public static final String MEDICATION_WEIGHT_MORE_THAN_DRONE_LIMIT = "MED$0008";

    public static final String NO_CURRENT_TRIP_FOR_DRONE = "TRP$0001";


}
